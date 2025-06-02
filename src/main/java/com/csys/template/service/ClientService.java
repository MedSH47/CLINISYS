package com.csys.template.service;

import com.csys.template.domain.Client;
import com.csys.template.dto.ClientDTO;
import com.csys.template.factory.ClientFactory;
import com.csys.template.repository.ClientRepository;
import com.google.common.base.Preconditions;
import java.lang.Integer;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing Client.
 */
@Service
@Transactional
public class ClientService {
  private final Logger log = LoggerFactory.getLogger(ClientService.class);

  private final ClientRepository clientRepository;

  public ClientService(ClientRepository clientRepository) {
    this.clientRepository=clientRepository;
  }

  /**
   * Save a clientDTO.
   *
   * @param clientDTO
   * @return the persisted entity
   */
  public ClientDTO save(ClientDTO clientDTO) {
    log.debug("Request to save Client: {}",clientDTO);
    Client client = ClientFactory.toEntity(clientDTO);
    client = clientRepository.save(client);
    ClientDTO resultDTO = ClientFactory.toDTO(client);
    return resultDTO;
  }

  /**
   * Update a clientDTO.
   *
   * @param clientDTO
   * @return the updated entity
   */
  public ClientDTO update(ClientDTO clientDTO) {
    log.debug("Request to update Client: {}",clientDTO);
    Client inBase= clientRepository.findById(clientDTO.getId()).orElse(null);
    Preconditions.checkArgument(inBase != null, "client.NotFound");
    Client client = ClientFactory.toEntity(clientDTO);
    client = clientRepository.save(client);
    ClientDTO resultDTO = ClientFactory.toDTO(client);
    return resultDTO;
  }

  /**
   * Get one clientDTO by id.
   *
   * @param id the id of the entity
   * @return the entity DTO
   */
  @Transactional(
      readOnly = true
  )
  public ClientDTO findOne(Integer id) {
    log.debug("Request to get Client: {}",id);
    Client client= clientRepository.findById(id).orElse(null);
    ClientDTO dto = ClientFactory.toDTO(client);
    return dto;
  }

  /**
   * Get one client by id.
   *
   * @param id the id of the entity
   * @return the entity
   */
  @Transactional(
      readOnly = true
  )
  public Client findClient(Integer id) {
    log.debug("Request to get Client: {}",id);
    Client client= clientRepository.findById(id).orElse(null);
    return client;
  }

  /**
   * Get all the clients.
   *
   * @return the the list of entities
   */
  @Transactional(
      readOnly = true
  )
  public Collection<ClientDTO> findAll() {
    log.debug("Request to get All Clients");
    Collection<Client> result= clientRepository.findAll();
    return ClientFactory.toDTOs(result);
  }

  /**
   * Delete client by id.
   *
   * @param id the id of the entity
   */
  public void delete(Integer id) {
    log.debug("Request to delete Client: {}",id);
    clientRepository.deleteById(id);
  }
}

