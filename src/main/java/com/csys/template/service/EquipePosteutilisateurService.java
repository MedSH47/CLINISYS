package com.csys.template.service;

import com.csys.template.domain.Equipe;
import com.csys.template.domain.EquipePosteutilisateur;
import com.csys.template.domain.EquipePosteutilisateurPK;
import com.csys.template.domain.Poste;
import com.csys.template.domain.Utilisateur;
import com.csys.template.dto.EquipePosteutilisateurDTO;
import com.csys.template.factory.EquipePosteutilisateurFactory;
import com.csys.template.repository.EquipePosteutilisateurRepository;
import com.csys.template.repository.EquipeRepository;
import com.csys.template.repository.PosteRepository;
import com.csys.template.repository.UtilisateurRepository;
import com.google.common.base.Preconditions;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing EquipePosteutilisateur.
 */
@Service
@Transactional
public class EquipePosteutilisateurService {
  private final Logger log = LoggerFactory.getLogger(EquipePosteutilisateurService.class);

  private final EquipePosteutilisateurRepository equipeposteutilisateurRepository;
  private final EquipeRepository equipeRepository;
  private final PosteRepository posteRepository;
  private final UtilisateurRepository utilisateurRepository;

  public EquipePosteutilisateurService(
      EquipePosteutilisateurRepository equipeposteutilisateurRepository,
      EquipeRepository equipeRepository,
      PosteRepository posteRepository,
      UtilisateurRepository utilisateurRepository) {
    this.equipeposteutilisateurRepository = equipeposteutilisateurRepository;
    this.equipeRepository = equipeRepository;
    this.posteRepository = posteRepository;
    this.utilisateurRepository = utilisateurRepository;
  }

  /**
   * Save a equipeposteutilisateurDTO.
   *
   * @param equipeposteutilisateurDTO
   * @return the persisted entity
   */
  public EquipePosteutilisateurDTO save(EquipePosteutilisateurDTO equipeposteutilisateurDTO) {
    log.debug("Request to save EquipePosteutilisateur: {}", equipeposteutilisateurDTO);
    EquipePosteutilisateurPK pk = equipeposteutilisateurDTO.getEquipePosteutilisateurPK();
    Preconditions.checkArgument(pk != null, "EquipePosteutilisateurPK must not be null");
    
    Equipe equipe = equipeRepository.findById(pk.getIdEquipe())
        .orElseThrow(() -> new IllegalArgumentException("Equipe with id " + pk.getIdEquipe() + " not found"));
    Poste poste = posteRepository.findById(pk.getIdPoste())
        .orElseThrow(() -> new IllegalArgumentException("Poste with id " + pk.getIdPoste() + " not found"));
    Utilisateur utilisateur = utilisateurRepository.findById(pk.getIdUtilisateur())
        .orElseThrow(() -> new IllegalArgumentException("Utilisateur with id " + pk.getIdUtilisateur() + " not found"));

    EquipePosteutilisateur equipeposteutilisateur = EquipePosteutilisateurFactory.equipeposteutilisateurDTOToEquipePosteutilisateur(
        equipeposteutilisateurDTO, equipe, poste, utilisateur);
    equipeposteutilisateur = equipeposteutilisateurRepository.save(equipeposteutilisateur);
    return EquipePosteutilisateurFactory.equipeposteutilisateurToEquipePosteutilisateurDTO(equipeposteutilisateur, false);
  }

  /**
   * Update a equipeposteutilisateurDTO.
   *
   * @param equipeposteutilisateurDTO
   * @return the updated entity
   */
  public EquipePosteutilisateurDTO update(EquipePosteutilisateurDTO equipeposteutilisateurDTO) {
    log.debug("Request to update EquipePosteutilisateur: {}", equipeposteutilisateurDTO);
    EquipePosteutilisateurPK pk = equipeposteutilisateurDTO.getEquipePosteutilisateurPK();
    EquipePosteutilisateur inBase = equipeposteutilisateurRepository.findById(pk)
        .orElseThrow(() -> new IllegalArgumentException("equipeposteutilisateur.NotFound"));
    
    Equipe equipe = equipeRepository.findById(pk.getIdEquipe())
        .orElseThrow(() -> new IllegalArgumentException("Equipe with id " + pk.getIdEquipe() + " not found"));
    Poste poste = posteRepository.findById(pk.getIdPoste())
        .orElseThrow(() -> new IllegalArgumentException("Poste with id " + pk.getIdPoste() + " not found"));
    Utilisateur utilisateur = utilisateurRepository.findById(pk.getIdUtilisateur())
        .orElseThrow(() -> new IllegalArgumentException("Utilisateur with id " + pk.getIdUtilisateur() + " not found"));

    EquipePosteutilisateur equipeposteutilisateur = EquipePosteutilisateurFactory.equipeposteutilisateurDTOToEquipePosteutilisateur(
        equipeposteutilisateurDTO, equipe, poste, utilisateur);
    equipeposteutilisateur = equipeposteutilisateurRepository.save(equipeposteutilisateur);
    return EquipePosteutilisateurFactory.equipeposteutilisateurToEquipePosteutilisateurDTO(equipeposteutilisateur, false);
  }

  /**
   * Get one equipeposteutilisateurDTO by id.
   *
   * @param id the id of the entity
   * @return the entity DTO
   */
  @Transactional(readOnly = true)
  public EquipePosteutilisateurDTO findOne(EquipePosteutilisateurPK id) {
    log.debug("Request to get EquipePosteutilisateur: {}", id);
    EquipePosteutilisateur equipeposteutilisateur = equipeposteutilisateurRepository.findById(id)
        .orElse(null);
    return EquipePosteutilisateurFactory.equipeposteutilisateurToEquipePosteutilisateurDTO(equipeposteutilisateur, true);
  }

  /**
   * Get one equipeposteutilisateur by id with details.
   *
   * @param id the id of the entity
   * @return the entity DTO with details
   */
  @Transactional(readOnly = true)
  public EquipePosteutilisateurDTO findOneWithDetails(EquipePosteutilisateurPK id) {
    log.debug("Request to get EquipePosteutilisateur with details: {}", id);
    EquipePosteutilisateur equipeposteutilisateur = equipeposteutilisateurRepository.findById(id)
        .orElse(null);
    return EquipePosteutilisateurFactory.equipeposteutilisateurToEquipePosteutilisateurDTO(equipeposteutilisateur, true);
  }

  /**
   * Get all the equipeposteutilisateurs.
   *
   * @param includeDetails whether to include equipe, poste, and utilisateur details
   * @return the list of entities
   */
  @Transactional(readOnly = true)
  public Collection<EquipePosteutilisateurDTO> findAll(boolean includeDetails) {
    log.debug("Request to get All EquipePosteutilisateurs with details: {}", includeDetails);
    Collection<EquipePosteutilisateur> result = equipeposteutilisateurRepository.findAll();
    return EquipePosteutilisateurFactory.equipeposteutilisateurToEquipePosteutilisateurDTOs(result, includeDetails);
  }

  /**
   * Delete equipeposteutilisateur by id.
   *
   * @param id the id of the entity
   */
  public void delete(EquipePosteutilisateurPK id) {
    log.debug("Request to delete EquipePosteutilisateur: {}", id);
    equipeposteutilisateurRepository.deleteById(id);
  }
}