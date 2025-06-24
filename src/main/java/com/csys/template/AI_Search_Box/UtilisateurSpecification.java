package com.csys.template.AI_Search_Box;

import com.csys.template.domain.Utilisateur;
import com.csys.template.domain.enum_identifier.Role;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UtilisateurSpecification {

    public static Specification<Utilisateur> findByEntities(Map<String, String> entities) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (entities.containsKey("role")) {
                try {
                    predicates.add(criteriaBuilder.equal(root.get("role"), Role.valueOf(entities.get("role"))));
                } catch (IllegalArgumentException e) { /* Ignore invalid role */ }
            }
            if (entities.containsKey("actif")) {
                if ("true".equalsIgnoreCase(entities.get("actif"))) {
                    predicates.add(criteriaBuilder.isTrue(root.get("actif")));
                } else if ("false".equalsIgnoreCase(entities.get("actif"))) {
                    predicates.add(criteriaBuilder.isFalse(root.get("actif")));
                }
            }
            if (entities.containsKey("nom")) {
                predicates.add(criteriaBuilder.like(root.get("nom"), "%" + entities.get("nom") + "%"));
            }
            if (entities.containsKey("prenom")) {
                predicates.add(criteriaBuilder.like(root.get("prenom"), "%" + entities.get("prenom") + "%"));
            }
            if (entities.containsKey("login")) {
                predicates.add(criteriaBuilder.like(root.get("login"), "%" + entities.get("login") + "%"));
            }
            if (entities.containsKey("email")) {
                predicates.add(criteriaBuilder.like(root.get("email"), "%" + entities.get("email") + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}