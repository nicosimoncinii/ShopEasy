package it.shopeasy.repository;

import it.shopeasy.model.PasswordResetToken;
import it.shopeasy.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);
    void deleteByUtente(Utente utente);
}