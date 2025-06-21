package com.csys.template.AI_ml;

import com.csys.template.domain.Ticket;
import com.csys.template.domain.enum_identifier.Priorite;
import com.csys.template.domain.enum_identifier.Status;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TicketSpecification {

    public static Specification<Ticket> findByEntities(Map<String, String> entities) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (entities.containsKey("client_name")) {
                predicates.add(criteriaBuilder.equal(root.get("idClient").get("nomComplet"), entities.get("client_name")));
            }
            if (entities.containsKey("user_name")) {
                predicates.add(criteriaBuilder.equal(root.get("idUtilisateur").get("login"), entities.get("user_name")));
            }
            if (entities.containsKey("module_name")) {
                predicates.add(criteriaBuilder.equal(root.get("module").get("designation"), entities.get("module_name")));
            }
            if (entities.containsKey("status")) {
                try {
                    predicates.add(criteriaBuilder.equal(root.get("statue"), Status.valueOf(entities.get("status"))));
                } catch (IllegalArgumentException e) { /* Ignore invalid status */ }
            }
            if (entities.containsKey("priority")) {
                 try {
                    predicates.add(criteriaBuilder.equal(root.get("priorite"), Priorite.valueOf(entities.get("priority"))));
                } catch (IllegalArgumentException e) { /* Ignore invalid priority */ }
            }

            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            if (entities.containsKey("dateCreation_after")) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dateCreation"), LocalDateTime.parse(entities.get("dateCreation_after"), formatter)));
            }
            if (entities.containsKey("dateCreation_before")) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("dateCreation"), LocalDateTime.parse(entities.get("dateCreation_before"), formatter)));
            }
            if (entities.containsKey("date_echeance_after")) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("date_echeance"), LocalDateTime.parse(entities.get("date_echeance_after"), formatter)));
            }
            if (entities.containsKey("date_echeance_before")) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("date_echeance"), LocalDateTime.parse(entities.get("date_echeance_before"), formatter)));
            }
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}