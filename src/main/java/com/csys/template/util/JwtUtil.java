package com.csys.template.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    private final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // ... (vos méthodes existantes : extractUsername, extractExpiration, etc.)

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

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        // Vous pouvez ajouter d'autres informations sur l'utilisateur dans les claims si nécessaire
        return createToken(claims, userDetails.getUsername(), 60 * 60 * 1000); // 1 heure pour un token de session normal
    }

    /**
     * NOUVELLE MÉTHODE : Génère un jeton à courte durée de vie pour la réinitialisation de mot de passe.
     * @param email L'e-mail de l'utilisateur, qui sera le "subject" du jeton.
     * @return Un JWT valide pour 10 minutes.
     */
    public String generatePasswordResetToken(String email) {
        // Pas besoin de claims supplémentaires pour ce jeton, l'e-mail dans le sujet suffit.
        return createToken(new HashMap<>(), email, 10 * 60 * 1000); // 10 minutes
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
    
    /**
     * NOUVEAU : Valide un jeton sans avoir besoin d'un objet UserDetails.
     * Utile pour valider le jeton de réinitialisation.
     */
    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }
}