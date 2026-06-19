package it.shopeasy.model;


import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "prodotti")
public class Prodotto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(nullable = false)
    private String nome;

    private String descrizione;

    @Column(nullable = false)
    private Double prezzo;

    @Column(nullable = false)
    private Integer quantita;

    private String immagine;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;


    public Prodotto() {
    }

    public Prodotto(String nome, String descrizione, Double prezzo,
                    Integer quantita, String immagine, Categoria categoria) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
        this.quantita = quantita;
        this.immagine = immagine;
        this.categoria = categoria;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setPrezzo(Double prezzo) {
        this.prezzo = prezzo;
    }

    public void setQuantita(Integer quantita) {
        this.quantita = quantita;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void setId(Long id) {
    }
}