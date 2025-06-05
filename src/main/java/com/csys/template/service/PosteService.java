package com.csys.template.service;

import com.csys.template.domain.Poste;
import com.csys.template.domain.QPoste;
import com.csys.template.dto.PosteDTO;
import com.csys.template.factory.PosteFactory;
import com.csys.template.repository.PosteRepository;
import com.csys.template.util.WhereClauseBuilder;
import com.google.common.base.Preconditions;
import java.lang.Integer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing Poste.
 */
@Service
@Transactional
public class PosteService {
  private final Logger log = LoggerFactory.getLogger(PosteService.class);

  private final PosteRepository posteRepository;

  public PosteService(PosteRepository posteRepository) {
    this.posteRepository = posteRepository;
  }

  /**
   * Save a posteDTO.
   *
   * @param posteDTO
   * @return the persisted entity
   */
  public PosteDTO save(PosteDTO posteDTO, String user) {
    log.debug("Request to save Poste: {}", posteDTO);
    Poste poste = PosteFactory.toEntity(posteDTO, null, user);
    poste = posteRepository.save(poste);
    PosteDTO resultDTO = PosteFactory.toDTO(poste);
    return resultDTO;
  }

  /**
   * Update a posteDTO.
   *
   * @param posteDTO
   * @return the updated entity
   */
  public PosteDTO update(PosteDTO posteDTO, String user) {
    log.debug("Request to update Poste: {}", posteDTO);
    Poste inBase = posteRepository.findById(posteDTO.getId()).orElse(null);
    Preconditions.checkArgument(inBase != null, "poste.NotFound");
    Poste poste = PosteFactory.toEntity(posteDTO, inBase, user);
    poste = posteRepository.save(poste);
    PosteDTO resultDTO = PosteFactory.toDTO(poste);
    return resultDTO;
  }

  /**
   * Get one posteDTO by id.
   *
   * @param id the id of the entity
   * @return the entity DTO
   */
  @Transactional(readOnly = true)
  public PosteDTO findOne(Integer id) {
    log.debug("Request to get Poste: {}", id);
    Poste poste = posteRepository.findById(id).orElse(null);
    PosteDTO dto = PosteFactory.toDTO(poste);
    return dto;
  }

  /**
   * Get one poste by id.
   *
   * @param id the id of the entity
   * @return the entity
   */
  @Transactional(readOnly = true)
  public Poste findPoste(Integer id) {
    log.debug("Request to get Poste: {}", id);
    Poste poste = posteRepository.findById(id).orElse(null);
    return poste;
  }

  /**
   * Get all the postes.
   *
   * @return the the list of entities
   */
  @Transactional(readOnly = true)
  public List<PosteDTO> findAll(Boolean[] actifs) {
    log.debug("Request to get All Postes");

    QPoste qPoste = QPoste.poste;
    WhereClauseBuilder builder = new WhereClauseBuilder()
        .optionalAnd(actifs, () -> qPoste.actif.in(actifs));
    List<Poste> result = (List<Poste>) posteRepository.findAll(builder);
    return PosteFactory.toDTOs(result);

  }

  /**
   * Delete poste by id.
   *
   * @param id the id of the entity
   */
  public void delete(Integer id) {
    log.debug("Request to delete Poste: {}", id);
    posteRepository.deleteById(id);
  }
}
