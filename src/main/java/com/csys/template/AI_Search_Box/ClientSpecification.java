package com.csys.template.AI_Search_Box;

import com.csys.template.domain.Client;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClientSpecification {
    public static Specification<Client> findByEntities(Map<String, String> entities) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (entities.containsKey("nomComplet")) {
                predicates.add(criteriaBuilder.like(root.get("nomComplet"), "%" + entities.get("nomComplet") + "%"));
            }
            if (entities.containsKey("email")) {
                predicates.add(criteriaBuilder.like(root.get("email"), "%" + entities.get("email") + "%"));
            }
            if (entities.containsKey("region")) {
                predicates.add(criteriaBuilder.like(root.get("region"), "%" + entities.get("region") + "%"));
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