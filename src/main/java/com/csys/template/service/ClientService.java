package com.csys.template.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csys.template.domain.Client;
import com.csys.template.dto.ClientDto;
import com.csys.template.factory.ClientFactory;
import com.csys.template.repository.ClientRepository;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Client addClient(Client entity) {
        return clientRepository.save(entity);
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client updateClient(Client entity) {
        return clientRepository.save(entity);
    }

    public ClientDto findOne(Integer id) {
        Client c = clientRepository.findOneById(id);
        return ClientFactory.clientToClientDto(c);
    }
    
    public void deleteClient(Integer id) {
        clientRepository.deleteById(id);
    }

    
}
