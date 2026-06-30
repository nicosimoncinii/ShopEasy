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
        context.setVariable("messaggioAzione", "Per iniziare, conferma il tuo account cliccando il pulsante qui sotto.");
        context.setVariable("linkBottone", "http://localhost:4200/conferma-account");
        context.setVariable("testoBottone", "Conferma account");
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