package com.csys.template.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csys.template.config.MailSender;
import com.csys.template.domain.QUtilisateur;
import com.csys.template.domain.Utilisateur;
import com.csys.template.domain.enum_identifier.Role;
import com.csys.template.domain.enum_identifier.Status;
import com.csys.template.dtoRequest.UtilisateurRequestDTO;
import com.csys.template.dtoResponse.UtilisateurResponseDTO;
import com.csys.template.factory.UtilisateurFactory;
import com.csys.template.repository.UtilisateurRepository;
import com.csys.template.util.FormulairesHtml;
import com.csys.template.util.WhereClauseBuilder;

@Service
@Transactional
public class UtilisateurService {

  private final Logger log = LoggerFactory.getLogger(UtilisateurService.class);
  private final UtilisateurRepository utilisateurRepository;
  private final MailSender mailSender;

  public UtilisateurService(UtilisateurRepository utilisateurRepository, MailSender mailSender) {
    this.utilisateurRepository = utilisateurRepository;
    this.mailSender = mailSender;
  }

  public CompletableFuture<Utilisateur> saveUserWithPhoto(UtilisateurRequestDTO dto, byte[] photoBytes) {
    // Here you would perform long-running operations like resizing the photo,
    // saving it to cloud storage, etc.
    // For this example, we just set the bytes.

    Utilisateur utilisateur = UtilisateurFactory.toEntity(dto);
    if (photoBytes != null) {
      utilisateur.setPhoto(photoBytes);
    }
    utilisateur = utilisateurRepository.save(utilisateur);

    return CompletableFuture.completedFuture(utilisateur);
  }
  // ... other methods

  public UtilisateurResponseDTO update(Integer userId, UtilisateurRequestDTO userRequestDTO, byte[] photo) {
    log.debug("Service: Request to update Utilisateur ID: {}", userId);

    Utilisateur existingUser = utilisateurRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé avec l'id: " + userId));

    if (photo != null) {
      existingUser.setPhoto(photo);
    }

    boolean passwordChanged = false;
    String newPassword = userRequestDTO.getMotDePasse();

    if (newPassword != null && !newPassword.equals(existingUser.getMotDePasse())) {
      existingUser.setMotDePasse(newPassword); // Idéalement, le mot de passe devrait être chiffré ici
      passwordChanged = true;
    }

    UtilisateurFactory.updateFromDTO(existingUser, userRequestDTO);
    Utilisateur updatedUser = utilisateurRepository.save(existingUser);

    if (passwordChanged) {
      String subject = "✅ Votre mot de passe a été mis à jour";
      String userName = existingUser.getNom() + " " + existingUser.getPrenom();

      // Utilisation du nouveau gabarit HTML
      String htmlBody = FormulairesHtml.genererHtmlChangementMotDePasse(userName);

      try {
        // Appel de la méthode pour envoyer l'email HTML
        mailSender.sendHtmlMail(existingUser.getEmail(), subject, htmlBody);
      } catch (MessagingException e) {
        log.error("Échec de l'envoi de l'e-mail d'alerte de changement de mot de passe à l'utilisateur {}",
            existingUser.getLogin(), e);
      }
    }

    return UtilisateurFactory.toResponseDTO(updatedUser);
  }

  @Transactional(readOnly = true)
  public UtilisateurResponseDTO findOne(Integer id) {
    log.debug("Request to get Utilisateur: {}", id);
    Utilisateur utilisateur = utilisateurRepository.findById(id).orElse(null);
    return UtilisateurFactory.toResponseDTO(utilisateur);
  }

  @Transactional(readOnly = true)
  public Collection<UtilisateurResponseDTO> findAll(Role role, Boolean[] actifs) {
    log.debug("Request to get All Utilisateurs");
    QUtilisateur qUtilisateur = QUtilisateur.utilisateur;
    WhereClauseBuilder builder = new WhereClauseBuilder()
        .optionalAnd(role, () -> qUtilisateur.role.in(role));
    if (actifs != null && actifs.length == 1) {
      builder.optionalAnd(actifs.length == 1, () -> qUtilisateur.actif.eq(actifs[0]));
    }
    List<Utilisateur> result = (List<Utilisateur>) utilisateurRepository.findAll(builder);
    return UtilisateurFactory.toResponseDTOs(result);
  } 

  public void delete(Integer id) {
    log.debug("Request to delete Utilisateur: {}", id);
    Utilisateur found = utilisateurRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Utilisateur not found with id: " + id));
    UtilisateurResponseDTO foundComplete = UtilisateurFactory.toResponseDTO(found);
    boolean hasEnCoursTicket = false;
    if (foundComplete.getTicketList() != null) {
      for (var ticket : foundComplete.getTicketList()) {
        if (ticket.getStatue().equals(Status.En_cours)) {
          hasEnCoursTicket = true;
          break;
        }
      }
      if (hasEnCoursTicket) {
        throw new IllegalStateException("Cannot delete Utilisateur with existing tickets En_cours: " + id);
      }
    }
    if (!found.getTicketList().isEmpty()) {
      log.debug("ticket related with this user setted nulls", foundComplete.getId());
    }
    utilisateurRepository.deleteById(id);
  }

  public UtilisateurResponseDTO findByLogin(String login) {
    Utilisateur utilisateur = utilisateurRepository.findByLogin(login);
    return UtilisateurFactory.toResponseDTO(utilisateur);
  }

  public UtilisateurResponseDTO findByEmail(String email) {
    log.debug("Request to get Utilisateur by email: {}", email);
    Utilisateur utilisateur = utilisateurRepository.findByemail(email);
    return UtilisateurFactory.toResponseDTO(utilisateur);
  }

  public List<String> getAllNames() {
    log.debug("Request to get all names of clients: {}");
    List<Utilisateur> utilisateurs = utilisateurRepository.findAll();
    return utilisateurs.stream()
        .map(Utilisateur::getLogin)
        .collect(Collectors.toList());
  }

  public Set<Utilisateur> findAllById(List<Integer> ListChat) {

    return (Set<Utilisateur>) utilisateurRepository.findAllById(ListChat);
  }

  public Optional<Utilisateur> findById(Integer senderId) {
    return utilisateurRepository.findById(senderId);
  }

  public void updatePassword(String email, String newPassword) {
    log.debug("Requête pour mettre à jour le mot de passe pour l'e-mail : {}", email);
    Utilisateur utilisateur = utilisateurRepository.findByemail(email);
    if (utilisateur == null) {
      throw new IllegalArgumentException("Aucun utilisateur trouvé avec l'e-mail : " + email);
    }

    // Mettre à jour le mot de passe.
    // NOTE : Dans une application de production, vous devriez chiffrer le mot de
    // passe ici.
    // ex: utilisateur.setMotDePasse(passwordEncoder.encode(newPassword));
    utilisateur.setMotDePasse(newPassword);

    // La transaction se chargera de sauvegarder l'entité modifiée.
    log.info("Le mot de passe pour l'utilisateur {} a été mis à jour avec succès.", utilisateur.getLogin());
  }

  public Object getIdBylogin(String username) {
    Utilisateur utilisateur = utilisateurRepository.findByLogin(username);
    return utilisateur != null ? utilisateur.getId() : null;
  }
}