package it.shopeasy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "statistiche")
public class Statistiche {
    @Id
    private Long id;


    @Column(name = "fatturato_totale")
    private Double fatturatoTotale;

    @Column(name = "visualizzazioni")
    private Long visualizzazioni;

    @Column(name = "utenti_registrati")
    private Long utenti_registrati;

    @Column(name = "ordini_totali")
    private Long ordini_totali;

    @Column(name = "prodotti_totali")
    private Long prodotti_totali;


    public Statistiche() {
    }

    public Statistiche(Double fatturatoTotale, Long visualizzazioni, Long utenti_registrati, Long ordini_totali,  Long prodotti_totali) {
        this.fatturatoTotale = fatturatoTotale;
        this.visualizzazioni = visualizzazioni;
        this.utenti_registrati = utenti_registrati;
        this.ordini_totali = ordini_totali;
        this.prodotti_totali = prodotti_totali;
    }

    // --- GETTER & SETTER ---

    public Long getId() {
        return id;
    }


    public Double getFatturatoTotale() {
        return fatturatoTotale;
    }

    public void setFatturatoTotale(Double fatturatoTotale) {
        this.fatturatoTotale = fatturatoTotale;
    }

    public Long getVisualizzazioni() {
        return visualizzazioni;
    }

    public void setVisualizzazioni(Long visualizzazioni) {
        this.visualizzazioni = visualizzazioni;
    }

    public Long getUtenti_registrati() {
        return utenti_registrati;
    }

    public void setUtenti_registrati(Long utenti_registrati) {
        this.utenti_registrati = utenti_registrati;
    }

    public Long getOrdini_totali() {
        return ordini_totali;
    }

    public void setOrdini_totali(Long ordini_totali) {
        this.ordini_totali = ordini_totali;
    }

    public Long getProdotti_totali() {
        return prodotti_totali;
    }

    public void setProdotti_totali(Long prodotti_totali) {
        this.prodotti_totali = prodotti_totali;
    }

}
