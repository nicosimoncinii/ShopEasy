package it.shopeasy.controller;

import it.shopeasy.dto.WishlistResponseDTO;
import it.shopeasy.model.Prodotto;
import it.shopeasy.model.Utente;
import it.shopeasy.model.Wishlist;
import it.shopeasy.repository.ProdottoRepository;
import it.shopeasy.repository.UtenteRepository;
import it.shopeasy.repository.WishlistRepository;
import it.shopeasy.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<WishlistResponseDTO>> prendiTutteLeWishlist() {
        return ResponseEntity.ok(wishlistService.prendiTutteLeWishlist());
    }

    @GetMapping("/{utenteId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<WishlistResponseDTO> prendiWishlistPerUtente(@PathVariable Long utenteId) {
        return ResponseEntity.ok(wishlistService.prendiWishlistPerUtenteId(utenteId));
    }

    @GetMapping("/me")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENTE')")
    public ResponseEntity<WishlistResponseDTO> prendiMiaWishlist(Principal principal) {
        return ResponseEntity.ok(wishlistService.prendiWishlistPerUtente(principal.getName()));
    }

    @PostMapping("/me/prodotti/{prodottoId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENTE')")
    public ResponseEntity<WishlistResponseDTO> aggiungiProdotto(@PathVariable Long prodottoId,
                                                                Principal principal) {
        return ResponseEntity.ok(wishlistService.aggiungiProdotto(principal.getName(), prodottoId));
    }

    @DeleteMapping("/me/prodotti/{prodottoId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENTE')")
    public ResponseEntity<WishlistResponseDTO> rimuoviProdotto(@PathVariable Long prodottoId,
                                                               Principal principal) {
        return ResponseEntity.ok(wishlistService.rimuoviProdotto(principal.getName(), prodottoId));
    }
}