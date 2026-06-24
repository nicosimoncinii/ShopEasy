package it.shopeasy.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class DettaglioOrdineRequestDTO {

    @NotNull
    private Long prodottoId;
    @NotNull
    @Positive
    private Integer quantita;

    public DettaglioOrdineRequestDTO() {}

    public DettaglioOrdineRequestDTO(Long prodottoId, Integer quantita) {
        this.prodottoId = prodottoId;
        this.quantita = quantita;
    }

    public Long getProdottoId() {
        return prodottoId;
    }

    public void setProdottoId(Long prodottoId) {
        this.prodottoId = prodottoId;
    }

    public Integer getQuantita() {
        return quantita;
    }

    public void setQuantita(Integer quantita) {
        this.quantita = quantita;
    }
}