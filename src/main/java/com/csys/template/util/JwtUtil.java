package com.csys.template.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.java.Log;

import org.springframework.security.core.GrantedAuthority; // Import nécessaire
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.csys.template.service.UtilisateurService;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List; // Import nécessaire
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors; // Import nécessaire

@Service
@Log
public class JwtUtil {

    private final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private final UtilisateurService utilisateurService;

    public JwtUtil(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * CORRECTION : Cette méthode inclut désormais les rôles de l'utilisateur dans le JWT.
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        
        // --- C'est la ligne clé qui a été ajoutée ---
        // On récupère les autorités (rôles) de l'objet UserDetails,
        // on les transforme en une liste de chaînes de caractères (ex: ["ROLE_A", "ROLE_E"]),
        // et on les ajoute au JWT sous la clé "roles".
        List<String> roles = userDetails.getAuthorities().stream()
                                        .map(GrantedAuthority::getAuthority)
                                        .collect(Collectors.toList());
        claims.put("roles", roles);
        // --- Fin de la ligne clé ajoutée ---
        // ajout id in claims
        claims.put("id", utilisateurService.getIdBylogin(userDetails.getUsername()));
        
        log.info("Generating JWT for user: " + userDetails.getUsername());
        // Durée de vie du token de session : 1 heure
        long sessionExpirationInMillis = 60 * 60 * 1000; 
        return createToken(claims, userDetails.getUsername(), sessionExpirationInMillis);
    }

    /**
     * Génère un jeton à courte durée de vie pour la réinitialisation de mot de passe.
     */
    public String generatePasswordResetToken(String email) {
        // Durée de vie du token de réinitialisation : 10 minutes
        long resetExpirationInMillis = 10 * 60 * 1000; 
        return createToken(new HashMap<>(), email, resetExpirationInMillis);
    }

    private String createToken(Map<String, Object> claims, String subject, long expirationTimeInMillis) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeInMillis))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    
    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }
}