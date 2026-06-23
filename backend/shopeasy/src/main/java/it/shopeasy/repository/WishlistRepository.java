package it.shopeasy.repository;

import it.shopeasy.model.Utente;
import it.shopeasy.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    Optional<Wishlist> findByUtente(Utente utente);
}