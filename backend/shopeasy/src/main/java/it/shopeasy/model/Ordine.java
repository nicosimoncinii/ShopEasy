package it.shopeasy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.shopeasy.enums.StatoOrdine;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ordine")
public class Ordine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime data;

    @OneToMany(mappedBy = "ordine", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<DettaglioOrdine> dettagli = new ArrayList<>();

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

    public Ordine(LocalDateTime data, Double totale, StatoOrdine stato, Utente utente) {
        this.data = data;
        this.totale = totale;
        this.stato = stato;
        this.utente = utente;
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

    public List<DettaglioOrdine> getDettagli() {
        return dettagli;
    }

    public void setDettagli(List<DettaglioOrdine> dettagli) {
        this.dettagli = dettagli;
    }
}