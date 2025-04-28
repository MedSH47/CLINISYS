package com.csys.template.service;

import com.csys.template.domain.Utilisateur;
import com.csys.template.dto.UtilisateurDTO;
import com.csys.template.factory.UtilisateurFactory;
import com.csys.template.repository.UtilisateurRepository;
import com.google.common.base.Preconditions;
import java.lang.Integer;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UtilisateurService {
  private final Logger log = LoggerFactory.getLogger(UtilisateurService.class);

  @Autowired
  private  UtilisateurRepository utilisateurRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public UtilisateurDTO save(UtilisateurDTO utilisateurDTO) {
    log.debug("Request to save Utilisateur: {}",utilisateurDTO);
    Utilisateur utilisateur = UtilisateurFactory.utilisateurDTOToUtilisateur(utilisateurDTO);
    utilisateur = utilisateurRepository.save(utilisateur);
    UtilisateurDTO resultDTO = UtilisateurFactory.utilisateurToUtilisateurDTO(utilisateur);
    return resultDTO;
  }

  public ResponseEntity<?> createUtilisateur(Utilisateur utilisateur) {
        if (utilisateurRepository.existsBylogin(utilisateur.getLogin())) {
            return ResponseEntity.badRequest().body("User already exists");
        }
        if (utilisateur.getId() == null) {
            utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
            utilisateurRepository.save(utilisateur);
            return ResponseEntity.ok().body(utilisateur.getLogin() + " created successfully");
        }
        return ResponseEntity.badRequest().body("User must not have ID");
    }

    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    public Optional<Utilisateur> getUtilisateurById(Integer id) {
        return utilisateurRepository.findById(id);
    }

 
  public UtilisateurDTO update(UtilisateurDTO utilisateurDTO) {
    log.debug("Request to update Utilisateur: {}",utilisateurDTO);
    Utilisateur inBase= utilisateurRepository.findById(utilisateurDTO.getId()).orElse(null);
    Preconditions.checkArgument(inBase != null, "utilisateur.NotFound");
    Utilisateur utilisateur = UtilisateurFactory.utilisateurDTOToUtilisateur(utilisateurDTO);
    utilisateur = utilisateurRepository.save(utilisateur);
    UtilisateurDTO resultDTO = UtilisateurFactory.utilisateurToUtilisateurDTO(utilisateur);
    return resultDTO;
  }


  public UtilisateurDTO findOne(Integer id) {
    log.debug("Request to get Utilisateur: {}",id);
    Utilisateur utilisateur= utilisateurRepository.findById(id).orElse(null);
    UtilisateurDTO dto = UtilisateurFactory.utilisateurToUtilisateurDTO(utilisateur);
    return dto;
  }


  public Utilisateur findUtilisateur(Integer id) {
    log.debug("Request to get Utilisateur: {}",id);
    Utilisateur utilisateur= utilisateurRepository.findById(id).orElse(null);
    return utilisateur;
  }


  @Transactional(
      readOnly = true
  )
  public List<UtilisateurDTO> findAll() {
    log.debug("Request to get All Utilisateurs");
    List<Utilisateur> result= utilisateurRepository.findAll();
    return UtilisateurFactory.utilisateurToUtilisateurDTOs(result);
  }

 
  public void delete(Integer id) {
    log.debug("Request to delete Utilisateur: {}",id);
    utilisateurRepository.deleteById(id);
  }


}

