package com.csys.template.domain.enum_identifier;


import java.util.HashMap;
import java.util.Map;

public enum Role {
    A("Admin"),
    E("Employe"),
    C("Chef_Equipe");

    private String name;

    //Constructeur
    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static final Map<Role, EnumDTO> ROLE = new HashMap<>();

    static {
        for (Role e : values()) {
            EnumDTO codeDesignationDTO = new EnumDTO();
            codeDesignationDTO.setCode(e);
            codeDesignationDTO.setDesignation(e.name);
            ROLE.put(e, codeDesignationDTO);
        }
    }

    public static EnumDTO valueOfLabel(Role label) {
        return ROLE.get(label);
    }

}