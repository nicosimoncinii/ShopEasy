package it.shopeasy.service;

import it.shopeasy.dto.WishlistRequestDTO;
import it.shopeasy.dto.WishlistResponseDTO;
import it.shopeasy.model.Wishlist;
import it.shopeasy.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    public List<WishlistResponseDTO> prendiTuttiWishlist(){
        return wishlistRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public WishlistResponseDTO prendiWishlistResponsePerId(Long id) {
        return toResponse(prendiWishlistPerId(id));
    }

    private Wishlist prendiWishlistPerId(Long id) {
        return wishlistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Wishlist non trovata con id: " + id));
    }

    public void cancellaWishlist(Long id) {
        wishlistRepository.deleteById(id);
    }

    public WishlistResponseDTO salvaWishlist(WishlistRequestDTO request) {
        Wishlist wishlist = new Wishlist();
        // Se la tua entità ha metodi set, inseriscili qui usando l'oggetto 'request'
        return toResponse(wishlistRepository.save(wishlist));
    }

    public WishlistResponseDTO aggiornaWishlist(Long id, WishlistRequestDTO nuovaRequest) {
        Wishlist wishlist = prendiWishlistPerId(id);
        // Se devi aggiornare i dati, usa l'oggetto 'nuovaRequest' qui
        return toResponse(wishlistRepository.save(wishlist));
    }

    private WishlistResponseDTO toResponse(Wishlist wishlist) {
        return new WishlistResponseDTO(
                wishlist.getId(),
                wishlist.getUtente(),
                wishlist.getProdotti()
        );
    }
}
