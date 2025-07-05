package com.csys.template.config;

// Créez ce nouveau fichier dans : src/main/java/com/csys/template/config/CustomSQLServerDialect.java

import org.hibernate.dialect.SQLServer2012Dialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;

public class CustomSQLServerDialect extends SQLServer2012Dialect {

    /**
     * Le constructeur où nous enregistrons nos fonctions SQL personnalisées.
     */
    public CustomSQLServerDialect() {
        super();

        // Enregistre la fonction DATEPART pour qu'Hibernate la reconnaisse.
        // Syntaxe: registerFunction("nom_pour_hibernate", new SQLFunctionTemplate(type_de_retour, "TEMPLATE_SQL_REEL"));
        
        registerFunction("datepart", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "DATEPART(?1, ?2)"));
        registerFunction("format", new SQLFunctionTemplate(StandardBasicTypes.STRING, "FORMAT(?1, ?2, ?3)"));

    }
}