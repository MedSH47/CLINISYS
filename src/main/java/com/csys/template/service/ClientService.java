package com.csys.template.service;

import com.csys.template.domain.Client;
import com.csys.template.dto.ClientDTO;
import com.csys.template.factory.ClientFactory;
import com.csys.template.repository.ClientRepository;
import com.csys.template.util.Helper;
import com.google.common.base.Preconditions;
import java.lang.Integer;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class ClientService {
  private final Logger log = LoggerFactory.getLogger(ClientService.class);

  private final ClientRepository clientRepository;

  public ClientService(ClientRepository clientRepository) {
    this.clientRepository=clientRepository;
  }


  public ClientDTO save(ClientDTO clientDTO) {
    log.debug("Request to save Client: {}",clientDTO);
    Client client = ClientFactory.toEntity(clientDTO);
    client = clientRepository.save(client);
    ClientDTO resultDTO = ClientFactory.toDTO(client);
    return resultDTO;
  }


public ClientDTO update(ClientDTO clientDTO) {
    log.debug("Request to update Client: {}", clientDTO);

    // 1. Load existing entity
    Client existingClient = clientRepository.findById(clientDTO.getId())
        .orElseThrow(() -> new IllegalArgumentException("client.NotFound"));

    // 2. Convert DTO to a temporary entity holding only the new values
    Client updatedFields = ClientFactory.toEntity(clientDTO);

    // 3. Merge non-null fields from updatedFields into existingClient
    Helper.mergeNonNullFields(updatedFields, existingClient);

    // 4. Save and return
    Client saved = clientRepository.save(existingClient);
    return ClientFactory.toDTO(saved);
}



  @Transactional(
      readOnly = true
  )
  public ClientDTO findOne(Integer id) {
    log.debug("Request to get Client: {}",id);
    Client client= clientRepository.findById(id).orElse(null);
    ClientDTO dto = ClientFactory.toDTO(client);
    return dto;
  }

  @Transactional(
      readOnly = true
  )
  public Client findClient(Integer id) {
    log.debug("Request to get Client: {}",id);
    Client client= clientRepository.findById(id).orElse(null);
    return client;
  }

  @Transactional(
      readOnly = true
  )
  public Collection<ClientDTO> findAll() {
    log.debug("Request to get All Clients");
    Collection<Client> result= clientRepository.findAll();
    return ClientFactory.toDTOs(result);
  }


  public void delete(Integer id) {
    log.debug("Request to delete Client: {}",id);
    clientRepository.deleteById(id);
  }
}

