package it.shopeasy.shopeasy.repository;

import it.shopeasy.shopeasy.model.Ordine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface OrdineRepository extends JpaRepository<Ordine, Long>{
    
}
