package it.shopeasy.controller;

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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private ProdottoRepository prodottoRepository;

    @Autowired
    private WishlistRepository wishlistRepository;

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
    public ResponseEntity<Wishlist> salvaWishlist(@RequestBody Wishlist wishlist) {
        Wishlist creato = wishlistService.salvaWishlist(wishlist);
        return ResponseEntity.status(HttpStatus.CREATED).body(creato);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Wishlist> aggiornaWishlist(@RequestBody Wishlist wishlist, @PathVariable Long id) {
        Wishlist aggiornato = wishlistService.aggiornaWishlist(id, wishlist);
        return ResponseEntity.ok(aggiornato);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancellaWishlist(@PathVariable Long id) {
        wishlistService.cancellaWishlist(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<List<Prodotto>> getMyWishlist() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Utente utente = utenteRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        Wishlist wishlist = wishlistRepository.findByUtente(utente)
                .orElseGet(() -> {
                    Wishlist nuova = new Wishlist();
                    nuova.setUtente(utente);
                    return wishlistRepository.save(nuova);
                });

        return ResponseEntity.ok(wishlist.getProdotti());
    }

    @PostMapping("/add/{id}")
    public ResponseEntity<String> addToWishlist(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Utente utente = utenteRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        Prodotto prodotto = prodottoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prodotto non trovato con ID: " + id));

        Wishlist wishlist = wishlistRepository.findByUtente(utente)
                .orElseGet(() -> {
                    Wishlist nuova = new Wishlist();
                    nuova.setUtente(utente);
                    return wishlistRepository.save(nuova);
                });

        if (wishlist.getProdotti().contains(prodotto)) {
            return ResponseEntity.badRequest().body("Prodotto già presente nella wishlist");
        }

        wishlist.aggiungiProdotto(prodotto);
        wishlistRepository.save(wishlist);

        return ResponseEntity.status(HttpStatus.CREATED).body("Prodotto aggiunto alla wishlist");
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<String> removeFromWishlist(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Utente utente = utenteRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        Prodotto prodotto = prodottoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prodotto non trovato con ID: " + id));

        Wishlist wishlist = wishlistRepository.findByUtente(utente)
                .orElseThrow(() -> new RuntimeException("Wishlist non trovata"));

        if (!wishlist.getProdotti().contains(prodotto)) {
            return ResponseEntity.badRequest().body("Prodotto non presente nella wishlist");
        }

        wishlist.rimuoviProdotto(prodotto);
        wishlistRepository.save(wishlist);

        return ResponseEntity.ok("Prodotto rimosso dalla wishlist");
    }
}