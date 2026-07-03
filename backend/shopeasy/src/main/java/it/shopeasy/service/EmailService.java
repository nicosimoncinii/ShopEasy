package it.shopeasy.service;

import it.shopeasy.model.Utente;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendWelcomeEmail(Utente utente) {
        Context context = new Context();
        context.setVariable("nomeBrand", "ShopEasy");
        context.setVariable("nomeUtente", utente.getNome());
        context.setVariable("salutoIniziale", "Ciao");
        context.setVariable("messaggioPrincipale", "Benvenuto su <b>ShopEasy</b>! Siamo felici di averti con noi.");
        context.setVariable("messaggioAzione", "Premi il pulsante per iniziare a fare compere!");
        context.setVariable("linkBottone", "http://localhost:4200/");
        context.setVariable("testoBottone", "Home");
        context.setVariable("messaggioAssistenza", "Per qualsiasi domanda, contatta il nostro supporto.");
        context.setVariable("salutoFinale", "Team ShopEasy");
        context.setVariable("testoCopyright", "© 2026 ShopEasy. Tutti i diritti riservati.");
        context.setVariable("notaFooter", "Questa è un'email automatica, non rispondere.");

        String htmlContent = templateEngine.process("welcome-email", context);

        sendHtmlEmail(utente.getEmail(), "Benvenuto su ShopEasy", htmlContent);
    }

    public void sendPasswordResetEmail(Utente utente, String token) {
        String resetUrl = "http://localhost:4200/reset-password?token=" + token;

        Context context = new Context();
        context.setVariable("nomeBrand", "ShopEasy");
        context.setVariable("nomeUtente", utente.getNome());
        context.setVariable("salutoIniziale", "Ciao");
        context.setVariable("messaggioPrincipale", "Hai richiesto il reset della password.");
        context.setVariable("messaggioAzione", "Clicca sul pulsante per impostarne una nuova.");
        context.setVariable("linkBottone", resetUrl);
        context.setVariable("testoBottone", "Reimposta password");
        context.setVariable("messaggioAssistenza", "Se non hai richiesto tu il reset, ignora questa email.");
        context.setVariable("salutoFinale", "Team ShopEasy");
        context.setVariable("testoCopyright", "© 2026 ShopEasy. Tutti i diritti riservati.");
        context.setVariable("notaFooter", "Questa è un'email automatica, non rispondere.");

        String htmlContent = templateEngine.process("welcome-email", context);

        sendHtmlEmail(utente.getEmail(), "ShopEasy - Reset Password", htmlContent);
    }
    public void sendOrderConfirmationEmail(Utente utente, String riepilogoProdotti, String totale) {

        String htmlContent = """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>Conferma Ordine - ShopEasy</title>
            </head>
            <body style="margin: 0; padding: 0; background-color: #f4f6f8; font-family: 'Segoe UI', Arial, sans-serif;">
                <table width="100%%" border="0" cellspacing="0" cellpadding="0" style="background-color: #f4f6f8; padding: 20px 0;">
                    <tr>
                        <td align="center">
                            <table width="600" border="0" cellspacing="0" cellpadding="0" style="background-color: #ffffff; border-radius: 8px; overflow: hidden; box-shadow: 0 4px 10px rgba(0,0,0,0.05);">
                                <tr>
                                    <td style="background-color: #1e293b; padding: 30px; text-align: center;">
                                        <h1 style="color: #ffffff; margin: 0; font-size: 26px; font-weight: 700; letter-spacing: 1px;">SHOPEASY</h1>
                                        <p style="color: #94a3b8; margin: 5px 0 0 0; font-size: 14px;">Grazie per il tuo ordine!</p>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="padding: 30px;">
                                        <h2 style="color: #0f172a; margin: 0 0 15px 0; font-size: 20px;">
                                            Ciao %s,
                                        </h2>
                                        <p style="color: #334155; margin: 0 0 25px 0; font-size: 15px; line-height: 1.6;">
                                            Il tuo ordine è stato registrato ed è in fase di preparazione. Ecco il riepilogo dettagliato degli articoli che hai acquistato:
                                        </p>
                                        <h3 style="color: #0f172a; margin: 0 0 10px 0; font-size: 14px; text-transform: uppercase; letter-spacing: 0.5px;">Riepilogo Prodotti</h3>
                                        
                                        <div style="background-color: #f8fafc; padding: 15px; border-radius: 6px; margin-bottom: 25px; color: #334155;">
                                            %s
                                        </div>
                                        <table width="100%%" border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td align="right" style="padding: 10px 0; color: #0f172a; font-size: 16px; font-weight: 700;">Totale dell'ordine:</td>
                                                <td align="right" style="padding: 10px 0; color: #3b82f6; font-size: 18px; font-weight: 700; width: 100px;">
                                                    € %s
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="background-color: #f8fafc; padding: 20px; text-align: center; border-top: 1px solid #e2e8f0;">
                                        <p style="color: #64748b; margin: 0; font-size: 12px;">
                                            © 2026 ShopEasy. Tutti i diritti riservati.
                                        </p>
                                        <p style="color: #94a3b8; margin: 5px 0 0 0; font-size: 11px;">
                                            Questa è una mail automatica, si prega di non rispondere direttamente.
                                        </p>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </body>
            </html>
            """.formatted(utente.getNome(), riepilogoProdotti, totale);

        // Inviamo la stringa HTML usando il metodo privato già presente nel file
        sendHtmlEmail(utente.getEmail(), "Conferma Ordine - ShopEasy", htmlContent);
    }


    private void sendHtmlEmail(String to, String subject, String htmlContent) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Errore nell'invio dell'email a " + to, e);
        }
    }
}