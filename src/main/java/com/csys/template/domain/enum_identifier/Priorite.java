package com.csys.template.domain.enum_identifier;

public enum Priorite {
    Base("base level"),
    Moyen("Moyen level"),
    Haute("Haute level");

    private String description;

    // Constructor
    Priorite(String description) {
        this.description = description;
    }

    // Getter
    public String getDescription() {
        return description;
    }
}
