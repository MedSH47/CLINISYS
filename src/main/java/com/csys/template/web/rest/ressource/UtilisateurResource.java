package com.csys.template.web.rest.ressource;

import com.csys.template.domain.Utilisateur;
import com.csys.template.dto.UtilisateurDTO;
import com.csys.template.service.CustomUserDetailsService;
import com.csys.template.service.JwtUtil;
import com.csys.template.service.UtilisateurService;

import java.lang.Integer;
import java.lang.String;
import java.util.Collections;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UtilisateurResource {
  private final UtilisateurService utilisateurService;
  private final JwtUtil jwtUtil;
  private final CustomUserDetailsService customUserDetailsService;
  private final AuthenticationManager authenticationManager;
  private final Logger log = LoggerFactory.getLogger(UtilisateurService.class);

  public UtilisateurResource(UtilisateurService utilisateurService, JwtUtil jwtUtil,
      CustomUserDetailsService customUserDetailsService, AuthenticationManager authenticationManager) {
    this.utilisateurService = utilisateurService;
    this.jwtUtil = jwtUtil;
    this.customUserDetailsService = customUserDetailsService;
    this.authenticationManager = authenticationManager;
  }

  @PostMapping("/authenticate")
  public ResponseEntity<?> createAuthenticationToken(@RequestBody Utilisateur authenticationRequest) {
    try {
      Utilisateur user = utilisateurService.findBylogin(authenticationRequest.getLogin());
      if (!user.getActif()) {
        return ResponseEntity.status(HttpStatus.LOCKED).body(Collections.singletonMap("message", "Utilisateur inactif"));
      }
  
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              authenticationRequest.getLogin(),
              authenticationRequest.getPassword()
          )
      );
  
      final UserDetails userDetails = customUserDetailsService.loadUserByUsername(authenticationRequest.getLogin());
      final String jwt = jwtUtil.generateToken(userDetails);
  
      return ResponseEntity.ok(jwt);
    } catch (BadCredentialsException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
              .body(Collections.singletonMap("message", "Identifiants incorrects"));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(Collections.singletonMap("message", "user not found"));
    }
  }
  

  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@RequestBody Utilisateur utilisateur) {
    if (utilisateur.getRole()!=null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Role Given by the admin You not allowed to fill this field");
    }
    return utilisateurService.createUtilisateur(utilisateur);
  }
  @GetMapping("/utilisateurs")
  public ResponseEntity<?> getAllUtilisateurs(@RequestHeader(value = "Authorization", required = true) String authorizationHeader) {
    log.debug("Request to get all Utilisateurs");

    // Check if Authorization header is present
    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(Collections.singletonMap("error", "Authorization header is missing or invalid"));
    }

    try {
      String token = authorizationHeader.substring(7);

      // First check if token can be parsed (without validation)
      try {
        jwtUtil.extractUsername(token); // This will throw SignatureException if invalid
      } catch (io.jsonwebtoken.security.SignatureException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(Collections.singletonMap("error", "Invalid JWT signature"));
      } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(Collections.singletonMap("error", "Invalid JWT token"));
      }

      // Now do full validation
      String username = jwtUtil.extractUsername(token);
      if (username == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(Collections.singletonMap("error", "Invalid JWT token"));
      }

      UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
      if (!jwtUtil.validateToken(token, userDetails)) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(Collections.singletonMap("error", "Invalid or expired JWT token"));
      }

      // If everything is valid, return the users
      return ResponseEntity.ok(utilisateurService.findAll());

    } catch (Exception e) {
      log.error("JWT validation error", e);
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(Collections.singletonMap("error", "Authentication failed"));
    }
  }

  @DeleteMapping("/utilisateurs/{id}")
  public ResponseEntity<?> deleteUtilisateur(
      @PathVariable Integer id,
      @RequestHeader(value = "Authorization") String authorizationHeader) {
  
    log.debug("Request to delete Utilisateur: {}", id);
  
    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(Map.of("error", "Authorization header is missing or invalid"));
    }
  
    try {
      String token = authorizationHeader.substring(7);
      String username = jwtUtil.extractUsername(token);
  
      if (username == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(Map.of("error", "Invalid JWT token"));
      }
  
      UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
      if (!jwtUtil.validateToken(token, userDetails)) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(Map.of("error", "Invalid or expired JWT token"));
      }
  
      boolean isAdmin = userDetails.getAuthorities().stream()
    .anyMatch(auth -> auth.getAuthority().equals("ROLE_Admin"));
  
      if (!isAdmin) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(Map.of("error", "Only users with ADMIN role can perform this action"));
      }
  
      if (!utilisateurService.existsById(id)) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(Map.of("error", "User not found with id: " + id));
      }
  
      if ("Admin".equalsIgnoreCase(utilisateurService.getRole(id).name())) {
        return ResponseEntity.status(HttpStatus.LOCKED)
            .body(Map.of("error", "Deleting an Admin is prohibited"));
      }
  
      utilisateurService.delete(id);
      return ResponseEntity.ok().build();
  
    } catch (UsernameNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(Map.of("error", "User not found"));
    } catch (Exception e) {
      log.error("Deletion error", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(Map.of("error", "Internal server error"));
    }
  }
  

  @GetMapping("/utilisateur/{id}")
  public UtilisateurDTO postMethodName(@PathVariable Integer id) {
      return utilisateurService.findOne(id);
  }
  
}
