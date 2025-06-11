package com.csys.template.service;

import com.csys.template.domain.EquipePosteutilisateur;
import com.csys.template.domain.EquipePosteutilisateurPK;
import com.csys.template.dtoRequest.EquipePosteutilisateurRequestDTO;
import com.csys.template.dtoResponse.EquipePosteutilisateurResponseDTO;
import com.csys.template.factory.EquipePosteutilisateurFactory;
import com.csys.template.repository.EquipePosteutilisateurRepository;
import com.csys.template.repository.EquipeRepository;
import com.csys.template.repository.PosteRepository;
import com.csys.template.repository.UtilisateurRepository;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EquipePosteutilisateurService {
    private final Logger log = LoggerFactory.getLogger(EquipePosteutilisateurService.class);
    private final EquipePosteutilisateurRepository equipePosteutilisateurRepository;
    private final EquipeRepository equipeRepository;
    private final PosteRepository posteRepository;
    private final UtilisateurRepository utilisateurRepository;

    public EquipePosteutilisateurService(
            EquipePosteutilisateurRepository equipePosteutilisateurRepository,
            EquipeRepository equipeRepository,
            PosteRepository posteRepository,
            UtilisateurRepository utilisateurRepository) {
        this.equipePosteutilisateurRepository = equipePosteutilisateurRepository;
        this.equipeRepository = equipeRepository;
        this.posteRepository = posteRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    public EquipePosteutilisateurResponseDTO save(EquipePosteutilisateurRequestDTO requestDTO) {
        log.debug("Request to save EquipePosteutilisateur: {}", requestDTO);
        
        Objects.requireNonNull(requestDTO.getIdEquipe(), "Equipe ID must not be null");
        Objects.requireNonNull(requestDTO.getIdPoste(), "Poste ID must not be null");
        Objects.requireNonNull(requestDTO.getIdUtilisateur(), "Utilisateur ID must not be null");

        equipeRepository.findById(requestDTO.getIdEquipe())
                .orElseThrow(() -> new IllegalArgumentException("Equipe not found"));
        posteRepository.findById(requestDTO.getIdPoste())
                .orElseThrow(() -> new IllegalArgumentException("Poste not found"));
        utilisateurRepository.findById(requestDTO.getIdUtilisateur())
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur not found"));

        EquipePosteutilisateur entity = EquipePosteutilisateurFactory.toEntity(requestDTO);
        entity = equipePosteutilisateurRepository.save(entity);
        
        return EquipePosteutilisateurFactory.toResponseDTO(entity);
    }

    @Transactional(readOnly = true)
    public EquipePosteutilisateurResponseDTO findOne(EquipePosteutilisateurPK id) {
        log.debug("Request to get EquipePosteutilisateur: {}", id);
        EquipePosteutilisateur entity = equipePosteutilisateurRepository.findById(id).orElse(null);
        return EquipePosteutilisateurFactory.toResponseDTO(entity);
    }

    @Transactional(readOnly = true)
    public List<EquipePosteutilisateurResponseDTO> findAll() {
        log.debug("Request to get All EquipePosteutilisateurs");
        return EquipePosteutilisateurFactory.toResponseDTOs(equipePosteutilisateurRepository.findAll());
    }

    public void delete(EquipePosteutilisateurPK id) {
        log.debug("Request to delete EquipePosteutilisateur: {}", id);
        equipePosteutilisateurRepository.deleteById(id);
    }
}