package com.csys.template.service;

import com.csys.template.domain.Ticketfichier;
import com.csys.template.dto.TicketfichierDTO;
import com.csys.template.factory.TicketfichierFactory;
import com.csys.template.repository.TicketfichierRepository;
import com.google.common.base.Preconditions;
import java.lang.Integer;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing Ticketfichier.
 */
@Service
@Transactional
public class TicketfichierService {
  private final Logger log = LoggerFactory.getLogger(TicketfichierService.class);

  private final TicketfichierRepository ticketfichierRepository;

  public TicketfichierService(TicketfichierRepository ticketfichierRepository) {
    this.ticketfichierRepository=ticketfichierRepository;
  }

  /**
   * Save a ticketfichierDTO.
   *
   * @param ticketfichierDTO
   * @return the persisted entity
   */
  public TicketfichierDTO save(TicketfichierDTO ticketfichierDTO) {
    log.debug("Request to save Ticketfichier: {}",ticketfichierDTO);
    Ticketfichier ticketfichier = TicketfichierFactory.ticketfichierDTOToTicketfichier(ticketfichierDTO);
    ticketfichier = ticketfichierRepository.save(ticketfichier);
    TicketfichierDTO resultDTO = TicketfichierFactory.ticketfichierToTicketfichierDTO(ticketfichier);
    return resultDTO;
  }

  /**
   * Update a ticketfichierDTO.
   *
   * @param ticketfichierDTO
   * @return the updated entity
   */
  public TicketfichierDTO update(TicketfichierDTO ticketfichierDTO) {
    log.debug("Request to update Ticketfichier: {}",ticketfichierDTO);
    Ticketfichier inBase= ticketfichierRepository.findById(ticketfichierDTO.getId()).orElse(null);
    Preconditions.checkArgument(inBase != null, "ticketfichier.NotFound");
    Ticketfichier ticketfichier = TicketfichierFactory.ticketfichierDTOToTicketfichier(ticketfichierDTO);
    ticketfichier = ticketfichierRepository.save(ticketfichier);
    TicketfichierDTO resultDTO = TicketfichierFactory.ticketfichierToTicketfichierDTO(ticketfichier);
    return resultDTO;
  }

  /**
   * Get one ticketfichierDTO by id.
   *
   * @param id the id of the entity
   * @return the entity DTO
   */
  @Transactional(
      readOnly = true
  )
  public TicketfichierDTO findOne(Integer id) {
    log.debug("Request to get Ticketfichier: {}",id);
    Ticketfichier ticketfichier= ticketfichierRepository.findById(id).orElse(null);
    TicketfichierDTO dto = TicketfichierFactory.ticketfichierToTicketfichierDTO(ticketfichier);
    return dto;
  }

  /**
   * Get one ticketfichier by id.
   *
   * @param id the id of the entity
   * @return the entity
   */
  @Transactional(
      readOnly = true
  )
  public Ticketfichier findTicketfichier(Integer id) {
    log.debug("Request to get Ticketfichier: {}",id);
    Ticketfichier ticketfichier= ticketfichierRepository.findById(id).orElse(null);
    return ticketfichier;
  }

  /**
   * Get all the ticketfichiers.
   *
   * @return the the list of entities
   */
  @Transactional(
      readOnly = true
  )
  public Collection<TicketfichierDTO> findAll() {
    log.debug("Request to get All Ticketfichiers");
    Collection<Ticketfichier> result= ticketfichierRepository.findAll();
    return TicketfichierFactory.ticketfichierToTicketfichierDTOs(result);
  }

  /**
   * Delete ticketfichier by id.
   *
   * @param id the id of the entity
   */
  public void delete(Integer id) {
    log.debug("Request to delete Ticketfichier: {}",id);
    ticketfichierRepository.deleteById(id);
  }
}

