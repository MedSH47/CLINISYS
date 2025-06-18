package com.csys.template.service;

import com.csys.template.domain.Equipe;
import com.csys.template.domain.QEquipe;
import com.csys.template.domain.Utilisateur;
import com.csys.template.domain.enum_identifier.Role;
import com.csys.template.dtoRequest.EquipeRequestDTO;
import com.csys.template.dtoResponse.EquipeResponseDTO;
import com.csys.template.factory.EquipeFactory;
import com.csys.template.repository.EquipeRepository;
import com.csys.template.repository.UtilisateurRepository;
import com.csys.template.util.WhereClauseBuilder;

import liquibase.pro.packaged.B;
import liquibase.pro.packaged.el;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EquipeService {
    private final Logger log = LoggerFactory.getLogger(EquipeService.class);
    private final EquipeRepository equipeRepository;
    private final UtilisateurRepository utilisateurRepository;

    public EquipeService(EquipeRepository equipeRepository, UtilisateurRepository utilisateurRepository) {
        this.equipeRepository = equipeRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    public EquipeResponseDTO save(EquipeRequestDTO equipeRequestDTO) {
        log.debug("Request to save Equipe : {}", equipeRequestDTO);
        if(equipeRepository.existsBydesignation(equipeRequestDTO.getDesignation())) {
            throw new IllegalArgumentException("equipe.AlreadyExists");
        }
        Equipe equipe = EquipeFactory.toEntity(equipeRequestDTO);
        equipe = equipeRepository.save(equipe);
        return EquipeFactory.toResponseDTO(equipe);
    }

    public EquipeResponseDTO update(Integer equipeId, EquipeRequestDTO equipeRequestDTO) {
        log.debug("Request to update Equipe : {}", equipeId);
        Equipe existingEquipe = equipeRepository.findById(equipeId)
                .orElseThrow(() -> new IllegalArgumentException("equipe.NotFound"));

        if(equipeRequestDTO.getIdChefEquipe() !=null) {
            Utilisateur newChefEquipe = utilisateurRepository.findById(equipeRequestDTO.getIdChefEquipe())
                .orElseThrow(() -> new IllegalArgumentException("chefEquipe.NotFound"));
           
             existingEquipe.setChefEquipe(newChefEquipe);

        }
        
        existingEquipe.setDesignation(equipeRequestDTO.getDesignation());
        existingEquipe.setActif(equipeRequestDTO.getActif());
        Equipe saved = equipeRepository.save(existingEquipe);
        return EquipeFactory.toResponseDTO(saved);
    }

    @Transactional(readOnly = true)
    public EquipeResponseDTO findOne(Integer id) {
        log.debug("Request to get Equipe : {}", id);
        Equipe equipe = equipeRepository.findById(id).orElse(null);
        return EquipeFactory.toResponseDTO(equipe);
    }

    @Transactional(readOnly = true)
    public List<EquipeResponseDTO> findAll(Boolean[] actifs) {
        log.debug("Request to get All Equipes");
        QEquipe qEquipe = QEquipe.equipe;
        WhereClauseBuilder builder = new WhereClauseBuilder()
        .optionalAnd(actifs, () -> qEquipe.actif.in(actifs));
        log.debug("Request to get All Equipes");
        List<Equipe> result = (List<Equipe>) equipeRepository.findAll(builder);
        return EquipeFactory.toResponseDTOs(result);
    }

    public void delete(Integer id) {
        log.debug("Request to delete Equipe : {}", id);
        Equipe equipe = equipeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("equipe.NotFound"));
        EquipeResponseDTO equipeResponseDTO = EquipeFactory.toResponseDTO(equipe);
        if (!equipeResponseDTO.getUtilisateurs().isEmpty()) {
            throw new IllegalArgumentException("equipe.HasUsers");
        }
        equipeRepository.deleteById(id);
    }
}