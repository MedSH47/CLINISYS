package com.csys.template.AI_Search_Box;

import com.csys.template.domain.Equipe;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EquipeSpecification {
    public static Specification<Equipe> findByEntities(Map<String, String> entities) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (entities.containsKey("designation")) {
                predicates.add(criteriaBuilder.like(root.get("designation"), "%" + entities.get("designation") + "%"));
            }
            if (entities.containsKey("chefEquipe")) {
                predicates.add(criteriaBuilder.equal(root.get("chefEquipe").get("login"), entities.get("chefEquipe")));
            }
            if (entities.containsKey("actif")) {
                if ("true".equalsIgnoreCase(entities.get("actif"))) {
                    predicates.add(criteriaBuilder.isTrue(root.get("actif")));
                } else if ("false".equalsIgnoreCase(entities.get("actif"))) {
                    predicates.add(criteriaBuilder.isFalse(root.get("actif")));
                }
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}