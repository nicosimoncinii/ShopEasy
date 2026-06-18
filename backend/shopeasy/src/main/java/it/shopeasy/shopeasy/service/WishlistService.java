package it.shopeasy.shopeasy.service;

import it.shopeasy.shopeasy.model.Wishlist;
import it.shopeasy.shopeasy.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    public List<Wishlist> prendiTuttiUtenti(){
        return wishlistRepository.findAll();
    }

    public Wishlist prendiWishlistPerId(Long id) {
        return wishlistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Wishlist non trovato con id: " + id));
    }

    public void cancellaWishlist(Long id)
    {
        wishlistRepository.deleteById(id);
    }
}
