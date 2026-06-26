package it.shopeasy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.shopeasy.enums.StatoOrdine;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "ordine")
public class Ordine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime data;

    @OneToMany(mappedBy = "ordine", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<DettaglioOrdine> dettagli = new HashSet<>();

    @Column(nullable = false)
    private Double totale;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatoOrdine stato;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    @JsonIgnore
    private Utente utente;

    public Ordine() {
    }

    public Ordine(LocalDateTime data, Double totale, StatoOrdine stato, Utente utente, Set<DettaglioOrdine> dettagli) {
        this.data = data;
        this.totale = totale;
        this.stato = stato;
        this.utente = utente;
        this.dettagli = dettagli;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Double getTotale() {
        return totale;
    }

    public void setTotale(Double totale) {
        this.totale = totale;
    }

    public StatoOrdine getStato() {
        return stato;
    }

    public void setStato(StatoOrdine stato) {
        this.stato = stato;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public Set<DettaglioOrdine> getDettagli() {
        return dettagli;
    }

    public void setDettagli(Set<DettaglioOrdine> dettagli) {
        this.dettagli = dettagli;
    }
}