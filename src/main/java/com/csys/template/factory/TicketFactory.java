package com.csys.template.factory;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.csys.template.domain.Client;
import com.csys.template.domain.Module;
import com.csys.template.domain.Ticket;
import com.csys.template.domain.Utilisateur;
import com.csys.template.domain.enum_identifier.Status;
import com.csys.template.dtoRequest.TicketRequestDTO;
import com.csys.template.dtoResponse.TicketResponseDTO;
import com.csys.template.util.Helper;

public class TicketFactory {

    public static List<TicketResponseDTO> toResponseDTOsParents(Collection<Ticket> tickets) {
        if (tickets == null) {
            return Collections.emptyList();
        }
        return tickets.stream()
                // keep only tickets whose parentTicket is null
                .filter(t -> t.getParentTicket() == null)
                // map to the full DTO
                .map(TicketFactory::toResponseDTO)
                .collect(Collectors.toList());
    }

    public static TicketResponseDTO toResponseDTO(Ticket ticket) {
        if (ticket == null)
            return null;
        TicketResponseDTO dto = new TicketResponseDTO();
        dto.setId(ticket.getId());
        dto.setTitre(ticket.getTitre());
        dto.setDateTraitement(ticket.getDateTraitement());
        dto.setDescription(ticket.getDescription());
        dto.setDateCreation(ticket.getDateCreation());
        dto.setUserCreation(ticket.getUserCreation());
        dto.setActif(ticket.getActif());
        dto.setPriorite(ticket.getPriorite());
        dto.setStatue(ticket.getStatue());
        dto.setDate_echeance(ticket.getDate_echeance());
        dto.setDateCloture(ticket.getDateCloture());
        dto.setChildTickets(TicketFactory.toDTOsLight(ticket.getChildTickets()));
        dto.setDocumentJointesList(DocumentJointesFactory.toResponseDTOs(ticket.getDocumentJointesList()));
        dto.setIdUtilisateur(UtilisateurFactory.toDTOLight(ticket.getIdUtilisateur()));
        dto.setCommentaireList(CommentaireFactory.toResponseDTOs(ticket.getCommentaireList()));
        dto.setIdClient(ClientFactory.toDTOLight(ticket.getIdClient()));
        dto.setIdModule(ModuleFactory.toDTOLight(ticket.getModule()));

        if (ticket.getParentTicket() != null) {
            dto.setParentTicket(TicketFactory.toDTOLight(ticket.getParentTicket()));
        }
        return dto;
    }

    public static TicketResponseDTO toDTOLight(Ticket ticket) {
        if (ticket == null)
            return null;
        TicketResponseDTO dto = new TicketResponseDTO();
        dto.setActif(ticket.getActif());
        dto.setId(ticket.getId());
        dto.setTitre(ticket.getTitre());
        dto.setDateTraitement(ticket.getDateTraitement());
        dto.setStatue(ticket.getStatue());
        dto.setDescription(ticket.getDescription());
        dto.setIdModule(ModuleFactory.toDTOLight(ticket.getModule()));
        dto.setIdUtilisateur(UtilisateurFactory.toDTOLight(ticket.getIdUtilisateur()));
        dto.setPriorite(ticket.getPriorite());
        dto.setDateCreation(ticket.getDateCreation());
        dto.setDate_echeance(ticket.getDate_echeance());
        dto.setCommentaireList(CommentaireFactory.toResponseDTOs(ticket.getCommentaireList()));
        return dto;
    }

    public static Ticket toEntity(TicketRequestDTO dto) {
        if (dto == null)
            return null;
        Ticket entity = new Ticket();
        entity.setTitre(dto.getTitre());
        entity.setDescription(dto.getDescription());
        entity.setPriorite(dto.getPriorite());
        entity.setStatue(dto.getStatue());
        entity.setDateCreation(LocalDateTime.now());
        entity.setUserCreation(Helper.getUserAuthenticated());
        entity.setActif(dto.getActif());
        entity.setDate_echeance(dto.getDate_echeance());
        entity.setDateTraitement(dto.getDateTraitement());

        if (dto.getIdParentTicket() != null) {
            Ticket parent = new Ticket();
            parent.setId(dto.getIdParentTicket());
            entity.setParentTicket(parent);
        }
        if (dto.getIdClient() != null) {
            Client client = new Client();
            client.setId(dto.getIdClient());
            entity.setIdClient(client);
        }
        if (dto.getIdModule() != null) {
            Module module = new Module();
            module.setId(dto.getIdModule());
            entity.setModule(module);
        }
        if (dto.getIdUtilisateur() != null) {
            Utilisateur user = new Utilisateur();
            user.setId(dto.getIdUtilisateur());
            entity.setIdUtilisateur(user);
        }
        return entity;
    }

    public static List<TicketResponseDTO> toResponseDTOs(Collection<Ticket> tickets) {
        if (tickets == null)
            return Collections.emptyList();
        return tickets.stream().map(TicketFactory::toResponseDTO).collect(Collectors.toList());
    }

    public static List<TicketResponseDTO> toDTOsLight(Collection<Ticket> tickets) {
        if (tickets == null)
            return Collections.emptyList();
        return tickets.stream().map(TicketFactory::toDTOLight).collect(Collectors.toList());
    }

    public static void updateFromDTO(Ticket entity, TicketRequestDTO dto) {
        if (dto == null || entity == null) {
            return;
        }

        if (dto.getDate_echeance() != null) {
            entity.setDate_echeance(dto.getDate_echeance());
        }

        if (dto.getTitre() != null) {
            entity.setTitre(dto.getTitre());
        }

        if (dto.getDescription() != null) {
            entity.setDescription(dto.getDescription());
        }

        if (dto.getPriorite() != null) {
            entity.setPriorite(dto.getPriorite());
        }

        if (dto.getDateTraitement() != null) {
            entity.setDateTraitement(dto.getDateTraitement());
        }
        if (dto.getActif() != null) {
            entity.setActif(dto.getActif());
        }

        if (dto.getStatue() != null) {
            if (dto.getStatue() == Status.Termine && entity.getStatue() != Status.Termine) {
                entity.setDateCloture(LocalDateTime.now());
            }
            entity.setStatue(dto.getStatue());
        }

        if (dto.getIdModule() != null) {
            if (dto.getIdModule() != 0) {
                Module module = new Module();
                module.setId(dto.getIdModule());
                entity.setModule(module);
            } else {
                entity.setModule(null);
            }
        }

        if (dto.getIdUtilisateur() != null && dto.getIdUtilisateur() != 0) {
            Utilisateur user = new Utilisateur();
            user.setId(dto.getIdUtilisateur());
            entity.setIdUtilisateur(user);
        } else {
            entity.setIdUtilisateur(null);
        }

        if (dto.getIdParentTicket() != null) {
            Ticket parent = new Ticket();
            parent.setId(dto.getIdParentTicket());
            entity.setParentTicket(parent);
        }

        if (dto.getIdClient() != null) {
            Client client = new Client();
            client.setId(dto.getIdClient());
            entity.setIdClient(client);
        }
    }

}