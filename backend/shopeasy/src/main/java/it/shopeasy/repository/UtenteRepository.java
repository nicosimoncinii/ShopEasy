package it.shopeasy.repository;

import it.shopeasy.enums.RuoloUtente;
import it.shopeasy.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UtenteRepository extends JpaRepository<Utente, Long> {
    Optional<Utente> findByEmail(String email);
    boolean existsByEmail(String email);
    List<Utente> findByRuoloNome(RuoloUtente nome);
}