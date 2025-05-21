package com.csys.template.service;

import com.csys.template.domain.EquipePoste;
import com.csys.template.dto.EquipePosteDTO;
import com.csys.template.factory.EquipePosteFactory;
import com.csys.template.repository.EquipePosteRepository;
import com.google.common.base.Preconditions;
import java.lang.Integer;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing EquipePoste.
 */
@Service
@Transactional
public class EquipePosteService {
  private final Logger log = LoggerFactory.getLogger(EquipePosteService.class);

  private final EquipePosteRepository equipeposteRepository;

  public EquipePosteService(EquipePosteRepository equipeposteRepository) {
    this.equipeposteRepository=equipeposteRepository;
  }

  /**
   * Save a equipeposteDTO.
   *
   * @param equipeposteDTO
   * @return the persisted entity
   */
  public EquipePosteDTO save(EquipePosteDTO equipeposteDTO) {
    log.debug("Request to save EquipePoste: {}",equipeposteDTO);
    EquipePoste equipeposte = EquipePosteFactory.equipeposteDTOToEquipePoste(equipeposteDTO);
    equipeposte = equipeposteRepository.save(equipeposte);
    EquipePosteDTO resultDTO = EquipePosteFactory.equipeposteToEquipePosteDTO(equipeposte);
    return resultDTO;
  }

  /**
   * Update a equipeposteDTO.
   *
   * @param equipeposteDTO
   * @return the updated entity
   */
  public EquipePosteDTO update(EquipePosteDTO equipeposteDTO) {
    log.debug("Request to update EquipePoste: {}",equipeposteDTO);
    EquipePoste inBase= equipeposteRepository.findById(equipeposteDTO.getId()).orElse(null);
    Preconditions.checkArgument(inBase != null, "equipeposte.NotFound");
    EquipePoste equipeposte = EquipePosteFactory.equipeposteDTOToEquipePoste(equipeposteDTO);
    equipeposte = equipeposteRepository.save(equipeposte);
    EquipePosteDTO resultDTO = EquipePosteFactory.equipeposteToEquipePosteDTO(equipeposte);
    return resultDTO;
  }

  /**
   * Get one equipeposteDTO by id.
   *
   * @param id the id of the entity
   * @return the entity DTO
   */
  @Transactional(
      readOnly = true
  )
  public EquipePosteDTO findOne(Integer id) {
    log.debug("Request to get EquipePoste: {}",id);
    EquipePoste equipeposte= equipeposteRepository.findById(id).orElse(null);
    EquipePosteDTO dto = EquipePosteFactory.equipeposteToEquipePosteDTO(equipeposte);
    return dto;
  }

  /**
   * Get one equipeposte by id.
   *
   * @param id the id of the entity
   * @return the entity
   */
  @Transactional(
      readOnly = true
  )
  public EquipePoste findEquipePoste(Integer id) {
    log.debug("Request to get EquipePoste: {}",id);
    EquipePoste equipeposte= equipeposteRepository.findById(id).orElse(null);
    return equipeposte;
  }

  /**
   * Get all the equipepostes.
   *
   * @return the the list of entities
   */
  @Transactional(
      readOnly = true
  )
  public Collection<EquipePosteDTO> findAll() {
    log.debug("Request to get All EquipePostes");
    Collection<EquipePoste> result= equipeposteRepository.findAll();
    return EquipePosteFactory.equipeposteToEquipePosteDTOs(result);
  }

  /**
   * Delete equipeposte by id.
   *
   * @param id the id of the entity
   */
  public void delete(Integer id) {
    log.debug("Request to delete EquipePoste: {}",id);
    equipeposteRepository.deleteById(id);
  }
}

