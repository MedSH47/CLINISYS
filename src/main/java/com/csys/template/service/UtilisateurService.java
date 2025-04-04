package com.csys.template.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csys.template.domain.Utilisateur;
import com.csys.template.repository.UtilisateurRepository;

@Service
public class UtilisateurService {
    @Autowired
    UtilisateurRepository utilisateurRepository;

    public List<Utilisateur> findall() {
        return utilisateurRepository.findAll();
    }

    public Utilisateur findOne(Integer id) {
        return utilisateurRepository.findOneById(id);
    }
    public Utilisateur addUtilisateur(Utilisateur entity) {
        return utilisateurRepository.save(entity);
    }
    public Utilisateur updateUtilisateur(Utilisateur entity) {
        return utilisateurRepository.save(entity);
    }
    public void deleteUtilisateur(Integer id) {
        utilisateurRepository.deleteById(id);
    }
}
