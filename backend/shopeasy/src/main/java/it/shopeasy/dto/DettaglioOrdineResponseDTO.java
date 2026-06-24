package it.shopeasy.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public class DettaglioOrdineResponseDTO {


    private Long prodottoId;

    private String nome;

    private Double prezzoUnitario;

    private Integer quantita;


    public DettaglioOrdineResponseDTO() {
    }

    public DettaglioOrdineResponseDTO(Long prodottoId, String nome, Double prezzoUnitario, Integer quantita) {
        this.prodottoId = prodottoId;
        this.nome = nome;
        this.prezzoUnitario = prezzoUnitario;
        this.quantita = quantita;
    }

    public Long getProdottoId() {
        return prodottoId;
    }

    public void setProdottoId(Long prodottoId) {
        this.prodottoId = prodottoId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
}
