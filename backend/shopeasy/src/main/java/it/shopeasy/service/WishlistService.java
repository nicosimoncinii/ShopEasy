package it.shopeasy.service;

import it.shopeasy.model.Wishlist;
import it.shopeasy.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    public List<Wishlist> prendiTuttiWishlist(){
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

    public Wishlist salvaWishlist(Wishlist wishlist) {

        return wishlistRepository.save(wishlist);
    }

    public Wishlist aggiornaWishlist(Long id, Wishlist nuovoWishlist) {
        Wishlist wishlist = prendiWishlistPerId(id);

        wishlist.setUtente(nuovoWishlist.getUtente());


        return salvaWishlist(wishlist);
    }
}
