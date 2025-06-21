package com.csys.template.AI_ml;

import com.csys.template.domain.*;
import com.csys.template.domain.Module;
import com.csys.template.factory.*;
import com.csys.template.repository.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // <-- IMPORT ADDED
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AiSearchService {

    private final RestTemplate restTemplate;
    private final TicketRepository ticketRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final EquipeRepository equipeRepository;
    private final ModuleRepository moduleRepository;
    private final PosteRepository posteRepository;
    private final ClientRepository clientRepository;

    public AiSearchService(RestTemplate restTemplate, TicketRepository ticketRepository, UtilisateurRepository utilisateurRepository, EquipeRepository equipeRepository, ModuleRepository moduleRepository, PosteRepository posteRepository, ClientRepository clientRepository) {
        this.restTemplate = restTemplate;
        this.ticketRepository = ticketRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.equipeRepository = equipeRepository;
        this.moduleRepository = moduleRepository;
        this.posteRepository = posteRepository;
        this.clientRepository = clientRepository;
    }

    @Transactional(readOnly = true) // <-- FIX: ANNOTATION ADDED
    public AiSearchResponseDTO performSearch(String query) {
        String aiServiceUrl = "http://localhost:5001/parse-query";
        Map<String, String> requestBody = Collections.singletonMap("query", query);
        AiQueryResponse aiResponse = restTemplate.postForObject(aiServiceUrl, requestBody, AiQueryResponse.class);

        if (aiResponse == null || aiResponse.getEntityType() == null || "unknown".equals(aiResponse.getEntityType())) {
            return new AiSearchResponseDTO("unknown", Collections.emptyList());
        }

        String entityType = aiResponse.getEntityType();
        Map<String, String> entities = aiResponse.getEntities();
        List<?> data;

        switch (entityType) {
            case "ticket":
                Specification<Ticket> ticketSpec = TicketSpecification.findByEntities(entities);
                data = ticketRepository.findAll(ticketSpec).stream().map(TicketFactory::toResponseDTO).collect(Collectors.toList());
                break;
            case "utilisateur":
                Specification<Utilisateur> userSpec = UtilisateurSpecification.findByEntities(entities);
                data = utilisateurRepository.findAll(userSpec).stream().map(UtilisateurFactory::toResponseDTO).collect(Collectors.toList());
                break;
            case "equipe":
                Specification<Equipe> equipeSpec = GenericSpecification.findByEntities(entities);
                data = equipeRepository.findAll(equipeSpec).stream().map(EquipeFactory::toResponseDTO).collect(Collectors.toList());
                break;
            case "module":
                Specification<Module> moduleSpec = GenericSpecification.findByEntities(entities);
                data = moduleRepository.findAll(moduleSpec).stream().map(ModuleFactory::toResponseDTO).collect(Collectors.toList());
                break;
            case "poste":
                Specification<Poste> posteSpec = GenericSpecification.findByEntities(entities);
                data = posteRepository.findAll(posteSpec).stream().map(PosteFactory::toResponseDTO).collect(Collectors.toList());
                break;
            case "client":
                Specification<Client> clientSpec = GenericSpecification.findByEntities(entities);
                data = clientRepository.findAll(clientSpec).stream().map(ClientFactory::toResponseDTO).collect(Collectors.toList());
                break;
            default:
                data = Collections.emptyList();
                break;
        }
        
        return new AiSearchResponseDTO(entityType, data);
    }
}