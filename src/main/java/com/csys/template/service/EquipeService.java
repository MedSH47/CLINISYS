package com.csys.template.service;

import com.csys.template.domain.Equipe;
import com.csys.template.dto.EquipeDTO;
import com.csys.template.factory.EquipeFactory;
import com.csys.template.repository.EquipeRepository;
import com.google.common.base.Preconditions;
import java.lang.Integer;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing Equipe.
 */
@Service
@Transactional
public class EquipeService {
  private final Logger log = LoggerFactory.getLogger(EquipeService.class);

  private final EquipeRepository equipeRepository;

  public EquipeService(EquipeRepository equipeRepository) {
    this.equipeRepository=equipeRepository;
  }

  
  public EquipeDTO save(EquipeDTO equipeDTO) {
    log.debug("Request to save Equipe: {}",equipeDTO);
    Equipe equipe = EquipeFactory.equipeDTOToEquipe(equipeDTO);
    equipe = equipeRepository.save(equipe);
    EquipeDTO resultDTO = EquipeFactory.equipeToEquipeDTO(equipe);
    return resultDTO;
  }

  /**
   * Update a equipeDTO.
   *
   * @param equipeDTO
   * @return the updated entity
   */
  public EquipeDTO update(EquipeDTO equipeDTO) {
    log.debug("Request to update Equipe: {}",equipeDTO);
    Equipe inBase= equipeRepository.findById(equipeDTO.getId()).orElse(null);
    Preconditions.checkArgument(inBase != null, "equipe.NotFound");
    Equipe equipe = EquipeFactory.equipeDTOToEquipe(equipeDTO);
    equipe = equipeRepository.save(equipe);
    EquipeDTO resultDTO = EquipeFactory.equipeToEquipeDTO(equipe);
    return resultDTO;
  }

  /**
   * Get one equipeDTO by id.
   *
   * @param id the id of the entity
   * @return the entity DTO
   */
  @Transactional(
      readOnly = true
  )
  public EquipeDTO findOne(Integer id) {
    log.debug("Request to get Equipe: {}",id);
    Equipe equipe= equipeRepository.findById(id).orElse(null);
    EquipeDTO dto = EquipeFactory.equipeToEquipeDTO(equipe);
    return dto;
  }

  /**
   * Get one equipe by id.
   *
   * @param id the id of the entity
   * @return the entity
   */
  @Transactional(
      readOnly = true
  )
  public Equipe findEquipe(Integer id) {
    log.debug("Request to get Equipe: {}",id);
    Equipe equipe= equipeRepository.findById(id).orElse(null);
    return equipe;
  }

  /**
   * Get all the equipes.
   *
   * @return the the list of entities
   */
  
  
    public List<EquipeDTO> findAll() {
        List<Equipe> equipes = equipeRepository.findAll();
        equipes.forEach(e -> {
            e.getUtilisateurList().size();
            e.getTicketList().size();
        });
        return (List<EquipeDTO>) EquipeFactory.equipeToEquipeDTOs(equipes);
    }

  /**
   * Delete equipe by id.
   *
   * @param id the id of the entity
   */
  public void delete(Integer id) {
    log.debug("Request to delete Equipe: {}",id);
    equipeRepository.deleteById(id);
  }
}

