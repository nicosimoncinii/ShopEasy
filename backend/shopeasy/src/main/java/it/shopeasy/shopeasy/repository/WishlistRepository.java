package it.shopeasy.shopeasy.repository;


import it.shopeasy.shopeasy.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface WishlistRepository extends JpaRepository<Wishlist, Long>{

}
