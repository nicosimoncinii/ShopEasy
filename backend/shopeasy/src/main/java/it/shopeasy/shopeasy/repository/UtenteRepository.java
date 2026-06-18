package it.shopeasy.shopeasy.repository;

import it.shopeasy.shopeasy.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UtenteRepository extends JpaRepository<Utente, Long>{

}
