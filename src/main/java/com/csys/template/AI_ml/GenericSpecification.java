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

            // Handles 'designation' or 'nomComplet'
            if (entities.containsKey("designation")) {
                 predicates.add(criteriaBuilder.like(root.get("designation"), "%" + entities.get("designation") + "%"));
            }
             if (entities.containsKey("nomComplet")) {
                 predicates.add(criteriaBuilder.like(root.get("nomComplet"), "%" + entities.get("nomComplet") + "%"));
            }
            
            // Filter for Module by Equipe name
            if (entities.containsKey("equipe")) {
                 predicates.add(criteriaBuilder.equal(root.get("equipe").get("designation"), entities.get("equipe")));
            }
            
            // Filter for Equipe by ChefEquipe login
            if (entities.containsKey("chefEquipe")) {
                 predicates.add(criteriaBuilder.equal(root.get("chefEquipe").get("login"), entities.get("chefEquipe")));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}