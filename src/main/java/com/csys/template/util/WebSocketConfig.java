package com.csys.template.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * Configure les points de terminaison STOMP sur lesquels le serveur WebSocket écoutera.
     * C'est ici que les clients se connecteront.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Nous définissons un seul point de terminaison clair : /api/ws
        // setAllowedOriginPatterns("*") autorise les connexions de n'importe quelle origine (utile pour le développement).
        // En production, vous devriez le remplacer par l'URL de votre frontend, par exemple : .setAllowedOrigins("http://votre-domaine.com")
        // withSockJS() fournit une solution de repli pour les navigateurs qui ne supportent pas les WebSockets.
        registry.addEndpoint("/api/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    /**
     * Configure le broker de messages qui sera utilisé pour router les messages des clients
     * vers d'autres clients.
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Définit le préfixe pour les destinations gérées par l'application (c'est-à-dire les méthodes annotées avec @MessageMapping).
        // Les messages envoyés à des destinations comme /app/chat.sendMessage seront routés vers un contrôleur.
        config.setApplicationDestinationPrefixes("/app");

        // Active un simple broker en mémoire pour gérer les abonnements et diffuser les messages.
        // Les clients s'abonneront à des destinations commençant par /topic ou /user.
        config.enableSimpleBroker("/topic", "/user");
        
        // Définit le préfixe utilisé pour identifier les destinations spécifiques à un utilisateur.
        // Permet d'envoyer des messages à un utilisateur particulier.
        config.setUserDestinationPrefix("/user");
    }
}
