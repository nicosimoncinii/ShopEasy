package it.shopeasy.model;

import it.shopeasy.dto.UtenteResponseDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.data.domain.Page;

import java.util.List;

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
    private Long utentiRegistrati;

    @Column(name = "ordini_totali")
    private Long ordiniTotali;

    @Column(name = "prodotti_totali")
    private Long prodottiTotali;


    public Statistiche() {
    }

    public Statistiche(Double fatturatoTotale, Long visualizzazioni, Long utentiRegistrati, Long ordiniTotali,  Long prodottiTotali) {
        this.fatturatoTotale = fatturatoTotale;
        this.visualizzazioni = visualizzazioni;
        this.utentiRegistrati = utentiRegistrati;
        this.ordiniTotali = ordiniTotali;
        this.prodottiTotali = prodottiTotali;

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

    public Long getUtentiRegistrati() {
        return utentiRegistrati;
    }

    public void setUtentiRegistrati(Long utenti_registrati) {
        this.utentiRegistrati = utenti_registrati;
    }

    public Long getOrdiniTotali() {
        return ordiniTotali;
    }

    public void setOrdiniTotali(Long ordini_totali) {
        this.ordiniTotali = ordini_totali;
    }

    public Long getProdottiTotali() {
        return prodottiTotali;
    }

    public void setProdottiTotali(Long prodotti_totali) {
        this.prodottiTotali = prodotti_totali;
    }



}
