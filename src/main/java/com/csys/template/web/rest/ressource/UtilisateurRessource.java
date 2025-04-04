package com.csys.template.web.rest.ressource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csys.template.domain.Utilisateur;
import com.csys.template.service.UtilisateurService;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/Utilisateur")
public class UtilisateurRessource {

    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping
    public List<Utilisateur> getall() {
        return utilisateurService.findall();
    }
    @GetMapping("/{id}")
    public Utilisateur findOne(@PathVariable Integer id) {
        return utilisateurService.findOne(id);
    }
    @PostMapping
    public Utilisateur addUtilisateur(@RequestBody Utilisateur entity) {
        return utilisateurService.addUtilisateur(entity);
    }
    @PutMapping
    public Utilisateur updateUtilisateur(@RequestBody Utilisateur entity) {
        return utilisateurService.updateUtilisateur(entity);
    }
    @DeleteMapping("/{id}")
    public void deleteUtilisateur(@PathVariable Integer id) {
        utilisateurService.deleteUtilisateur(id);
    }
    
}
