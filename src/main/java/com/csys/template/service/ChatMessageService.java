package com.csys.template.service;

import com.csys.template.domain.ChatMessage;
import com.csys.template.domain.Utilisateur;
import com.csys.template.dtoProjection.ChatContactDTO;
import com.csys.template.dtoRequest.UtilisateurRequestDTO;
import com.csys.template.dtoResponse.UtilisateurResponseDTO;
import com.csys.template.factory.UtilisateurFactory;
import com.csys.template.repository.ChatMessageRepository;
import com.csys.template.repository.UtilisateurRepository;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Import essentiel

/**
 * Service pour gérer la logique métier du chat.
 * L'annotation @Service en fait un bean Spring.
 * Les méthodes sont transactionnelles pour assurer la cohérence des données.
 */
@Service
public class ChatMessageService {

    private static final Logger log = LoggerFactory.getLogger(ChatMessageService.class);
    
    private final ChatMessageRepository chatMessageRepository;
    private final UtilisateurRepository utilisateurRepository;

    @Autowired
    public ChatMessageService(ChatMessageRepository chatMessageRepository, UtilisateurRepository utilisateurRepository) {
        this.chatMessageRepository = chatMessageRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    /**
     * Traite, valide, et sauvegarde un message privé.
     * L'annotation @Transactional garantit que toutes les opérations sur la base de données
     * (lecture des utilisateurs, sauvegarde du message) sont atomiques.
     * Si une erreur survient, tout est annulé (rollback). Si tout réussit, tout est validé (commit).
     *
     * @param messagePayload Le message reçu du client WebSocket.
     * @param principal L'objet Principal représentant l'utilisateur authentifié (l'expéditeur).
     * @return Le message sauvegardé avec les détails de l'expéditeur et du destinataire.
     */
    @Transactional
    public ChatMessage processAndSavePrivateMessage(ChatMessage messagePayload, Principal principal) {
        log.info("SERVICE: Début du traitement du message de {} vers l'utilisateur ID {}", principal.getName(), messagePayload.getReceiver());

        // 1. Récupérer les entités complètes de l'expéditeur et du destinataire
        Utilisateur senderUser = utilisateurRepository.findByLogin(principal.getName());
        if (senderUser == null) {
            log.error("SERVICE: Expéditeur non trouvé avec le login: {}", principal.getName());
            // Il est préférable de lever une exception spécifique que votre contrôleur peut attraper
            throw new IllegalArgumentException("L'utilisateur expéditeur n'existe pas : " + principal.getName());
        }
        
        Utilisateur receiverUser = utilisateurRepository.findById(messagePayload.getReceiver())
                .orElseThrow(() -> {
                    log.error("SERVICE: Destinataire non trouvé avec l'ID: {}", messagePayload.getReceiver());
                    return new IllegalArgumentException("L'utilisateur destinataire avec l'ID " + messagePayload.getReceiver() + " n'existe pas.");
                });

        // 2. Hydrater l'objet message avec les informations serveur avant la sauvegarde
        messagePayload.setSender(senderUser.getId());
        // L'ID du récepteur est déjà fourni par le client, on fait confiance à cette information pour le moment.
        
        // 3. Sauvegarder en base de données. La transaction garantit le "commit".
        ChatMessage savedMessage = chatMessageRepository.save(messagePayload);
        log.info("SERVICE: Message sauvegardé en BDD avec l'ID: {}", savedMessage.getId());
        
        // 4. Enrichir l'objet sauvegardé avec les détails pour le renvoyer au frontend
        // Ces détails ne sont pas persistés en BDD car les champs sont @Transient sur l'entité
        savedMessage.setSenderDetails(UtilisateurFactory.toDTOLight(senderUser));
        savedMessage.setReceiverDetails(UtilisateurFactory.toDTOLight(receiverUser));

        return savedMessage;
    }

    /**
     * Récupère l'historique de chat entre deux utilisateurs.
     * Transaction en lecture seule (@Transactional(readOnly = true)) pour de meilleures performances.
     */
    @Transactional(readOnly = true)
    public List<ChatMessage> findByParticipants(Integer user1, Integer user2) {
        // La recherche elle-même
        List<ChatMessage> messages = chatMessageRepository.findBySenderAndReceiverOrReceiverAndSenderOrderByTimestampAsc(user1, user2, user1, user2);
        
        // Enrichissement des messages avec les détails de l'expéditeur/destinataire
        messages.forEach(msg -> {
             utilisateurRepository.findById(msg.getSender()).ifPresent(user -> msg.setSenderDetails(UtilisateurFactory.toDTOLight(user)));
             utilisateurRepository.findById(msg.getReceiver()).ifPresent(user -> msg.setReceiverDetails(UtilisateurFactory.toDTOLight(user)));
        });
        return messages;
    }

    /**
     * Récupère les derniers messages de chaque conversation pour un utilisateur donné.
     * Transaction en lecture seule.
     */
        @Transactional(readOnly = true)
    public List<ChatContactDTO> findMyChatMessages(Integer userId) {
        // 1. Récupère les derniers messages de chaque conversation
        List<ChatMessage> latestMessages = chatMessageRepository.findLatestMessageFromEachConversation(userId);
        
        List<ChatContactDTO> contacts = new ArrayList<>();

        // 2. Pour chaque dernier message, on identifie le partenaire et on crée le DTO
        for (ChatMessage msg : latestMessages) {
            // On détermine qui est le partenaire
            Integer partnerId = msg.getSender().equals(userId) ? msg.getReceiver() : msg.getSender();
            
            // On récupère les détails du partenaire
            utilisateurRepository.findById(partnerId).ifPresent(partnerUser -> {
                UtilisateurResponseDTO partnerDto = UtilisateurFactory.toDTOLight(partnerUser);
                contacts.add(new ChatContactDTO(partnerDto, msg));
            });
        }
        
        return contacts;
    }
}