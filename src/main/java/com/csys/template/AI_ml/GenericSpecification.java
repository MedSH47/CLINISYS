package com.csys.template.AI_ml;

import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GenericSpecification {

    public static <T> Specification<T> findByEntities(Map<String, String> entities) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (entities.containsKey("actif")) {
                predicates.add(criteriaBuilder.equal(root.get("actif"), Boolean.parseBoolean(entities.get("actif"))));
            }

            // Handles 'designation', 'nomComplet', 'titre', etc.
            if (entities.containsKey("name")) {
                try {
                    predicates.add(criteriaBuilder.like(root.get("designation"), "%" + entities.get("name") + "%"));
                } catch (IllegalArgumentException e) {
                    try {
                        predicates.add(criteriaBuilder.like(root.get("nomComplet"), "%" + entities.get("name") + "%"));
                    } catch (IllegalArgumentException e2) {
                        try {
                           predicates.add(criteriaBuilder.like(root.get("titre"), "%" + entities.get("name") + "%"));
                        } catch (IllegalArgumentException e3) {/* Field not found */}
                    }
                }
            }

            // Handles filtering a related entity by its name (e.g., Module by Equipe's name)
            if (entities.containsKey("equipe_name")) {
                 predicates.add(criteriaBuilder.equal(root.get("equipe").get("designation"), entities.get("equipe_name")));
            }
            if (entities.containsKey("chef_equipe_name")) {
                 predicates.add(criteriaBuilder.equal(root.get("chefEquipe").get("login"), entities.get("chef_equipe_name")));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}