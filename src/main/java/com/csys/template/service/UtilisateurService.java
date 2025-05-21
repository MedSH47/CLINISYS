package com.csys.template.service;

import com.csys.template.domain.Utilisateur;
import com.csys.template.dto.UtilisateurDTO;
import com.csys.template.factory.UtilisateurFactory;
import com.csys.template.repository.UtilisateurRepository;
import com.google.common.base.Preconditions;
import java.lang.Integer;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UtilisateurService {

  private final UtilisateurRepository utilisateurRepository;

  
  private final PasswordEncoder passwordEncoder;

  public UtilisateurService(UtilisateurRepository utilisateurRepository,PasswordEncoder passwordEncoder) {
    this.utilisateurRepository=utilisateurRepository;
    this.passwordEncoder=passwordEncoder;
    
  }

  
  public ResponseEntity<?> createUtilisateur(Utilisateur utilisateur) {
    String hashedpassword = passwordEncoder.encode(utilisateur.getMotDePasse());
    utilisateur.setMotDePasse(hashedpassword);
    Utilisateur newuser = utilisateurRepository.save(utilisateur);
    return ResponseEntity.status(HttpStatus.CREATED).body(newuser);
  }

 public UtilisateurDTO update(UtilisateurDTO utilisateurDTO) {

    Utilisateur inBase = utilisateurRepository.findById(utilisateurDTO.getId()).orElse(null);
    Preconditions.checkArgument(inBase != null, "utilisateur.NotFound");

    // Ne pas ré-encoder si aucun nouveau mot de passe n’a été fourni
    if (utilisateurDTO.getMotDePasse() != null && 
        !passwordEncoder.matches(utilisateurDTO.getMotDePasse(), inBase.getMotDePasse())) {
        utilisateurDTO.setMotDePasse(passwordEncoder.encode(utilisateurDTO.getMotDePasse()));
    } else {
        utilisateurDTO.setMotDePasse(inBase.getMotDePasse());
    }

    Utilisateur utilisateur = UtilisateurFactory.utilisateurDTOToUtilisateur(utilisateurDTO);
    utilisateur = utilisateurRepository.save(utilisateur);
    return UtilisateurFactory.utilisateurToUtilisateurDTO(utilisateur);
}

  @Transactional(
      readOnly = true
  )
  public UtilisateurDTO findOne(Integer id) {
    Utilisateur utilisateur= utilisateurRepository.findById(id).orElse(null);
    UtilisateurDTO dto = UtilisateurFactory.utilisateurToUtilisateurDTO(utilisateur);
    return dto;
  }

  @Transactional(
      readOnly = true
  )
  public Utilisateur findUtilisateur(Integer id) {
    Utilisateur utilisateur= utilisateurRepository.findById(id).orElse(null);
    return utilisateur;
  }


  @Transactional(
      readOnly = true
  )
  public Collection<UtilisateurDTO> findAll() {
    Collection<Utilisateur> result= utilisateurRepository.findAll();
    return UtilisateurFactory.utilisateurToUtilisateurDTOs(result);
  }

  
  public void delete(Integer id) {
    utilisateurRepository.deleteById(id);
  }

  public boolean existeByemail(String email) {
    // TODO Auto-generated method stub
    return utilisateurRepository.existsByemail(email);
  }

  public Utilisateur findByemail(String email) {
    // TODO Auto-generated method stub
    return utilisateurRepository.findByemail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email : " + email));

  }
}

