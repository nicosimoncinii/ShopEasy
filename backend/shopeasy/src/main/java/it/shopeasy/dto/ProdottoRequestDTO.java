package it.shopeasy.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ProdottoRequestDTO {


    @NotNull(message = "Il nome è necessario")
    private String nome;

    private String descrizione;

    @NotNull(message = "Il prezzo è necessario")
    @Positive(message = "Il prezzo deve essere un valore positivo")
    private Double prezzo;

    @NotNull(message = "La quantità è necessaria")
    @Positive(message = "La quantità deve essere un valore positivo")
    private Integer quantita;

    private String immagine;

    private Long categoriaId;


    public ProdottoRequestDTO() {

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(Double prezzo) {
        this.prezzo = prezzo;
    }

    public Integer getQuantita() {
        return quantita;
    }

    public void setQuantita(Integer quantita) {
        this.quantita = quantita;
    }

    public String getImmagine() {
        return immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }
}
