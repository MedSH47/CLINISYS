package com.csys.template.dtoProjection;

import com.csys.template.domain.ChatMessage;
import com.csys.template.dtoRequest.UtilisateurRequestDTO;
import com.csys.template.dtoResponse.UtilisateurResponseDTO;

/**
 * DTO pour représenter un contact dans la liste de chat.
 * Contient le partenaire de la conversation et le dernier message échangé.
 */
public class ChatContactDTO {

    private UtilisateurResponseDTO partner; // L'autre utilisateur de la conversation
    private ChatMessage lastMessage; // Le dernier message

    // Constructeurs, Getters et Setters

    public ChatContactDTO() {
    }

    public ChatContactDTO(UtilisateurResponseDTO partner, ChatMessage lastMessage) {
        this.partner = partner;
        this.lastMessage = lastMessage;
    }

    public UtilisateurResponseDTO getPartner() {
        return partner;
    }

    public void setPartner(UtilisateurResponseDTO partner) {
        this.partner = partner;
    }

    public ChatMessage getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(ChatMessage lastMessage) {
        this.lastMessage = lastMessage;
    }
}