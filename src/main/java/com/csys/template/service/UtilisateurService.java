package com.csys.template.service;

import com.csys.template.domain.Utilisateur;
import com.csys.template.domain.enum_identifier.Status;
import com.csys.template.dtoRequest.UtilisateurRequestDTO;
import com.csys.template.dtoResponse.UtilisateurResponseDTO;
import com.csys.template.factory.UtilisateurFactory;
import com.csys.template.repository.UtilisateurRepository;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  public UtilisateurResponseDTO save(UtilisateurRequestDTO utilisateurRequestDTO) {
    log.debug("Service: Request to save Utilisateur: {}", utilisateurRequestDTO);
    if (utilisateurRepository.findByLogin(utilisateurRequestDTO.getLogin()) != null) {
      throw new IllegalArgumentException("Login already exists: " + utilisateurRequestDTO.getLogin());

    }
    Utilisateur utilisateur = UtilisateurFactory.toEntity(utilisateurRequestDTO);
    utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
    utilisateur = utilisateurRepository.save(utilisateur);
    return UtilisateurFactory.toResponseDTO(utilisateur);
  }

  public UtilisateurResponseDTO update(Integer userId, UtilisateurRequestDTO userRequestDTO, byte[] photo) {
    log.debug("Service: Request to update Utilisateur ID: {}", userId);
    Utilisateur existing = utilisateurRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("Utilisateur not found with id: " + userId));

    existing.setNom(userRequestDTO.getNom());
    existing.setPrenom(userRequestDTO.getPrenom());
    existing.setLogin(userRequestDTO.getLogin());
    existing.setEmail(userRequestDTO.getEmail());
    existing.setNumTelephone(userRequestDTO.getNumTelephone());
    existing.setRole(userRequestDTO.getRole());
    existing.setActivite(userRequestDTO.getActivite());

    if (userRequestDTO.getMotDePasse() != null && !userRequestDTO.getMotDePasse().isEmpty()) {
      existing.setMotDePasse(passwordEncoder.encode(userRequestDTO.getMotDePasse()));
    }
    if (photo != null && photo.length > 0) {
      existing.setPhoto(photo);
    }

    Utilisateur saved = utilisateurRepository.save(existing);
    return UtilisateurFactory.toResponseDTO(saved);
  }

  @Transactional(readOnly = true)
  public UtilisateurResponseDTO findOne(Integer id) {
    log.debug("Request to get Utilisateur: {}", id);
    Utilisateur utilisateur = utilisateurRepository.findById(id).orElse(null);
    return UtilisateurFactory.toResponseDTO(utilisateur);
  }

  @Transactional(readOnly = true)
  public Collection<UtilisateurResponseDTO> findAll() {
    log.debug("Request to get All Utilisateurs");
    return UtilisateurFactory.toResponseDTOs(utilisateurRepository.findAll());
  }

  public void delete(Integer id) {
    log.debug("Request to delete Utilisateur: {}", id);
    
    utilisateurRepository.deleteById(id);
  }

  public UtilisateurResponseDTO findByLogin(String login) {
    Utilisateur utilisateur = utilisateurRepository.findByLogin(login);
    return UtilisateurFactory.toResponseDTO(utilisateur);
  }
}