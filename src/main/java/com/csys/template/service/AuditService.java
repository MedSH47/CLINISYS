package com.csys.template.service;

import com.csys.template.config.jpa.audit.Revision;
import com.csys.template.domain.Client;
import com.csys.template.domain.Ticket;
import com.csys.template.domain.Utilisateur;
import com.csys.template.dtoResponse.AuditLogDTO;
import com.csys.template.dtoResponse.RevisionInfoDTO;
import com.csys.template.factory.ClientFactory;
import com.csys.template.factory.TicketFactory;
import com.csys.template.factory.UtilisateurFactory;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class AuditService {

    private final EntityManager entityManager;

    public AuditService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Méthode générique privée pour récupérer l'historique d'une entité.
     * Elle est réutilisable pour n'importe quelle classe d'entité.
     * @param <T> Le type de l'entité (ex: Ticket.class).
     * @param entityClass La classe de l'entité.
     * @param entityId L'ID de l'entité.
     * @param toDto Une fonction pour convertir l'entité en son DTO.
     * @return Une liste d'événements d'audit.
     */
    private <T> List<AuditLogDTO> getHistoryForEntity(Class<T> entityClass, Object entityId, Function<T, Object> toDto) {
        AuditReader auditReader = AuditReaderFactory.get(entityManager);

        // This query correctly fetches all revisions, including deletions, because the third parameter is 'true'.
        List<Object[]> results = auditReader.createQuery()
                .forRevisionsOfEntity(entityClass, false, true)
                .add(AuditEntity.id().eq(entityId))
                .addOrder(AuditEntity.revisionNumber().asc())
                .getResultList();

        return results.stream()
                .map(result -> {
                    // result[0] is the entity state at a specific revision.
                    // For a DEL revision, this holds the state of the entity just BEFORE it was deleted.
                    T entityState = (T) result[0];
                    
                    // result[1] is our custom Revision entity containing user and timestamp.
                    Revision revision = (Revision) result[1];
                    
                    // result[2] is the revision type (ADD, MOD, DEL).
                    org.hibernate.envers.RevisionType revisionType = (org.hibernate.envers.RevisionType) result[2];

                    RevisionInfoDTO revisionInfoDTO = new RevisionInfoDTO(revision.getId(), revision.getTimestamp(), revision.getUserCreate());
                    
                    // We apply the conversion function to get the DTO.
                    // For DEL revisions, the 'entityState' is valid and contains the pre-deletion data.
                    // The factory must be robust enough to handle detached audited entities, especially lazy collections.
                    Object dto = toDto.apply(entityState);

                    return new AuditLogDTO(revisionInfoDTO, revisionType, dto);
                })
                .collect(Collectors.toList());
    }

    /**
     * Méthode publique pour récupérer l'historique des Tickets.
     */
    public List<AuditLogDTO> getTicketHistory(Integer ticketId) {
        return getHistoryForEntity(Ticket.class, ticketId, entity -> TicketFactory.toDTOLight((Ticket) entity));
    }

    /**
     * ✅ NOUVEAU : Méthode publique pour récupérer l'historique des Clients.
     */
    public List<AuditLogDTO> getClientHistory(Integer clientId) {
        return getHistoryForEntity(Client.class, clientId, entity -> ClientFactory.toDTOLight((Client) entity));
    }

    /**
     * ✅ NOUVEAU : Méthode publique pour récupérer l'historique des Utilisateurs.
     */
    public List<AuditLogDTO> getUtilisateurHistory(Integer utilisateurId) {
        return getHistoryForEntity(Utilisateur.class, utilisateurId, entity -> UtilisateurFactory.toDTOLight((Utilisateur) entity));
    }
}