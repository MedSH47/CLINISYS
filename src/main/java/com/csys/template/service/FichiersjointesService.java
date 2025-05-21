package com.csys.template.service;

import com.csys.template.domain.Fichiersjointes;
import com.csys.template.dto.FichiersjointesDTO;
import com.csys.template.factory.FichiersjointesFactory;
import com.csys.template.repository.FichiersjointesRepository;
import com.google.common.base.Preconditions;
import java.lang.Integer;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing Fichiersjointes.
 */
@Service
@Transactional
public class FichiersjointesService {
  private final Logger log = LoggerFactory.getLogger(FichiersjointesService.class);

  private final FichiersjointesRepository fichiersjointesRepository;

  public FichiersjointesService(FichiersjointesRepository fichiersjointesRepository) {
    this.fichiersjointesRepository=fichiersjointesRepository;
  }

  /**
   * Save a fichiersjointesDTO.
   *
   * @param fichiersjointesDTO
   * @return the persisted entity
   */
  public FichiersjointesDTO save(FichiersjointesDTO fichiersjointesDTO) {
    log.debug("Request to save Fichiersjointes: {}",fichiersjointesDTO);
    Fichiersjointes fichiersjointes = FichiersjointesFactory.fichiersjointesDTOToFichiersjointes(fichiersjointesDTO);
    fichiersjointes = fichiersjointesRepository.save(fichiersjointes);
    FichiersjointesDTO resultDTO = FichiersjointesFactory.fichiersjointesToFichiersjointesDTO(fichiersjointes);
    return resultDTO;
  }

  /**
   * Update a fichiersjointesDTO.
   *
   * @param fichiersjointesDTO
   * @return the updated entity
   */
  public FichiersjointesDTO update(FichiersjointesDTO fichiersjointesDTO) {
    log.debug("Request to update Fichiersjointes: {}",fichiersjointesDTO);
    Fichiersjointes inBase= fichiersjointesRepository.findById(fichiersjointesDTO.getId()).orElse(null);
    Preconditions.checkArgument(inBase != null, "fichiersjointes.NotFound");
    Fichiersjointes fichiersjointes = FichiersjointesFactory.fichiersjointesDTOToFichiersjointes(fichiersjointesDTO);
    fichiersjointes = fichiersjointesRepository.save(fichiersjointes);
    FichiersjointesDTO resultDTO = FichiersjointesFactory.fichiersjointesToFichiersjointesDTO(fichiersjointes);
    return resultDTO;
  }

  /**
   * Get one fichiersjointesDTO by id.
   *
   * @param id the id of the entity
   * @return the entity DTO
   */
  @Transactional(
      readOnly = true
  )
  public FichiersjointesDTO findOne(Integer id) {
    log.debug("Request to get Fichiersjointes: {}",id);
    Fichiersjointes fichiersjointes= fichiersjointesRepository.findById(id).orElse(null);
    FichiersjointesDTO dto = FichiersjointesFactory.fichiersjointesToFichiersjointesDTO(fichiersjointes);
    return dto;
  }

  /**
   * Get one fichiersjointes by id.
   *
   * @param id the id of the entity
   * @return the entity
   */
  @Transactional(
      readOnly = true
  )
  public Fichiersjointes findFichiersjointes(Integer id) {
    log.debug("Request to get Fichiersjointes: {}",id);
    Fichiersjointes fichiersjointes= fichiersjointesRepository.findById(id).orElse(null);
    return fichiersjointes;
  }

  /**
   * Get all the fichiersjointess.
   *
   * @return the the list of entities
   */
  @Transactional(
      readOnly = true
  )
  public Collection<FichiersjointesDTO> findAll() {
    log.debug("Request to get All Fichiersjointess");
    Collection<Fichiersjointes> result= fichiersjointesRepository.findAll();
    return FichiersjointesFactory.fichiersjointesToFichiersjointesDTOs(result);
  }

  /**
   * Delete fichiersjointes by id.
   *
   * @param id the id of the entity
   */
  public void delete(Integer id) {
    log.debug("Request to delete Fichiersjointes: {}",id);
    fichiersjointesRepository.deleteById(id);
  }
}

