package com.csys.template.service;

import com.csys.template.domain.Utilisateur;
import com.csys.template.dto.UtilisateurDTO;
import com.csys.template.factory.UtilisateurFactory;
import com.csys.template.repository.EquipeRepository;
import com.csys.template.repository.TicketRepository;
import com.csys.template.repository.UtilisateurRepository;
import com.csys.template.util.Helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection; // Keep if used by other methods

@Service
@Transactional
public class UtilisateurService {
 
  private final Logger log = LoggerFactory.getLogger(UtilisateurService.class);
  private final PasswordEncoder passwordEncoder;
  private final UtilisateurRepository utilisateurRepository;


 

  public UtilisateurService(PasswordEncoder passwordEncoder, UtilisateurRepository utilisateurRepository,
      EquipeRepository equipeRepository, TicketRepository ticketRepository) {
    this.passwordEncoder = passwordEncoder;
    this.utilisateurRepository = utilisateurRepository;
  }

  public UtilisateurDTO save(UtilisateurDTO utilisateurDTO) {
    log.debug("Service: Request to save Utilisateur: {}", utilisateurDTO);
    if (utilisateurDTO.getMotDePasse() != null && !utilisateurDTO.getMotDePasse().isEmpty()) {
        utilisateurDTO.setMotDePasse(passwordEncoder.encode(utilisateurDTO.getMotDePasse()));
    } else {
        // Consider if password is truly optional on creation or throw error
        log.warn("Attempting to save user with empty or null password.");
    }
    Utilisateur utilisateur = UtilisateurFactory.toEntity(utilisateurDTO);
    utilisateur = utilisateurRepository.save(utilisateur);
    log.info("Service: Utilisateur saved successfully with ID: {}", utilisateur.getId());
    return UtilisateurFactory.toDTO(utilisateur);
  }

  public UtilisateurDTO update(UtilisateurDTO utilisateurDTO) {
    Integer userId = utilisateurDTO.getId();
    if (userId == null) {
        log.error("Utilisateur ID is null in DTO for update.");
        throw new IllegalArgumentException("Utilisateur ID must be provided for update.");
    }
    log.debug("Service: Request to update Utilisateur. DTO ID: {}, has photo? {}",
        userId,
        (utilisateurDTO.getPhoto() != null && utilisateurDTO.getPhoto().length > 0)
    );

    // 1. Load the existing entity
    Utilisateur existing = utilisateurRepository.findById(userId)
        .orElseThrow(() -> {
            log.error("Utilisateur not found with id: {}", userId);
            return new IllegalArgumentException("Utilisateur not found with id: " + userId);
        });

    // 2. Map DTO -> temp entity (only fields present in DTO will be non-null)
    Utilisateur updatedFields = UtilisateurFactory.toEntity(utilisateurDTO);

    // 3. Merge all non-null values (skips static/final) into the loaded entity
    Helper.mergeNonNullFields(updatedFields, existing);

    // 4. Special handling: password
    if (utilisateurDTO.getMotDePasse() != null && !utilisateurDTO.getMotDePasse().isEmpty()) {
        log.info("Service: Updating password for user ID: {}", userId);
        existing.setMotDePasse(passwordEncoder.encode(utilisateurDTO.getMotDePasse()));
    } else {
        log.info("Service: Password not changed for user ID: {}. Keeping existing.", userId);
    }

    // 5. Special handling: photo
    if (utilisateurDTO.getPhoto() != null && utilisateurDTO.getPhoto().length > 0) {
        log.info("Service: Updating photo for user ID: {}. New size: {} bytes",
                 userId, utilisateurDTO.getPhoto().length);
        existing.setPhoto(utilisateurDTO.getPhoto());
    } else {
        log.info("Service: No new photo for user ID: {}. Keeping existing.", userId);
    }

    // 6. Save and convert back to DTO
    Utilisateur saved = utilisateurRepository.save(existing);
    log.info("Service: Utilisateur updated successfully with ID: {}", saved.getId());
    return UtilisateurFactory.toDTO(saved);
}
    @Transactional(
      readOnly = true
  )
  public UtilisateurDTO findOne(Integer id) {
    log.debug("Request to get Utilisateur: {}",id);
    Utilisateur utilisateur= utilisateurRepository.findById(id).orElse(null);
    //Preconditions.checkArgument(utilisateur != null, "utilisateur.NotFound"); // This was in your original code, good for GET
    if (utilisateur == null) {
        log.warn("No Utilisateur found with ID: {}", id);
        // Depending on requirements, you might throw an exception or return null/empty DTO
    }
    return UtilisateurFactory.toDTO(utilisateur);
  }

  @Transactional(
      readOnly = true
  )
  public Utilisateur findUtilisateur(Integer id) {
    log.debug("Request to get Utilisateur: {}",id);
    return utilisateurRepository.findById(id).orElse(null);
  }

  @Transactional(
      readOnly = true
  )
  public Collection<UtilisateurDTO> findAll() {
    log.debug("Request to get All Utilisateurs");
    return UtilisateurFactory.toDTOs(utilisateurRepository.findAll());
  }



public void delete(Integer id) {
    log.debug("Request to delete Utilisateur: {}", id);

    utilisateurRepository.deleteById(id);
    log.info("Utilisateur deleted successfully with ID: {}", id);
  }

    public UtilisateurDTO findByemail(String email){
    Utilisateur utilisateur= utilisateurRepository.findByemail(email);
    if (utilisateur == null) {
        log.warn("No Utilisateur found with email: {}", email);
    }
    return UtilisateurFactory.toDTO(utilisateur);
  }

   public UtilisateurDTO findByLogin(String login){
    Utilisateur utilisateur= utilisateurRepository.findByLogin(login);
    if (utilisateur == null) {
        log.warn("No Utilisateur found with email: {}", login);
    }
    return UtilisateurFactory.toDTO(utilisateur);
  }

}