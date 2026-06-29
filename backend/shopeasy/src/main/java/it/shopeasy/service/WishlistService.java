package it.shopeasy.service;

import it.shopeasy.dto.ProdottoResponseDTO;
import it.shopeasy.dto.WishlistRequestDTO;
import it.shopeasy.dto.WishlistResponseDTO;
import it.shopeasy.model.Prodotto;
import it.shopeasy.model.Utente;
import it.shopeasy.model.Wishlist;
import it.shopeasy.repository.ProdottoRepository;
import it.shopeasy.repository.UtenteRepository;
import it.shopeasy.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WishlistService {
    @Autowired
    private WishlistRepository wishlistRepository;
    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private ProdottoRepository prodottoRepository;

    public List<WishlistResponseDTO> prendiTutteLeWishlist() {
        return wishlistRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public WishlistResponseDTO prendiWishlistPerUtente(String email) {
        Utente utente = utenteRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        Wishlist wishlist = wishlistRepository.findByUtente(utente)
                .orElseThrow(() -> new RuntimeException("Wishlist non trovata"));
        return toResponse(wishlist);
    }

    public WishlistResponseDTO prendiWishlistPerUtenteId(Long utenteId) {
        Utente utente = utenteRepository.findById(utenteId)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        Wishlist wishlist = wishlistRepository.findByUtente(utente)
                .orElseThrow(() -> new RuntimeException("Wishlist non trovata"));
        return toResponse(wishlist);
    }

    public WishlistResponseDTO aggiungiProdotto(String email, Long prodottoId) {
        Utente utente = utenteRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        Wishlist wishlist = wishlistRepository.findByUtente(utente)
                .orElseThrow(() -> new RuntimeException("Wishlist non trovata"));
        Prodotto prodotto = prodottoRepository.findById(prodottoId)
                .orElseThrow(() -> new RuntimeException("Prodotto non trovato"));

        if (wishlist.getProdotti().contains(prodotto)) {
            throw new RuntimeException("Prodotto già presente nella wishlist");
        }
        if(prodotto.getQuantita() == 0)
            throw new IllegalArgumentException("Il prodotto deve essere in magazzino");

        wishlist.aggiungiProdotto(prodotto);
        return toResponse(wishlistRepository.save(wishlist));
    }

    public WishlistResponseDTO rimuoviProdotto(String email, Long prodottoId) {
        Utente utente = utenteRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        Wishlist wishlist = wishlistRepository.findByUtente(utente)
                .orElseThrow(() -> new RuntimeException("Wishlist non trovata"));
        Prodotto prodotto = prodottoRepository.findById(prodottoId)
                .orElseThrow(() -> new RuntimeException("Prodotto non trovato"));

        if (!wishlist.getProdotti().contains(prodotto)) {
            throw new RuntimeException("Prodotto non presente nella wishlist");
        }

        wishlist.rimuoviProdotto(prodotto);
        return toResponse(wishlistRepository.save(wishlist));
    }

    private WishlistResponseDTO toResponse(Wishlist wishlist) {
        Set<ProdottoResponseDTO> prodottiDTO = wishlist.getProdotti()
                .stream()
                .map(prodotto -> new ProdottoResponseDTO(
                        prodotto.getId(),
                        prodotto.getNome(),
                        prodotto.getDescrizione(),
                        prodotto.getPrezzo(),
                        prodotto.getQuantita(),
                        prodotto.getImmagine(),
                        prodotto.getCategoria() != null ? prodotto.getCategoria().getId() : null,
                        prodotto.getCategoria() != null ? prodotto.getCategoria().getNome() : null
                ))
                .collect(Collectors.toSet());

        return new WishlistResponseDTO(
                wishlist.getId(),
                wishlist.getUtente().getId(),
                prodottiDTO
        );
    }
}
