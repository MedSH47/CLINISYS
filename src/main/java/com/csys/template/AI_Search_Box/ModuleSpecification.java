package com.csys.template.AI_Search_Box;

import com.csys.template.domain.Module;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ModuleSpecification {
    public static Specification<Module> findByEntities(Map<String, String> entities) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (entities.containsKey("designation")) {
                predicates.add(criteriaBuilder.like(root.get("designation"), "%" + entities.get("designation") + "%"));
            }
            if (entities.containsKey("equipe")) {
                predicates.add(criteriaBuilder.equal(root.get("equipe").get("designation"), entities.get("equipe")));
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