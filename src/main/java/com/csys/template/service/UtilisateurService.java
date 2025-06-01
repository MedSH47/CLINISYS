package com.csys.template.service;

import com.csys.template.domain.Utilisateur;
import com.csys.template.dto.UtilisateurDTO;
import com.csys.template.factory.UtilisateurFactory;
import com.csys.template.repository.UtilisateurRepository;
import com.google.common.base.Preconditions; // Keep this if you use it elsewhere, or remove

import liquibase.pro.packaged.em;

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

  public UtilisateurService(PasswordEncoder passwordEncoder, UtilisateurRepository utilisateurRepository) {
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
    Utilisateur utilisateur = UtilisateurFactory.utilisateurDTOToUtilisateur(utilisateurDTO);
    utilisateur = utilisateurRepository.save(utilisateur);
    log.info("Service: Utilisateur saved successfully with ID: {}", utilisateur.getId());
    return UtilisateurFactory.utilisateurToUtilisateurDTO(utilisateur,true);
  }

  public UtilisateurDTO update(UtilisateurDTO utilisateurDTO) {
    Integer userId = utilisateurDTO.getId();
    // Preconditions.checkArgument(userId != null, "Utilisateur ID must be provided for update.");
     if (userId == null) {
        log.error("Utilisateur ID is null in DTO for update.");
        throw new IllegalArgumentException("Utilisateur ID must be provided for update.");
    }
    log.debug("Service: Request to update Utilisateur. DTO ID: {}, DTO has photo: {}",
        userId, (utilisateurDTO.getPhoto() != null && utilisateurDTO.getPhoto().length > 0));
    if(utilisateurDTO.getPhoto() != null){
        log.debug("Service: DTO photo byte array length: {}", utilisateurDTO.getPhoto().length);
    }


    // Fetch the existing entity from the database
    Utilisateur utilisateur = utilisateurRepository.findById(userId)
        .orElseThrow(() -> {
            log.error("Utilisateur not found with id: {}", userId);
            return new IllegalArgumentException("Utilisateur not found with id: " + userId);
        });

    // Apply updates from DTO to the fetched entity
    utilisateur.setNom(utilisateurDTO.getNom());
    utilisateur.setPrenom(utilisateurDTO.getPrenom());
    utilisateur.setEmail(utilisateurDTO.getEmail());
    // Only update if DTO provides a value, or decide business logic for nulls
    if (utilisateurDTO.getNumTelephone() != null) {
        utilisateur.setNumTelephone(utilisateurDTO.getNumTelephone());
    }
    if (utilisateurDTO.getRole() != null) {
        utilisateur.setRole(utilisateurDTO.getRole());
    }
    if (utilisateurDTO.getActivite() != null) {
        utilisateur.setActivite(utilisateurDTO.getActivite());
    }
    // userCreation and dateCreation should typically not be updated.

    // Password Handling:
    // Your React code sends motDePasse as null if not changed.
    if (utilisateurDTO.getMotDePasse() != null && !utilisateurDTO.getMotDePasse().isEmpty()) {
        log.info("Service: Updating password for user ID: {}", userId);
        utilisateur.setMotDePasse(passwordEncoder.encode(utilisateurDTO.getMotDePasse()));
    } else {
        log.info("Service: Password not changed for user ID: {}. Existing password will be retained.", userId);
        // No action needed; the existing password on 'utilisateur' entity is kept.
    }

    // Photo Handling:
    // utilisateurDTO.getPhoto() will contain new photo bytes if a new file was uploaded
    // (this is set in the UtilisateurResource controller).
    // If no new file was uploaded, utilisateurDTO.getPhoto() will be null.
    if (utilisateurDTO.getPhoto() != null && utilisateurDTO.getPhoto().length > 0) {
        log.info("Service: Updating photo for user ID: {}. New photo size: {} bytes", userId, utilisateurDTO.getPhoto().length);
        utilisateur.setPhoto(utilisateurDTO.getPhoto()); // Set the new photo
    } else {
        log.info("Service: No new photo uploaded for user ID: {}. Existing photo will be preserved.", userId);
        // No action needed; the existing photo on 'utilisateur' entity is kept.
    }

    Utilisateur updatedUtilisateur = utilisateurRepository.save(utilisateur);
    log.info("Service: Utilisateur updated successfully with ID: {}", updatedUtilisateur.getId());
    return UtilisateurFactory.utilisateurToUtilisateurDTO(updatedUtilisateur,true);
  }

  // ... other existing methods (findOne, findAll, delete, etc.) ...
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
    return UtilisateurFactory.utilisateurToUtilisateurDTO(utilisateur,true);
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
    return UtilisateurFactory.utilisateurToUtilisateurDTOs(utilisateurRepository.findAll());
  }

  public void delete(Integer id) {
    log.debug("Request to delete Utilisateur: {}",id);
    utilisateurRepository.deleteById(id);
    log.info("Utilisateur deleted successfully with ID: {}", id);
  }
    public UtilisateurDTO findByemail(String email){
    Utilisateur utilisateur= utilisateurRepository.findByemail(email);
    if (utilisateur == null) {
        log.warn("No Utilisateur found with email: {}", email);
    }
    return UtilisateurFactory.utilisateurToUtilisateurDTO(utilisateur,true);
  }

   public UtilisateurDTO findByLogin(String login){
    Utilisateur utilisateur= utilisateurRepository.findByLogin(login);
    if (utilisateur == null) {
        log.warn("No Utilisateur found with email: {}", login);
    }
    return UtilisateurFactory.utilisateurToUtilisateurDTO(utilisateur,true);
  }

}