package com.csys.template.service;

import com.csys.template.domain.EquipePosteutilisateur;
import com.csys.template.domain.EquipePosteutilisateurPK;
import com.csys.template.dto.EquipePosteutilisateurDTO;
import com.csys.template.factory.EquipePosteutilisateurFactory;
import com.csys.template.repository.EquipePosteutilisateurRepository;
import com.csys.template.repository.EquipeRepository;
import com.csys.template.repository.PosteRepository;
import com.csys.template.repository.UtilisateurRepository;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing EquipePosteutilisateur.
 */
@Service
@Transactional
public class EquipePosteutilisateurService {
    private final Logger log = LoggerFactory.getLogger(EquipePosteutilisateurService.class);

    private final EquipePosteutilisateurRepository equipePosteutilisateurRepository;
    // Added final to other repositories for immutability
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

    /**
     * Save a equipePosteutilisateur.
     *
     * @param equipePosteutilisateurDTO the DTO to save
     * @return the persisted DTO
     */
    public EquipePosteutilisateurDTO save(EquipePosteutilisateurDTO equipePosteutilisateurDTO) {
        log.debug("Request to save EquipePosteutilisateur: {}", equipePosteutilisateurDTO);
        
        // CORRECTION: Use Objects.requireNonNull for standard validation instead of Google Preconditions.
        // Also ensure the IDs within the DTO are used for validation.
        Objects.requireNonNull(equipePosteutilisateurDTO.getIdEquipe(), "Equipe ID must not be null");
        Objects.requireNonNull(equipePosteutilisateurDTO.getIdPoste(), "Poste ID must not be null");
        Objects.requireNonNull(equipePosteutilisateurDTO.getIdUtilisateur(), "Utilisateur ID must not be null");

        // Validate that the referenced entities exist before saving the relationship.
        equipeRepository.findById(equipePosteutilisateurDTO.getIdEquipe())
                .orElseThrow(() -> new IllegalArgumentException("Equipe with id " + equipePosteutilisateurDTO.getIdEquipe() + " not found"));
        posteRepository.findById(equipePosteutilisateurDTO.getIdPoste())
                .orElseThrow(() -> new IllegalArgumentException("Poste with id " + equipePosteutilisateurDTO.getIdPoste() + " not found"));
        utilisateurRepository.findById(equipePosteutilisateurDTO.getIdUtilisateur())
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur with id " + equipePosteutilisateurDTO.getIdUtilisateur() + " not found"));

        // CORRECTION: Use the standard 'toEntity' factory method.
        EquipePosteutilisateur equipePosteutilisateur = EquipePosteutilisateurFactory.toEntity(equipePosteutilisateurDTO);
        equipePosteutilisateur = equipePosteutilisateurRepository.save(equipePosteutilisateur);
        
        // CORRECTION: Use the standard 'toDTO' factory method.
        return EquipePosteutilisateurFactory.toDTO(equipePosteutilisateur);
    }

    /**
     * Get one equipePosteutilisateur by id.
     *
     * @param id the id of the entity
     * @return the entity DTO
     */
    @Transactional(readOnly = true)
    public EquipePosteutilisateurDTO findOne(EquipePosteutilisateurPK id) {
        log.debug("Request to get EquipePosteutilisateur: {}", id);
        EquipePosteutilisateur equipePosteutilisateur = equipePosteutilisateurRepository.findById(id)
                .orElse(null);
        
        // CORRECTION: Use the standard 'toDTO' factory method. 
        // The factory now controls the level of detail, making a boolean flag here unnecessary.
        return EquipePosteutilisateurFactory.toDTO(equipePosteutilisateur);
    }

    /**
     * Get all the equipePosteutilisateurs.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<EquipePosteutilisateurDTO> findAll() {
        log.debug("Request to get All EquipePosteutilisateurs");
        Collection<EquipePosteutilisateur> result = equipePosteutilisateurRepository.findAll();
        
        // CORRECTION: Use the standard 'toDTOs' factory method.
        return EquipePosteutilisateurFactory.toDTOs(result);
    }

    /**
     * Delete equipePosteutilisateur by id.
     *
     * @param id the id of the entity
     */
    public void delete(EquipePosteutilisateurPK id) {
        log.debug("Request to delete EquipePosteutilisateur: {}", id);
        // This method was correct.
        equipePosteutilisateurRepository.deleteById(id);
    }
    
    /**
     * NOTE: The original file had an 'update' method that is functionally identical to 'save'
     * for a join table with a composite key. JPA's save() method handles both creation and updates (UPSERT).
     * If specific update logic is needed, it can be added here. For now, a single 'save' method is sufficient.
     */
}