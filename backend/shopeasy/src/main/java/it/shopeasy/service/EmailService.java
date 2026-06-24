package it.shopeasy.service;

import it.shopeasy.model.Utente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendWelcomeEmail(Utente utente) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(utente.getEmail());
        message.setSubject("Benvenuto su ShopEasy");
        message.setText(
            "Ciao " + utente.getNome() + ",\n\n" +
            "Benvenuto su ShopEasy!\n\n" +
            "Grazie per esserti registrato.\n\n" +
            "Team ShopEasy"
        );
        mailSender.send(message);
    }

    public void sendPasswordResetEmail(Utente utente, String token) {
        String resetUrl = "http://localhost:4200/reset-password?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(utente.getEmail());
        message.setSubject("ShopEasy - Reset Password");
        message.setText(
            "Ciao " + utente.getNome() + ",\n\n" +
            "Clicca sul link per reimpostare la password:\n" +
            resetUrl + "\n\n" +
            "Team ShopEasy"
        );
        mailSender.send(message);
    }
}