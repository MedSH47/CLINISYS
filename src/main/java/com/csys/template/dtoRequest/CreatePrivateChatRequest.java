package com.csys.template.dtoRequest;

import javax.validation.constraints.NotNull;

/**
 * DTO pour transporter les données nécessaires à la création d'une discussion privée.
 * Il est utilisé comme corps de la requête pour l'endpoint POST /api/chat/private.
 */
public class CreatePrivateChatRequest {

    @NotNull(message = "L'ID du premier utilisateur ne peut pas être nul.")
    private Integer userId1;

    @NotNull(message = "L'ID du deuxième utilisateur ne peut pas être nul.")
    private Integer userId2;

    // Getters et Setters
    
    public Integer getUserId1() {
        return userId1;
    }

    public void setUserId1(Integer userId1) {
        this.userId1 = userId1;
    }

    public Integer getUserId2() {
        return userId2;
    }

    public void setUserId2(Integer userId2) {
        this.userId2 = userId2;
    }
}
