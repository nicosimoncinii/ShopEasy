package it.shopeasy.dto.ordine;

import java.time.LocalDateTime;
import java.util.List;

import it.shopeasy.dto.prodotto.ProdottoOrdineResponseDTO;
public class OrdineResponseDTO {

    private Long id;
    private LocalDateTime data;
    private String stato;
    private Double totale;
    private Long utenteId;
    private String utenteNome;
    private List<ProdottoOrdineResponseDTO> prodotti;

    public OrdineResponseDTO() {}

    public OrdineResponseDTO(Long id, LocalDateTime data, String stato, Double totale,
                             Long utenteId, String utenteNome,
                             List<ProdottoOrdineResponseDTO> prodotti) {
        this.id = id;
        this.data = data;
        this.stato = stato;
        this.totale = totale;
        this.utenteId = utenteId;
        this.utenteNome = utenteNome;
        this.prodotti = prodotti;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getData() {
        return data;
    }

    public String getStato() {
        return stato;
    }

    public Double getTotale() {
        return totale;
    }

    public Long getUtenteId() {
        return utenteId;
    }

    public String getUtenteNome() {
        return utenteNome;
    }

    public List<ProdottoOrdineResponseDTO> getProdotti() {
        return prodotti;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public void setTotale(Double totale) {
        this.totale = totale;
    }

    public void setUtenteId(Long utenteId) {
        this.utenteId = utenteId;
    }

    public void setUtenteNome(String utenteNome) {
        this.utenteNome = utenteNome;
    }

    public void setProdotti(List<ProdottoOrdineResponseDTO> prodotti) {
        this.prodotti = prodotti;
    }
}
