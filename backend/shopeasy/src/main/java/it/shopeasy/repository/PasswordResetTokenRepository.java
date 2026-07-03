package it.shopeasy.repository;

import it.shopeasy.model.PasswordResetToken;
import it.shopeasy.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;


import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);
    @Modifying // <--- Obbligatorio per le query di cancellazione derivative
    void deleteByUtente(Utente utente);
}