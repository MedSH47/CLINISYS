package com.csys.template.web.rest.ressource;

import java.util.Collections;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csys.template.domain.Utilisateur;
import com.csys.template.service.CustomUserDetailsService;
import com.csys.template.service.JwtUtil;
import com.csys.template.service.UtilisateurService;
import com.csys.template.util.Helper;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api")
public class Authentification {

    private final UtilisateurService utilisateurService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;

    public Authentification(UtilisateurService utilisateurService,
                            AuthenticationManager authenticationManager,
                            CustomUserDetailsService customUserDetailsService,
                            JwtUtil jwtUtil) {
        this.utilisateurService = utilisateurService;
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody Utilisateur authenticationRequest) {
        try {
            // Authenticate credentials
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getLogin(),
                    authenticationRequest.getMotDePasse()
                )
            );

            // Load user details and generate token
            final UserDetails userDetails = customUserDetailsService.loadUserByUsername(authenticationRequest.getLogin());
            final String jwt = jwtUtil.generateToken(userDetails);

            // Return token as JSON
            return ResponseEntity.ok(Collections.singletonMap("token", jwt));

        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(Collections.singletonMap("message", "Utilisateur non trouvé"));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(Collections.singletonMap("message", "Identifiants incorrects"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Erreur d'authentification: " + e.getMessage()));
        }
    }
    @GetMapping("/getuserauth")
    public String getMethodName() {
        String username= Helper.getUserAuthenticated();
        return username;
    }
    
}