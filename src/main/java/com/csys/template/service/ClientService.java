package com.csys.template.service;

import com.csys.template.domain.Client;
import com.csys.template.dtoRequest.ClientRequestDTO;
import com.csys.template.dtoResponse.ClientResponseDTO;
import com.csys.template.factory.ClientFactory;
import com.csys.template.repository.ClientRepository;
import java.util.List;
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
        this.clientRepository = clientRepository;
    }

    public ClientResponseDTO save(ClientRequestDTO clientRequestDTO) {
        log.debug("Request to save Client : {}", clientRequestDTO);
        Client client = ClientFactory.toEntity(clientRequestDTO);
        client = clientRepository.save(client);
        return ClientFactory.toResponseDTO(client);
    }

    public ClientResponseDTO update(Integer clientId, ClientRequestDTO clientRequestDTO) {
        log.debug("Request to update Client : {}", clientId);
        Client existingClient = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("client.NotFound"));

        existingClient.setNomComplet(clientRequestDTO.getNomComplet());
        existingClient.setAdress(clientRequestDTO.getAdress());
        existingClient.setEmail(clientRequestDTO.getEmail());
        existingClient.setRegion(clientRequestDTO.getRegion());
        existingClient.setActif(clientRequestDTO.getActif());

        Client saved = clientRepository.save(existingClient);
        return ClientFactory.toResponseDTO(saved);
    }

    @Transactional(readOnly = true)
    public ClientResponseDTO findOne(Integer id) {
        log.debug("Request to get Client : {}", id);
        Client client = clientRepository.findById(id).orElse(null);
        return ClientFactory.toResponseDTO(client);
    }

    @Transactional(readOnly = true)
    public List<ClientResponseDTO> findAll() {
        log.debug("Request to get All Clients");
        List<Client> result = clientRepository.findAll();
        return ClientFactory.toResponseDTOs(result);
    }

    public void delete(Integer id) {
        log.debug("Request to delete Client: {}", id);
        clientRepository.deleteById(id);
    }
}