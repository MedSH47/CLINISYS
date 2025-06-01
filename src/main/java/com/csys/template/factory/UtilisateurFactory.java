package com.csys.template.factory;

import com.csys.template.domain.Utilisateur;
import com.csys.template.dto.UtilisateurDTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UtilisateurFactory {

    public static UtilisateurDTO utilisateurToUtilisateurDTO(Utilisateur utilisateur, boolean lazy) {
        if (utilisateur == null) {
            return null;
        }
        UtilisateurDTO utilisateurDTO = new UtilisateurDTO();

        // Basic fields always mapped
        utilisateurDTO.setLogin(utilisateur.getLogin());
        utilisateurDTO.setId(utilisateur.getId());
        utilisateurDTO.setNom(utilisateur.getNom());
        utilisateurDTO.setPrenom(utilisateur.getPrenom());
        utilisateurDTO.setNumTelephone(utilisateur.getNumTelephone());
        utilisateurDTO.setEmail(utilisateur.getEmail());
        utilisateurDTO.setUserCreation(utilisateur.getUserCreation());
        utilisateurDTO.setDateCreation(utilisateur.getDateCreation());
        utilisateurDTO.setRole(utilisateur.getRole());
        utilisateurDTO.setActivite(utilisateur.getActivite());

        if (lazy) {
            utilisateurDTO.setMotDePasse(utilisateur.getMotDePasse());
            utilisateurDTO.setPhoto(utilisateur.getPhoto());

            if (utilisateur.getEquipePosteutilisateurSet() != null) {
                utilisateurDTO.setEquipePosteutilisateurSet(
                    EquipePosteutilisateurFactory.equipeposteutilisateurToEquipePosteutilisateurDTOs(
                        utilisateur.getEquipePosteutilisateurSet(), false
                    )
                );
            } else {
                utilisateurDTO.setEquipePosteutilisateurSet(new ArrayList<>());
            }
        }

        // Convert tickets with lazy = false to avoid recursion
        utilisateurDTO.setTicketSet(TicketFactory.ticketToTicketDTOs(utilisateur.getTicketSet(), false));

        // Set EquipeSet as is (if you want DTO conversion, add similar lazy flag)
        utilisateurDTO.setEquipeSet(utilisateur.getEquipeSet());

        return utilisateurDTO;
    }

    public static Utilisateur utilisateurDTOToUtilisateur(UtilisateurDTO utilisateurDTO) {
        if (utilisateurDTO == null) {
            return null;
        }
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setLogin(utilisateurDTO.getLogin());
        utilisateur.setId(utilisateurDTO.getId());
        utilisateur.setNom(utilisateurDTO.getNom());
        utilisateur.setPrenom(utilisateurDTO.getPrenom());
        utilisateur.setNumTelephone(utilisateurDTO.getNumTelephone());
        utilisateur.setEmail(utilisateurDTO.getEmail());
        utilisateur.setUserCreation(utilisateurDTO.getUserCreation());
        utilisateur.setDateCreation(utilisateurDTO.getDateCreation());
        utilisateur.setMotDePasse(utilisateurDTO.getMotDePasse());
        utilisateur.setPhoto(utilisateurDTO.getPhoto());
        utilisateur.setRole(utilisateurDTO.getRole());
        utilisateur.setActivite(utilisateurDTO.getActivite());

        utilisateur.setTicketSet(TicketFactory.ticketDTOsToTickets(utilisateurDTO.getTicketSet()));

        // TODO: Add mapping for equipePosteutilisateurSet and equipeSet if needed

        return utilisateur;
    }

    public static Collection<UtilisateurDTO> utilisateurToUtilisateurDTOs(Collection<Utilisateur> utilisateurs, boolean lazy) {
        List<UtilisateurDTO> utilisateursDTO = new ArrayList<>();
        if (utilisateurs != null) {
            utilisateurs.forEach(x -> utilisateursDTO.add(utilisateurToUtilisateurDTO(x, lazy)));
        }
        return utilisateursDTO;
    }

    // Overload for default lazy = false
    public static Collection<UtilisateurDTO> utilisateurToUtilisateurDTOs(Collection<Utilisateur> utilisateurs) {
        return utilisateurToUtilisateurDTOs(utilisateurs, false);
    }
}
