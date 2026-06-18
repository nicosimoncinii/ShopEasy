package it.shopeasy.shopeasy.repository;

import it.shopeasy.shopeasy.model.Prodotto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ProdottoRepository extends JpaRepository<Prodotto, Long>{

}
