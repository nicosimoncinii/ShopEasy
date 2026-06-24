package it.shopeasy.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table(name = "wishlist")
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "utente_id", nullable = false, unique = true)
    @JsonIgnore
    private Utente utente;

    @ManyToMany
    @JoinTable(
            name = "wishlist_prodotto",
            joinColumns = @JoinColumn(name = "wishlist_id"),
            inverseJoinColumns = @JoinColumn(name = "prodotto_id")
    )
    private Set<Prodotto> prodotti = new HashSet<Prodotto>();

    public Wishlist() {
    }

    public Wishlist(Utente utente) {
        this.utente = utente;
    }

    public Long getId() {
        return id;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public Set<Prodotto> getProdotti() {
        return prodotti;
    }

    public void setProdotti(Set<Prodotto> prodotti) {
        this.prodotti = prodotti;
    }

    public void aggiungiProdotto(Prodotto prodotto) {
        prodotti.add(prodotto);
    }

    public void rimuoviProdotto(Prodotto prodotto) {
        prodotti.remove(prodotto);
    }
}