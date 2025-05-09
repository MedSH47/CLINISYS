package com.csys.template.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.csys.template.domain.Utilisateur;
import com.csys.template.repository.UtilisateurRepository;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurRepository.findBylogin(username);
        
        return User.builder()
                .username(utilisateur.getLogin())
                .password(utilisateur.getPassword())
                .roles(utilisateur.getRole().name()) // Converts enum to ROLE_ format
                .build();
    }
    
}