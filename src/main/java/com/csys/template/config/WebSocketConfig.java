package com.csys.template.config; // Ensure this package is correct for your project

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker // This enables WebSocket message handling, backed by a message broker.
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * This method registers the WebSocket endpoint that clients will connect to.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // The endpoint "/template-core/ws" is the HTTP URL that the SockJS client will connect to for the handshake.
        // This MUST EXACTLY match the URL used in your frontend's SockJS constructor.
        // setAllowedOriginPatterns("*") is a flexible way to handle CORS for WebSocket connections. It's good for development.
        // withSockJS() enables SockJS fallback options, which helps if a browser doesn't support WebSockets natively.
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*") // Allows connections from any origin
                .withSockJS();
    }

    /**
     * This method configures the message broker, which is responsible for routing messages.
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // This sets the prefix for the application's message-handling methods.
        // When your client sends a message to "/app/chat.sendPrivate", Spring looks for a @MessageMapping("/chat.sendPrivate") method.
        config.setApplicationDestinationPrefixes("/app");

        // This configures the actual message broker that broadcasts messages to subscribed clients.
        // We enable a simple, in-memory broker.
        // "/topic" is typically for public, one-to-many broadcasts.
        // "/queue" is typically for private, point-to-point messaging.
        // The broker will handle destinations starting with these prefixes.
        config.enableSimpleBroker("/topic", "/queue");

        // --- THIS IS THE KEY FOR REAL-TIME PRIVATE MESSAGES ---
        // This sets the prefix used to identify user-specific destinations.
        // When you use `messagingTemplate.convertAndSendToUser(username, ...)` and subscribe to `/user/queue/private`,
        // Spring uses this prefix to construct the final, unique destination for that user.
        // Without this line, `convertAndSendToUser` will NOT work.
        config.setUserDestinationPrefix("/user");
    }
    
}