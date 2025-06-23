package com.csys.template.AI_ml;

import com.csys.template.domain.*;
import com.csys.template.domain.Module;
import com.csys.template.factory.*;
import com.csys.template.repository.*;
import org.springframework.data.jpa.domain.Specification;
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

    public AiSearchService(RestTemplate restTemplate, TicketRepository ticketRepository, UtilisateurRepository utilisateurRepository, EquipeRepository equipeRepository, ModuleRepository moduleRepository, PosteRepository posteRepository, ClientRepository clientRepository) {
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

        // --- ADD THIS LOGIC TO HANDLE "did you mean" ---
        if (aiResponse.getDoumean() != null && !aiResponse.getDoumean().isEmpty()) {
            return new AiSearchResponseDTO(aiResponse.getDoumean());
        }
        // --- END OF NEW LOGIC ---

        if (aiResponse.getEntityType() != null && !"unknown".equals(aiResponse.getEntityType())) {
             String entityType = aiResponse.getEntityType();
             Map<String, String> entities = aiResponse.getEntities();
             
             if (entities == null || entities.isEmpty()) {
                return new AiSearchResponseDTO(entityType, Collections.emptyList());
             }

             List<?> data;
             switch (entityType) {
                case "ticket":
                    data = ticketRepository.findAll(TicketSpecification.findByEntities(entities)).stream().map(TicketFactory::toResponseDTO).collect(Collectors.toList());
                    break;
                case "utilisateur":
                    data = utilisateurRepository.findAll(UtilisateurSpecification.findByEntities(entities)).stream().map(UtilisateurFactory::toResponseDTO).collect(Collectors.toList());
                    break;
                case "equipe":
                    data = equipeRepository.findAll(GenericSpecification.findByEntities(entities)).stream().map(EquipeFactory::toResponseDTO).collect(Collectors.toList());
                    break;
                case "module":
                    data = moduleRepository.findAll(GenericSpecification.findByEntities(entities)).stream().map(ModuleFactory::toResponseDTO).collect(Collectors.toList());
                    break;
                case "poste":
                    data = posteRepository.findAll(GenericSpecification.findByEntities(entities)).stream().map(PosteFactory::toResponseDTO).collect(Collectors.toList());
                    break;
                case "client":
                    data = clientRepository.findAll(GenericSpecification.findByEntities(entities)).stream().map(ClientFactory::toResponseDTO).collect(Collectors.toList());
                    break;
                default:
                    data = Collections.emptyList();
                    break;
            }
            return new AiSearchResponseDTO(entityType, data);
        }
        
        return new AiSearchResponseDTO("unknown", Collections.emptyList());
    }
}