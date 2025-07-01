package com.csys.template.dtoRequest;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * DTO pour transporter les données nécessaires à la création d'une discussion de groupe.
 * Il est utilisé comme corps de la requête pour l'endpoint POST /api/chat/group.
 */
public class CreateGroupChatRequest {

    @NotBlank(message = "Le nom du groupe ne peut pas être vide.")
    @Size(min = 3, max = 50, message = "Le nom du groupe doit contenir entre 3 et 50 caractères.")
    private String name;

    @NotEmpty(message = "La liste des participants ne peut pas être vide.")
    private List<Integer> participantIds;

    // Getters et Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getParticipantIds() {
        return participantIds;
    }

    public void setParticipantIds(List<Integer> participantIds) {
        this.participantIds = participantIds;
    }
}
