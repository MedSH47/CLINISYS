package com.csys.template.repository;

import com.csys.template.domain.ChatMessage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository Spring Data JPA pour l'entité ChatMessage.
 */
@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {

    /**
     * Trouve tous les messages échangés entre deux utilisateurs (dans les deux sens)
     * et les trie par ordre chronologique (du plus ancien au plus récent).
     *
     * @param senderId1 ID du premier utilisateur.
     * @param receiverId1 ID du second utilisateur.
     * @param senderId2 ID du second utilisateur.
     * @param receiverId2 ID du premier utilisateur.
     * @return Une liste de ChatMessage triée.
     */
    List<ChatMessage> findBySenderAndReceiverOrReceiverAndSenderOrderByTimestampAsc(
        Integer senderId1, 
        Integer receiverId1, 
        Integer senderId2, 
        Integer receiverId2
    );


    /**
     * Trouve le dernier message de chaque conversation pour un utilisateur donné.
     * C'est une requête complexe qui ne peut pas être générée automatiquement à partir du nom de la méthode,
     * nous utilisons donc l'annotation @Query pour écrire notre propre logique en JPQL.
     *
     * Comment fonctionne la requête :
     * 1. La sous-requête (dans le IN) identifie l'ID du dernier message pour chaque fil de discussion.
     * 2. Elle regroupe les messages par "partenaire de conversation" (l'autre personne).
     * - `CASE WHEN cm2.sender = :userId THEN cm2.receiver ELSE cm2.sender END` permet d'identifier ce partenaire.
     * 3. `MAX(cm2.id)` est utilisé pour trouver le dernier message (en supposant que les IDs sont séquentiels et croissants).
     * 4. La requête principale récupère ensuite les entités ChatMessage complètes pour les IDs trouvés.
     *
     * @param userId L'ID de l'utilisateur pour lequel on veut récupérer les conversations.
     * @return Une liste contenant le dernier message de chaque conversation.
     */
    @Query("SELECT cm FROM ChatMessage cm WHERE cm.id IN (" +
           "SELECT MAX(cm2.id) FROM ChatMessage cm2 " +
           "WHERE cm2.sender = :userId OR cm2.receiver = :userId " +
           "GROUP BY CASE WHEN cm2.sender = :userId THEN cm2.receiver ELSE cm2.sender END" +
           ") ORDER BY cm.timestamp DESC")
    List<ChatMessage> findLatestMessageFromEachConversation(@Param("userId") Integer userId);
    
}