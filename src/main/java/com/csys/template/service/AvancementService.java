package com.csys.template.service;

import com.csys.template.domain.Avancement;
import com.csys.template.dto.AvancementDTO;
import com.csys.template.factory.AvancementFactory;
import com.csys.template.repository.AvancementRepository;
import com.google.common.base.Preconditions;
import java.lang.Integer;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing Avancement.
 */
@Service
@Transactional
public class AvancementService {
  private final Logger log = LoggerFactory.getLogger(AvancementService.class);

  private final AvancementRepository avancementRepository;

  public AvancementService(AvancementRepository avancementRepository) {
    this.avancementRepository=avancementRepository;
  }

  /**
   * Save a avancementDTO.
   *
   * @param avancementDTO
   * @return the persisted entity
   */
  public AvancementDTO save(AvancementDTO avancementDTO) {
    log.debug("Request to save Avancement: {}",avancementDTO);
    Avancement avancement = AvancementFactory.avancementDTOToAvancement(avancementDTO);
    avancement = avancementRepository.save(avancement);
    AvancementDTO resultDTO = AvancementFactory.avancementToAvancementDTO(avancement);
    return resultDTO;
  }

  /**
   * Update a avancementDTO.
   *
   * @param avancementDTO
   * @return the updated entity
   */
  public AvancementDTO update(AvancementDTO avancementDTO) {
    log.debug("Request to update Avancement: {}",avancementDTO);
    Avancement inBase= avancementRepository.findById(avancementDTO.getId()).orElse(null);
    Preconditions.checkArgument(inBase != null, "avancement.NotFound");
    Avancement avancement = AvancementFactory.avancementDTOToAvancement(avancementDTO);
    avancement = avancementRepository.save(avancement);
    AvancementDTO resultDTO = AvancementFactory.avancementToAvancementDTO(avancement);
    return resultDTO;
  }

  /**
   * Get one avancementDTO by id.
   *
   * @param id the id of the entity
   * @return the entity DTO
   */
  @Transactional(
      readOnly = true
  )
  public AvancementDTO findOne(Integer id) {
    log.debug("Request to get Avancement: {}",id);
    Avancement avancement= avancementRepository.findById(id).orElse(null);
    AvancementDTO dto = AvancementFactory.avancementToAvancementDTO(avancement);
    return dto;
  }

  /**
   * Get one avancement by id.
   *
   * @param id the id of the entity
   * @return the entity
   */
  @Transactional(
      readOnly = true
  )
  public Avancement findAvancement(Integer id) {
    log.debug("Request to get Avancement: {}",id);
    Avancement avancement= avancementRepository.findById(id).orElse(null);
    return avancement;
  }

  /**
   * Get all the avancements.
   *
   * @return the the list of entities
   */
  @Transactional(
      readOnly = true
  )
  public Collection<AvancementDTO> findAll() {
    log.debug("Request to get All Avancements");
    Collection<Avancement> result= avancementRepository.findAll();
    return AvancementFactory.avancementToAvancementDTOs(result);
  }

  /**
   * Delete avancement by id.
   *
   * @param id the id of the entity
   */
  public void delete(Integer id) {
    log.debug("Request to delete Avancement: {}",id);
    avancementRepository.deleteById(id);
  }
}

