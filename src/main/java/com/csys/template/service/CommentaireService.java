package com.csys.template.service;

import com.csys.template.domain.Commentaire;
import com.csys.template.dto.CommentaireDTO;
import com.csys.template.factory.CommentaireFactory;
import com.csys.template.repository.CommentaireRepository;
import com.csys.template.util.Helper;
import com.google.common.base.Preconditions;
import java.lang.Integer;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing Commentaire.
 */
@Service
@Transactional
public class CommentaireService {
  private final Logger log = LoggerFactory.getLogger(CommentaireService.class);

  private final CommentaireRepository commentaireRepository;

  public CommentaireService(CommentaireRepository commentaireRepository) {
    this.commentaireRepository=commentaireRepository;
  }

  /**
   * Save a commentaireDTO.
   *
   * @param commentaireDTO
   * @return the persisted entity
   */
  public CommentaireDTO save(CommentaireDTO commentaireDTO) {
    log.debug("Request to save Commentaire: {}",commentaireDTO);
    Commentaire commentaire = CommentaireFactory.toEntity(commentaireDTO);
    commentaire = commentaireRepository.save(commentaire);
    CommentaireDTO resultDTO = CommentaireFactory.toDTO(commentaire);
    return resultDTO;
  }

  /**
   * Update a commentaireDTO.
   *
   * @param commentaireDTO
   * @return the updated entity
   */
public CommentaireDTO update(CommentaireDTO commentaireDTO) {
    log.debug("Request to update Commentaire: {}", commentaireDTO);

    // 1. Fetch existing entity, fail if not found
    Commentaire existing = commentaireRepository.findById(commentaireDTO.getId())
        .orElseThrow(() -> new IllegalArgumentException("commentaire.NotFound"));

    // 2. Build a temp entity containing only the incoming DTO values
    Commentaire updatedFields = CommentaireFactory.toEntity(commentaireDTO);

    // 3. Merge non-null fields (skipping static/final) into the existing entity
    Helper.mergeNonNullFields(updatedFields, existing);

    // 4. Persist and return the updated DTO
    Commentaire saved = commentaireRepository.save(existing);
    return CommentaireFactory.toDTO(saved);
}
  /**
   * Get one commentaireDTO by id.
   *
   * @param id the id of the entity
   * @return the entity DTO
   */
  @Transactional(
      readOnly = true
  )
  public CommentaireDTO findOne(Integer id) {
    log.debug("Request to get Commentaire: {}",id);
    Commentaire commentaire= commentaireRepository.findById(id).orElse(null);
    CommentaireDTO dto = CommentaireFactory.toDTO(commentaire);
    return dto;
  }

  /**
   * Get one commentaire by id.
   *
   * @param id the id of the entity
   * @return the entity
   */
  @Transactional(
      readOnly = true
  )
  public Commentaire findCommentaire(Integer id) {
    log.debug("Request to get Commentaire: {}",id);
    Commentaire commentaire= commentaireRepository.findById(id).orElse(null);
    return commentaire;
  }

  /**
   * Get all the commentaires.
   *
   * @return the the list of entities
   */
  @Transactional(
      readOnly = true
  )
  public Collection<CommentaireDTO> findAll() {
    log.debug("Request to get All Commentaires");
    Collection<Commentaire> result= commentaireRepository.findAll();
    return CommentaireFactory.toDTOs(result);
  }

  /**
   * Delete commentaire by id.
   *
   * @param id the id of the entity
   */
  public void delete(Integer id) {
    log.debug("Request to delete Commentaire: {}",id);
    commentaireRepository.deleteById(id);
  }
}

