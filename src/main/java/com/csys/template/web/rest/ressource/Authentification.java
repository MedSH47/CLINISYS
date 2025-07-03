package com.csys.template.web.rest.ressource;

import com.csys.template.dtoRequest.AuthenticationRequest;
import com.csys.template.service.CustomUserDetailsService;
import com.csys.template.util.JwtUtil;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
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

/**
 * Ce contrôleur gère les points d'entrée de l'API pour l'authentification des utilisateurs.
 * Il inclut la connexion initiale pour obtenir un jeton JWT et le rafraîchissement des jetons.
 */
@RestController
@RequestMapping("/api")
public class Authentification {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;

    public Authentification(
            AuthenticationManager authenticationManager,
            CustomUserDetailsService customUserDetailsService,
            JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Crée un jeton d'authentification JWT pour un utilisateur.
     * C'est le point d'entrée principal pour la connexion.
     * @param authenticationRequest L'objet contenant le login et le mot de passe.
     * @return Une réponse avec le jeton JWT ou une erreur.
     */
    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            // Tente d'authentifier l'utilisateur avec les identifiants fournis.
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getLogin(),
                            authenticationRequest.getMotDePasse()
                    )
            );

            // Si l'authentification réussit, on charge les détails de l'utilisateur.
            final UserDetails userDetails = customUserDetailsService.loadUserByUsername(authenticationRequest.getLogin());

            // On génère le jeton. La logique dans JwtUtil s'assure que les rôles sont inclus.
            final String jwt = jwtUtil.generateToken(userDetails);

            // On renvoie le jeton dans une réponse 200 OK.
            return ResponseEntity.ok(Collections.singletonMap("token", jwt));

        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("message", "Utilisateur non trouvé"));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("message", "Identifiants incorrects"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("message", "Erreur interne du serveur: " + e.getMessage()));
        }
    }

    /**
     * Rafraîchit un jeton d'accès expiré en utilisant un jeton de rafraîchissement valide.
     * @param request Une map contenant le "refreshToken".
     * @return Un nouvel access token et le refresh token.
     */
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");

        if (refreshToken == null || refreshToken.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("message", "Refresh token manquant"));
        }

        try {
            String username = jwtUtil.extractUsername(refreshToken);

            if (jwtUtil.isTokenExpired(refreshToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body( Collections.singletonMap("message", "Refresh token expiré"));
            }

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            String newAccessToken = jwtUtil.generateToken(userDetails);

            Map<String, Object> response = new HashMap<>();
            response.put("accessToken", newAccessToken);
            response.put("refreshToken", refreshToken);
            response.put("expiresIn", 3600); 

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("message", "Refresh token invalide"));
        }
    }
}