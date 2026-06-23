package it.shopeasy.dto;

public class WishlistRequestDTO {

    private Long utenteId;
    private Long prodottoId;

    public WishlistRequestDTO() {
    }

    public WishlistRequestDTO(Long utenteId, Long prodottoId) {
        this.utenteId = utenteId;
        this.prodottoId = prodottoId;
    }

    public Long getUtenteId() {
        return utenteId;
    }

    public void setUtenteId(Long utenteId) {
        this.utenteId = utenteId;
    }

    public Long getProdottoId() {
        return prodottoId;
    }

    public void setProdottoId(Long prodottoId) {
        this.prodottoId = prodottoId;
    }
}
