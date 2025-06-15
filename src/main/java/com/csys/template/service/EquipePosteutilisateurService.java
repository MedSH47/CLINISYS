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
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

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

  public EquipePosteutilisateurResponseDTO save(EquipePosteutilisateurRequestDTO dto) {
    log.debug("Request to save EquipePosteutilisateur: {}", dto);

    // 1. validate
    Objects.requireNonNull(dto.getIdEquipe(), "Equipe ID must not be null");
    Objects.requireNonNull(dto.getIdPoste(), "Poste ID must not be null");
    Objects.requireNonNull(dto.getIdUtilisateur(), "Utilisateur ID must not be null");

    equipeRepository.findById(dto.getIdEquipe())
      .orElseThrow(() -> new IllegalArgumentException("Equipe not found"));
    posteRepository.findById(dto.getIdPoste())
      .orElseThrow(() -> new IllegalArgumentException("Poste not found"));
    utilisateurRepository.findById(dto.getIdUtilisateur())
      .orElseThrow(() -> new IllegalArgumentException("Utilisateur not found"));

    // 2. build the composite PK
    EquipePosteutilisateurPK pk = new EquipePosteutilisateurPK(
      dto.getIdPoste(),
      dto.getIdUtilisateur(),
      dto.getIdEquipe()
    );

    // 3. EXISTENCE CHECK
    if (equipePosteutilisateurRepository.existsById(pk)) {
      throw new IllegalStateException(
        String.format("Relation already exists: [equipe=%d, poste=%d, utilisateur=%d]",
          dto.getIdEquipe(), dto.getIdPoste(), dto.getIdUtilisateur())
      );
    }

    // 4. map DTO → Entity (your factory does this)
    EquipePosteutilisateur entity = EquipePosteutilisateurFactory.toEntity(dto);

    // 5. save the one and only time
    entity = equipePosteutilisateurRepository.save(entity);

    // 6. return DTO
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
        if (!equipePosteutilisateurRepository.existsById(id)) {
            throw new EntityNotFoundException("EquipePosteutilisateur not found with id: " + id);
        }
        equipePosteutilisateurRepository.deleteById(id);
    }

    
    @Transactional(readOnly = true)
    public List<EquipePosteutilisateurResponseDTO> findByEquipeId(Integer equipeId) {
        // Assuming your EquipePosteutilisateur entity has a relationship to Equipe,
        return equipePosteutilisateurRepository.findByEquipeId(equipeId).stream() // <--- THIS METHOD NEEDS TO EXIST IN YOUR REPOSITORY
                .map(EquipePosteutilisateurFactory::toResponseDTO)
                .collect(Collectors.toList());
    }

       
}