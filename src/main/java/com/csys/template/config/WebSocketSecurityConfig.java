package com.csys.template.config;

import com.csys.template.util.JwtUtil; // Import de VOTRE classe
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class WebSocketSecurityConfig implements WebSocketMessageBrokerConfigurer {

    private static final Logger log = LoggerFactory.getLogger(WebSocketSecurityConfig.class);
    private final JwtUtil jwtUtil; // Injection de VOTRE classe JwtUtil

    public WebSocketSecurityConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor =
                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    log.info("WebSocketSecurity: Interception de la trame de connexion STOMP.");
                    
                    List<String> authorizationHeaders = accessor.getNativeHeader("Authorization");
                    if (authorizationHeaders == null || authorizationHeaders.isEmpty()) {
                        log.warn("WebSocketSecurity: Header 'Authorization' non trouvé. La connexion restera anonyme.");
                        return message;
                    }
                    
                    String bearerToken = authorizationHeaders.get(0);
                    log.info("WebSocketSecurity: Token trouvé dans les headers.");

                    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
                        String token = bearerToken.substring(7);

                        // On utilise VOTRE JwtUtil pour valider et créer l'objet Authentication
                        if (jwtUtil.validateToken(token)) {
                            Authentication authentication = jwtUtil.getAuthentication(token);
                            accessor.setUser(authentication); // On attache l'utilisateur authentifié à la session WebSocket
                            log.info("WebSocketSecurity: Utilisateur '{}' authentifié avec succès via JWT.", authentication.getName());
                        } else {
                            log.warn("WebSocketSecurity: Token JWT invalide ou expiré.");
                        }
                    }
                }
                return message;
            }
        });
    }
}