package com.csys.template.service;

import com.csys.template.domain.DocumentJointes;
import com.csys.template.dto.DocumentJointesDTO;
import com.csys.template.factory.DocumentJointesFactory;
import com.csys.template.repository.DocumentJointesRepository;
import com.google.common.base.Preconditions;
import java.lang.Integer;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing DocumentJointes.
 */
@Service
@Transactional
public class DocumentJointesService {
  private final Logger log = LoggerFactory.getLogger(DocumentJointesService.class);

  private final DocumentJointesRepository documentjointesRepository;

  public DocumentJointesService(DocumentJointesRepository documentjointesRepository) {
    this.documentjointesRepository=documentjointesRepository;
  }

  /**
   * Save a documentjointesDTO.
   *
   * @param documentjointesDTO
   * @return the persisted entity
   */
  public DocumentJointesDTO save(DocumentJointesDTO documentjointesDTO) {
    log.debug("Request to save DocumentJointes: {}",documentjointesDTO);
    DocumentJointes documentjointes = DocumentJointesFactory.documentjointesDTOToDocumentJointes(documentjointesDTO);
    documentjointes = documentjointesRepository.save(documentjointes);
    DocumentJointesDTO resultDTO = DocumentJointesFactory.documentjointesToDocumentJointesDTO(documentjointes);
    return resultDTO;
  }

  /**
   * Update a documentjointesDTO.
   *
   * @param documentjointesDTO
   * @return the updated entity
   */
  public DocumentJointesDTO update(DocumentJointesDTO documentjointesDTO) {
    log.debug("Request to update DocumentJointes: {}",documentjointesDTO);
    DocumentJointes inBase= documentjointesRepository.findById(documentjointesDTO.getId()).orElse(null);
    Preconditions.checkArgument(inBase != null, "documentjointes.NotFound");
    DocumentJointes documentjointes = DocumentJointesFactory.documentjointesDTOToDocumentJointes(documentjointesDTO);
    documentjointes = documentjointesRepository.save(documentjointes);
    DocumentJointesDTO resultDTO = DocumentJointesFactory.documentjointesToDocumentJointesDTO(documentjointes);
    return resultDTO;
  }

  /**
   * Get one documentjointesDTO by id.
   *
   * @param id the id of the entity
   * @return the entity DTO
   */
  @Transactional(
      readOnly = true
  )
  public DocumentJointesDTO findOne(Integer id) {
    log.debug("Request to get DocumentJointes: {}",id);
    DocumentJointes documentjointes= documentjointesRepository.findById(id).orElse(null);
    DocumentJointesDTO dto = DocumentJointesFactory.documentjointesToDocumentJointesDTO(documentjointes);
    return dto;
  }

  /**
   * Get one documentjointes by id.
   *
   * @param id the id of the entity
   * @return the entity
   */
  @Transactional(
      readOnly = true
  )
  public DocumentJointes findDocumentJointes(Integer id) {
    log.debug("Request to get DocumentJointes: {}",id);
    DocumentJointes documentjointes= documentjointesRepository.findById(id).orElse(null);
    return documentjointes;
  }

  /**
   * Get all the documentjointess.
   *
   * @return the the list of entities
   */
  @Transactional(
      readOnly = true
  )
  public Collection<DocumentJointesDTO> findAll() {
    log.debug("Request to get All DocumentJointess");
    Collection<DocumentJointes> result= documentjointesRepository.findAll();
    return DocumentJointesFactory.documentjointesToDocumentJointesDTOs(result);
  }

  /**
   * Delete documentjointes by id.
   *
   * @param id the id of the entity
   */
  public void delete(Integer id) {
    log.debug("Request to delete DocumentJointes: {}",id);
    documentjointesRepository.deleteById(id);
  }
}

