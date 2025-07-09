package com.csys.template.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormulairesHtml {

    private static final String CSS_STYLE = """
        body { font-family: 'Arial', sans-serif; background-color: #f4f4f4; padding: 20px; color: #333; }
        .container { background-color: #fff; padding: 30px; border-radius: 10px; box-shadow: 0 0 15px rgba(0,0,0,0.1); max-width: 600px; margin: auto; border-top: 5px solid #0056b3; }
        .header { font-size: 24px; font-weight: bold; color: #0056b3; margin-bottom: 20px; }
        .code-box { background-color: #e9ecef; color: #0056b3; font-size: 24px; font-weight: bold; padding: 15px; border-radius: 8px; text-align: center; letter-spacing: 3px; margin: 25px 0; }
        .footer { font-size: 12px; color: #888; margin-top: 30px; text-align: center; }
        p { line-height: 1.6; }
        strong { color: #0056b3; }
        """;

    // Style spécifique pour les alertes de retard, pour attirer l'attention.
    private static final String CSS_STYLE_ALERTE_RETARD = """
        body { font-family: 'Arial', sans-serif; background-color: #fcf2f2; padding: 20px; color: #333; }
        .container { background-color: #fff; padding: 30px; border-radius: 10px; box-shadow: 0 0 15px rgba(0,0,0,0.1); max-width: 600px; margin: auto; border-top: 5px solid #d9534f; }
        .header { font-size: 24px; font-weight: bold; color: #d9534f; margin-bottom: 20px; }
        .ticket-details { background-color: #f9f9f9; border-left: 4px solid #d9534f; margin: 20px 0; padding: 15px; }
        .ticket-details p { margin: 10px 0; font-size: 14px; }
        .ticket-details strong { color: #333; min-width: 120px; display: inline-block;}
        .footer { font-size: 12px; color: #888; margin-top: 30px; text-align: center; }
        p { line-height: 1.6; }
        .emphasis { color: #d9534f; font-weight: bold; }
        """;

    /**
     * Gabarit pour la demande de réinitialisation du mot de passe.
     * @param nomUtilisateur Le nom de l'utilisateur.
     * @param codeReinitialisation Le code de vérification.
     * @return Le contenu HTML de l'e-mail.
     */
    public static String genererHtmlReinitialisationMotDePasse(String nomUtilisateur, String codeReinitialisation) {
        return """
        <!DOCTYPE html>
        <html><head><style>%s</style></head>
        <body>
            <div class="container">
                <div class="header">Demande de Réinitialisation de Mot de Passe</div>
                <p>Bonjour <strong>%s</strong>,</p>
                <p>Nous avons reçu une demande pour réinitialiser le mot de passe de votre compte. Si vous n'êtes pas à l'origine de cette demande, vous pouvez ignorer cet e-mail en toute sécurité.</p>
                <p>Utilisez le code ci-dessous pour procéder à la réinitialisation. Ce code est valide pour une durée de 10 minutes.</p>
                <div class="code-box">%s</div>
                <p>Cordialement,<br/>L'équipe de support</p>
                <div class="footer">© 2025 Votre Entreprise – Tous droits réservés.</div>
            </div>
        </body></html>
        """.formatted(CSS_STYLE, nomUtilisateur, codeReinitialisation);
    }

    /**
     * Gabarit de bienvenue pour un nouvel utilisateur.
     * @param nomUtilisateur Le nom de l'utilisateur.
     * @param login Le login de l'utilisateur pour se connecter.
     * @return Le contenu HTML de l'e-mail.
     */
    public static String genererHtmlBienvenue(String nomUtilisateur, String login) {
        return """
        <!DOCTYPE html>
        <html><head><style>%s</style></head>
        <body>
            <div class="container">
                <div class="header">Bienvenue chez Nous !</div>
                <p>Bonjour <strong>%s</strong>,</p>
                <p>Votre compte a été créé avec succès. Nous sommes ravis de vous compter parmi nos utilisateurs.</p>
                <p>Vous pouvez désormais vous connecter à notre plateforme en utilisant votre identifiant : <strong>%s</strong></p>
                <p>Si vous avez des questions, n'hésitez pas à contacter notre support.</p>
                <p>Cordialement,<br/>L'équipe de support</p>
                <div class="footer">© 2025 Votre Entreprise – Tous droits réservés.</div>
            </div>
        </body></html>
        """.formatted(CSS_STYLE, nomUtilisateur, login);
    }
    
    /**
     * Gabarit de confirmation de changement de mot de passe.
     * @param nomUtilisateur Le nom de l'utilisateur.
     * @return Le contenu HTML de l'e-mail.
     */
    public static String genererHtmlChangementMotDePasse(String nomUtilisateur) {
        return """
        <!DOCTYPE html>
        <html><head><style>%s</style></head>
        <body>
            <div class="container">
                <div class="header">Confirmation de Sécurité</div>
                <p>Bonjour <strong>%s</strong>,</p>
                <p>Ceci est une confirmation que le mot de passe de votre compte a été modifié avec succès.</p>
                <p>Si vous n'avez pas effectué cette action, veuillez contacter immédiatement notre support technique pour sécuriser votre compte.</p>
                <p>Cordialement,<br/>L'équipe de support</p>
                <div class="footer">© 2025 Votre Entreprise – Tous droits réservés.</div>
            </div>
        </body></html>
        """.formatted(CSS_STYLE, nomUtilisateur);
    }

    /**
     * Gabarit pour une alerte de ticket en retard.
     * @param nomDestinataire Le nom de la personne à qui l'e-mail est adressé (ex: nom de l'utilisateur assigné).
     * @param ticketInfo Un objet contenant les informations du ticket.
     * @return Le contenu HTML de l'e-mail.
     */
    public static String genererHtmlTicketEnRetard(String nomDestinataire, TicketInfo ticketInfo) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'à' HH:mm");
        String dateEcheanceFormatted = ticketInfo.getDateEcheance() != null ? ticketInfo.getDateEcheance().format(formatter) : "Non définie";

        return """
        <!DOCTYPE html>
        <html><head><meta charset="UTF-8"><style>%s</style></head>
        <body>
            <div class="container">
                <div class="header">Alerte : Ticket en Retard</div>
                <p>Bonjour <strong>%s</strong>,</p>
                <p>Ceci est une notification pour vous informer que le ticket suivant a dépassé sa date d'échéance et nécessite une attention immédiate.</p>
                
                <div class="ticket-details">
                    <p><strong>Titre :</strong> %s</p>
                    <p><strong>Description :</strong> %s</p>
                    <p><strong>Priorité :</strong> <span class="emphasis">%s</span></p>
                    <p><strong>Statut actuel :</strong> %s</p>
                    <p><strong>Date d'échéance :</strong> <span class="emphasis">%s</span></p>
                    <p><strong>Client :</strong> %s</p>
                    <p><strong>Module concerné :</strong> %s</p>
                    <p><strong>Assigné à :</strong> %s</p>
                </div>

                <p>Veuillez vous connecter à la plateforme pour traiter ce ticket dès que possible.</p>
                <p>Cordialement,<br/>Le système de notification</p>
                <div class="footer">© 2025 Votre Entreprise – Tous droits réservés.</div>
            </div>
        </body></html>
        """.formatted(
            CSS_STYLE_ALERTE_RETARD,
            nomDestinataire,
            ticketInfo.getTitre(),
            ticketInfo.getDescription(),
            ticketInfo.getPriorite(),
            ticketInfo.getStatut(),
            dateEcheanceFormatted,
            ticketInfo.getNomClient(),
            ticketInfo.getNomModule(),
            ticketInfo.getNomUtilisateur()
        );
    }

    /**
     * Classe interne pour encapsuler les informations du ticket.
     * Remplacez-la par votre DTO de ticket si vous en avez un.
     */
    public static class TicketInfo {
        private String titre;
        private String description;
        private String priorite;
        private String statut;
        private String nomClient;
        private String nomModule;
        private String nomUtilisateur;
        private LocalDateTime dateEcheance;

        // Getters
        public String getTitre() { return titre; }
        public String getDescription() { return description; }
        public String getPriorite() { return priorite; }
        public String getStatut() { return statut; }
        public String getNomClient() { return nomClient; }
        public String getNomModule() { return nomModule; }
        public String getNomUtilisateur() { return nomUtilisateur; }
        public LocalDateTime getDateEcheance() { return dateEcheance; }

        // Setters (un pattern Builder serait encore mieux)
        public void setTitre(String titre) { this.titre = titre; }
        public void setDescription(String description) { this.description = description; }
        public void setPriorite(String priorite) { this.priorite = priorite; }
        public void setStatut(String statut) { this.statut = statut; }
        public void setNomClient(String nomClient) { this.nomClient = nomClient; }
        public void setNomModule(String nomModule) { this.nomModule = nomModule; }
        public void setNomUtilisateur(String nomUtilisateur) { this.nomUtilisateur = nomUtilisateur; }
        public void setDateEcheance(LocalDateTime dateEcheance) { this.dateEcheance = dateEcheance; }
    }
}
