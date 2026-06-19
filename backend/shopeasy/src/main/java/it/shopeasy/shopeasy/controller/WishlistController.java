package it.shopeasy.shopeasy.controller;

import it.shopeasy.shopeasy.model.Prodotto;
import it.shopeasy.shopeasy.model.Wishlist;
import it.shopeasy.shopeasy.service.WishlistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping
    public ResponseEntity<List<Wishlist>> prendiTuttiWishlist() {
        return ResponseEntity.ok(wishlistService.prendiTuttiWishlist());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Wishlist> prendiWishlistPerId(@PathVariable Long id) {
        return ResponseEntity.ok(wishlistService.prendiWishlistPerId(id));
    }

    @PostMapping
    public ResponseEntity<Wishlist> salvaWishlist(@RequestBody Wishlist wishlist){
        Wishlist creato = wishlistService.salvaWishlist(wishlist);
        return ResponseEntity.status(HttpStatus.CREATED).body(creato);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Wishlist> aggiornaWishlist(@RequestBody Wishlist wishlist, @PathVariable Long id)
    {
        Wishlist aggiornato = wishlistService.aggiornaWishlist(id,wishlist);
        return ResponseEntity.ok(aggiornato);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Wishlist> cancellaWishlist(@PathVariable Long id){
        wishlistService.cancellaWishlist(id);
        return ResponseEntity.noContent().build();
    }


}
