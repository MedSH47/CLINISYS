package com.csys.template.util;

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
}