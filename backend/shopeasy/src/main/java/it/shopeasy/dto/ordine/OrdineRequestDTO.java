package it.shopeasy.dto.ordine;

import java.util.List;

import it.shopeasy.dto.prodotto.ProdottoOrdineRequestDTO;

public class OrdineRequestDTO {

    private Long utenteId;
    private List<ProdottoOrdineRequestDTO> prodotti;

    public OrdineRequestDTO() {}

    public OrdineRequestDTO(Long utenteId, List<ProdottoOrdineRequestDTO> prodotti) {
        this.utenteId = utenteId;
        this.prodotti = prodotti;
    }

    public Long getUtenteId() {
        return utenteId;
    }

    public void setUtenteId(Long utenteId) {
        this.utenteId = utenteId;
    }

    public List<ProdottoOrdineRequestDTO> getProdotti() {
        return prodotti;
    }

    public void setProdotti(List<ProdottoOrdineRequestDTO> prodotti) {
        this.prodotti = prodotti;
    }
}
