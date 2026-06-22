package it.shopeasy.repository;

import it.shopeasy.enums.RuoloUtente;
import it.shopeasy.model.Ruolo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RuoloRepository extends JpaRepository<Ruolo, Long> {
    Optional<Ruolo> findByNome(RuoloUtente nome);
}