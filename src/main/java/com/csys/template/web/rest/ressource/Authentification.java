package com.csys.template.web.rest.ressource;

import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csys.template.domain.Utilisateur;
import com.csys.template.service.CustomUserDetailsService;
import com.csys.template.service.JwtUtil;
import com.csys.template.service.UtilisateurService;



@RestController
@RequestMapping("/api")
public class Authentification{

    
private final UtilisateurService utilisateurService;
private final AuthenticationManager authenticationManager;
private final CustomUserDetailsService customUserDetailsService;
private final JwtUtil jwtUtil;


public Authentification(UtilisateurService utilisateurService, AuthenticationManager authenticationManager,
CustomUserDetailsService customUserDetailsService,JwtUtil jwtUtil) {
        this.utilisateurService = utilisateurService;
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService=customUserDetailsService;
        this.jwtUtil=jwtUtil;
        
    }


@PostMapping("/authenticate")
public ResponseEntity<?> createAuthenticationToken(@RequestBody Utilisateur authenticationRequest) {
    try {
        Utilisateur user = utilisateurService.findByemail(authenticationRequest.getEmail());

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                authenticationRequest.getEmail(),
                authenticationRequest.getMotDePasse()
            )
        );
        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(jwt);
    } catch (BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Collections.singletonMap("message", "Identifiants incorrects"));
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Collections.singletonMap("message", "Erreur interne"));
    }
}

  
}