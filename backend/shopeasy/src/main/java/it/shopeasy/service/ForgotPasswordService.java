package it.shopeasy.service;

import it.shopeasy.dto.auth.ForgotPasswordRequest;
import it.shopeasy.dto.auth.ForgotPasswordResponse;
import it.shopeasy.dto.auth.ResetPasswordRequest;
import it.shopeasy.model.PasswordResetToken;
import it.shopeasy.model.Utente;
import it.shopeasy.repository.PasswordResetTokenRepository;
import it.shopeasy.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Transactional
@Service
public class ForgotPasswordService {
    @Autowired
    private  PasswordResetTokenRepository tokenRepository;
    @Autowired
    private  UtenteRepository utenteRepository;
    @Autowired
    private EmailService emailService;




    public ForgotPasswordResponse creaToken(ForgotPasswordRequest request){

    Utente utente = utenteRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("Utente non trovato con l'email: " + request.getEmail()));

    tokenRepository.deleteByUtente(utente);

    tokenRepository.flush();

    String token = UUID.randomUUID().toString();
    LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(15);

    PasswordResetToken passwordResetToken = new PasswordResetToken(token, utente, expiryDate);
    tokenRepository.save(passwordResetToken);

    emailService.sendPasswordResetEmail(utente,token);



    return new ForgotPasswordResponse(token);
    }
}