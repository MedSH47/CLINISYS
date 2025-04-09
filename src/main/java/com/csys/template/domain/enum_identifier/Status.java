package com.csys.template.domain.enum_identifier;

public enum Status {
    Accepte("accepte"),
    Refuse("refuse"),
    En_Attende("en coure");

    private String description;

    // Constructor
    Status(String description) {
        this.description = description;
    }

    // Getter
    public String getDescription() {
        return description;
    }
}
