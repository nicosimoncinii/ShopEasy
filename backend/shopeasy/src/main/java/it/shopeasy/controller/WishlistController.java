package it.shopeasy.controller;

import it.shopeasy.dto.WishlistResponseDTO;
import it.shopeasy.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/api/wishlist")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<WishlistResponseDTO>> prendiTutteLeWishlist() {
        return ResponseEntity.ok(wishlistService.prendiTutteLeWishlist());
    }

    @GetMapping("/{utenteId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
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

    /*
    @PostMapping("/me/checkout")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENTE')")
    public ResponseEntity<WishlistResponseDTO> checkoutWishlist(Principal principal) {
        return ResponseEntity.ok(wishlistService.checkoutWishlist(principal.getName()));
    }

     */
}