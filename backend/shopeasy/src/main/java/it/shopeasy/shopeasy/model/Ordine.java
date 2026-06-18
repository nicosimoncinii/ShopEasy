package it.shopeasy.shopeasy.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ordini")
public class Ordine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime data;

    private Double totale;

    @Enumerated(EnumType.STRING)
    private StatoOrdine stato;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;


    public Ordine() {
    }

    public Ordine(LocalDateTime data,
                  Double totale,
                  StatoOrdine stato,
                  Utente utente) {

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

}