package it.shopeasy.dto;

public class ProdottoOrdineRequestDTO {

    private Long prodottoId;
    private Integer quantita;

    public ProdottoOrdineRequestDTO() {}

    public ProdottoOrdineRequestDTO(Long prodottoId, Integer quantita) {
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