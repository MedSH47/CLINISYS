package com.csys.template.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csys.template.domain.QUtilisateur;
import com.csys.template.domain.Utilisateur;
import com.csys.template.domain.enum_identifier.Role;
import com.csys.template.domain.enum_identifier.Status;
import com.csys.template.dtoRequest.UtilisateurRequestDTO;
import com.csys.template.dtoResponse.UtilisateurResponseDTO;
import com.csys.template.factory.UtilisateurFactory;
import com.csys.template.repository.UtilisateurRepository;
import com.csys.template.util.WhereClauseBuilder;

@Service
@Transactional
public class UtilisateurService {

  private final Logger log = LoggerFactory.getLogger(UtilisateurService.class);
  private final UtilisateurRepository utilisateurRepository;

  public UtilisateurService(UtilisateurRepository utilisateurRepository) {
    this.utilisateurRepository = utilisateurRepository;
  }

  public CompletableFuture<Utilisateur> saveUserWithPhoto(UtilisateurRequestDTO dto, byte[] photoBytes) {
    // Here you would perform long-running operations like resizing the photo,
    // saving it to cloud storage, etc.
    // For this example, we just set the bytes.

    dto.setPhoto(photoBytes);
    Utilisateur utilisateur = UtilisateurFactory.toEntity(dto);
    utilisateur = utilisateurRepository.save(utilisateur);

    return CompletableFuture.completedFuture(utilisateur);
  }
  // ... other methods

  public UtilisateurResponseDTO save(UtilisateurRequestDTO utilisateurRequestDTO) {
    log.debug("Service: Request to save Utilisateur: {}", utilisateurRequestDTO);
    if (utilisateurRepository.findByLogin(utilisateurRequestDTO.getLogin()) != null) {
      throw new IllegalArgumentException("Login already exists: " + utilisateurRequestDTO.getLogin());

    }
    Utilisateur utilisateur = UtilisateurFactory.toEntity(utilisateurRequestDTO);
    utilisateur = utilisateurRepository.save(utilisateur);
    return UtilisateurFactory.toResponseDTO(utilisateur);
  }

  public UtilisateurResponseDTO update(Integer userId, UtilisateurRequestDTO userRequestDTO, byte[] photo) {
    log.debug("Service: Request to update Utilisateur ID: {}", userId);
    Utilisateur existing = utilisateurRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("Utilisateur not found with id: " + userId));
    if (photo != null) {
      userRequestDTO.setPhoto(photo);
    }
    UtilisateurFactory.updateFromDTO(existing, userRequestDTO);
    utilisateurRepository.save(existing);
    return UtilisateurFactory.toResponseDTO(existing);
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

  public List<String> getAllNames() {
    log.debug("Request to get all names of clients: {}");
    List<Utilisateur> utilisateurs = utilisateurRepository.findAll();
    return utilisateurs.stream()
        .map(Utilisateur::getLogin)
        .collect(Collectors.toList());
  }

  public Set<Utilisateur> findAllById(List<Integer> ListChat){

    return (Set<Utilisateur>) utilisateurRepository.findAllById(ListChat);
  }

  public Optional<Utilisateur> findById(Integer senderId) {
    return utilisateurRepository.findById(senderId);
  }
  
}