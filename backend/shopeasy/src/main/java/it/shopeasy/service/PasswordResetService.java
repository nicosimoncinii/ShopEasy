package it.shopeasy.service;

import it.shopeasy.dto.auth.ForgotPasswordRequest;
import it.shopeasy.dto.auth.ResetPasswordRequest;
import it.shopeasy.model.PasswordResetToken;
import it.shopeasy.model.Utente;
import it.shopeasy.repository.PasswordResetTokenRepository;
import it.shopeasy.repository.UtenteRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Transactional
@Service
public class PasswordResetService {
    private final PasswordResetTokenRepository tokenRepository;
    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;

    public PasswordResetService(PasswordResetTokenRepository tokenRepository, UtenteRepository utenteRepository, PasswordEncoder passwordEncoder) {
        this.tokenRepository = tokenRepository;
        this.utenteRepository = utenteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void verificaPassword(ResetPasswordRequest resetPasswordRequest){
        if(!resetPasswordRequest.getNuovaPassword().equals(resetPasswordRequest.getConfermaPassword())){
            throw new RuntimeException("Le password non corrispondono");
        }
        
    }

    public void verificaPresenzaTokenDB(ResetPasswordRequest resetPasswordRequest){
        PasswordResetToken passwordResetToken = tokenRepository.findByToken(resetPasswordRequest.getToken())
                .orElseThrow(() -> new RuntimeException("Token non valido o scaduto"));

        if(passwordResetToken.isUsed()){
            throw new RuntimeException("Il token è già stato utilizzato");
        }
        if(passwordResetToken.getExpiryDate().isBefore(LocalDateTime.now())){
            throw new RuntimeException("Il token è scaduto");
        }

        
    }

    public void resetPassword(ResetPasswordRequest resetPasswordRequest) {
        
        verificaPassword(resetPasswordRequest);
        verificaPresenzaTokenDB(resetPasswordRequest);

        PasswordResetToken passwordResetToken = tokenRepository.findByToken(resetPasswordRequest.getToken()).get();
        Utente utente = passwordResetToken.getUtente();

        

        
        String passwordCriptata=passwordEncoder.encode(resetPasswordRequest.getNuovaPassword());
        utente.setPassword(passwordCriptata);
        utenteRepository.save(utente);

        passwordResetToken.setUsed(true);
        tokenRepository.save(passwordResetToken);
        tokenRepository.deleteByUtente(utente); // Elimina eventuali token precedenti per l'utente
    }

}