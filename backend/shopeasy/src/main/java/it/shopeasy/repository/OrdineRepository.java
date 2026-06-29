package it.shopeasy.repository;

import it.shopeasy.model.Ordine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdineRepository extends JpaRepository<Ordine, Long>{
    public List<Ordine> findByUtenteId(Long utente_id);
    public List<Ordine> findByEmail(String email);
}
