package it.shopeasy.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class WishlistRequestDTO {


    @NotNull(message = "L'id dell'utente è necessario")
    private Long utenteId;
    @NotNull(message = "L'id del prodotto è necessario")
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
