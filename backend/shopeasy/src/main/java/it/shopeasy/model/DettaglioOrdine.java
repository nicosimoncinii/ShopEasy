package it.shopeasy.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dettaglio_ordine")
public class DettaglioOrdine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ordine_id", nullable = false)
    @JsonIgnore
    private Ordine ordine;

    @ManyToOne
    @JoinColumn(name = "prodotto_id", nullable = false)
    private Prodotto prodotto;

    @Column(nullable = false)
    private Integer quantita;

    @Column(nullable = false)
    private Double prezzoUnitario;

    public DettaglioOrdine() {}

    public DettaglioOrdine(Ordine ordine, Prodotto prodotto, Integer quantita, Double prezzoUnitario) {
        this.ordine = ordine;
        this.prodotto = prodotto; // ✅ corretto
        this.quantita = quantita;
        this.prezzoUnitario = prezzoUnitario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrezzoUnitario() {
        return prezzoUnitario;
    }

    public void setPrezzoUnitario(Double prezzoUnitario) {
        this.prezzoUnitario = prezzoUnitario;
    }

    public Integer getQuantita() {
        return quantita;
    }

    public void setQuantita(Integer quantita) {
        this.quantita = quantita;
    }

    public Prodotto getProdotto() {
        return prodotto;
    }

    public void setProdotto(Prodotto prodotto) {
        this.prodotto = prodotto;
    }

    public Ordine getOrdine() {
        return ordine;
    }

    public void setOrdine(Ordine ordine) {
        this.ordine = ordine;
    }
}