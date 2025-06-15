package com.csys.template.service;

import com.csys.template.domain.QUtilisateur;
import com.csys.template.domain.Utilisateur;
import com.csys.template.domain.enum_identifier.Role;
import com.csys.template.domain.enum_identifier.Status;
import com.csys.template.dtoRequest.UtilisateurRequestDTO;
import com.csys.template.dtoResponse.UtilisateurResponseDTO;
import com.csys.template.factory.UtilisateurFactory;
import com.csys.template.repository.UtilisateurRepository;
import com.csys.template.util.WhereClauseBuilder;

import liquibase.pro.packaged.W;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.hibernate.annotations.Where;
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
    existing.setActif(userRequestDTO.getActif());

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
}