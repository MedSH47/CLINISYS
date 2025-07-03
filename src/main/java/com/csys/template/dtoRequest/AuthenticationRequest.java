package com.csys.template.dtoRequest;

// No complex annotations needed, just a plain Java object
public class AuthenticationRequest {

    private String login;
    private String motDePasse;

    // Getters and Setters are required for Jackson to work
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
}