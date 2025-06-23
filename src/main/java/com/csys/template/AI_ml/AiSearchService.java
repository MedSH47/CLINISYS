package com.csys.template.AI_ml;

import com.csys.template.domain.*;
import com.csys.template.domain.Module;
import com.csys.template.factory.*;
import com.csys.template.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    public AiSearchService(RestTemplate restTemplate,
                           TicketRepository ticketRepository,
                           UtilisateurRepository utilisateurRepository,
                           EquipeRepository equipeRepository,
                           ModuleRepository moduleRepository,
                           PosteRepository posteRepository,
                           ClientRepository clientRepository) {
        this.restTemplate = restTemplate;
        this.ticketRepository = ticketRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.equipeRepository = equipeRepository;
        this.moduleRepository = moduleRepository;
        this.posteRepository = posteRepository;
        this.clientRepository = clientRepository;
    }

    @Transactional(readOnly = true)
    public AiSearchResponseDTO performSearch(String query) {
        String aiServiceUrl = "http://localhost:5001/llm-parse";
        Map<String, String> requestBody = Collections.singletonMap("query", query);
        AiQueryResponse aiResponse = restTemplate.postForObject(aiServiceUrl, requestBody, AiQueryResponse.class);

        if (aiResponse == null) {
            return new AiSearchResponseDTO("unknown", Collections.emptyList());
        }

        if (aiResponse.getDoumean() != null && !aiResponse.getDoumean().isEmpty()) {
            return new AiSearchResponseDTO(aiResponse.getDoumean());
        }

        if (aiResponse.getEntityType() != null && !"unknown".equals(aiResponse.getEntityType())) {
            String entityType = aiResponse.getEntityType();
            Map<String, String> entities = aiResponse.getEntities();

            List<?> data;

            if ("ticket".equals(entityType)) {
                if (entities == null || entities.isEmpty()) {
                    data = ticketRepository.findAll().stream()
                            .map(TicketFactory::toResponseDTO)
                            .collect(Collectors.toList());
                } else {
                    data = ticketRepository.findAll(TicketSpecification.findByEntities(entities)).stream()
                            .map(TicketFactory::toResponseDTO)
                            .collect(Collectors.toList());
                }

            } else if ("utilisateur".equals(entityType)) {
                if (entities == null || entities.isEmpty()) {
                    data = utilisateurRepository.findAll().stream()
                            .map(UtilisateurFactory::toResponseDTO)
                            .collect(Collectors.toList());
                } else {
                    data = utilisateurRepository.findAll(UtilisateurSpecification.findByEntities(entities)).stream()
                            .map(UtilisateurFactory::toResponseDTO)
                            .collect(Collectors.toList());
                }

            } else if ("equipe".equals(entityType)) {
                if (entities == null || entities.isEmpty()) {
                    data = equipeRepository.findAll().stream()
                            .map(EquipeFactory::toResponseDTO)
                            .collect(Collectors.toList());
                } else {
                    data = equipeRepository.findAll(GenericSpecification.findByEntities(entities)).stream()
                            .map(EquipeFactory::toResponseDTO)
                            .collect(Collectors.toList());
                }

            } else if ("module".equals(entityType)) {
                if (entities == null || entities.isEmpty()) {
                    data = moduleRepository.findAll().stream()
                            .map(ModuleFactory::toResponseDTO)
                            .collect(Collectors.toList());
                } else {
                    data = moduleRepository.findAll(GenericSpecification.findByEntities(entities)).stream()
                            .map(ModuleFactory::toResponseDTO)
                            .collect(Collectors.toList());
                }

            } else if ("poste".equals(entityType)) {
                if (entities == null || entities.isEmpty()) {
                    data = posteRepository.findAll().stream()
                            .map(PosteFactory::toResponseDTO)
                            .collect(Collectors.toList());
                } else {
                    data = posteRepository.findAll(GenericSpecification.findByEntities(entities)).stream()
                            .map(PosteFactory::toResponseDTO)
                            .collect(Collectors.toList());
                }

            } else if ("client".equals(entityType)) {
                if (entities == null || entities.isEmpty()) {
                    data = clientRepository.findAll().stream()
                            .map(ClientFactory::toResponseDTO)
                            .collect(Collectors.toList());
                } else {
                    data = clientRepository.findAll(GenericSpecification.findByEntities(entities)).stream()
                            .map(ClientFactory::toResponseDTO)
                            .collect(Collectors.toList());
                }

            } else {
                data = Collections.emptyList();
            }

            return new AiSearchResponseDTO(entityType, data);
        }

        return new AiSearchResponseDTO("unknown", Collections.emptyList());
    }
}
