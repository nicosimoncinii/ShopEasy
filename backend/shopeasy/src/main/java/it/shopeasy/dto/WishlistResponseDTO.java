package it.shopeasy.dto;

import it.shopeasy.dto.ProdottoResponseDTO;

import java.util.Set;

public class WishlistResponseDTO {
    private Long id;
    private Long utenteId;
    private Set<ProdottoResponseDTO> prodotti;

    public WishlistResponseDTO() {
    }

    public WishlistResponseDTO(Long id, Long utenteId, Set<ProdottoResponseDTO> prodotti) {
        this.id = id;
        this.utenteId = utenteId;
        this.prodotti = prodotti;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUtenteId() {
        return utenteId;
    }

    public void setUtenteId(Long utenteId) {
        this.utenteId = utenteId;
    }

    public Set<ProdottoResponseDTO> getProdotti() {
        return prodotti;
    }

    public void setProdotti(Set<ProdottoResponseDTO> prodotti) {
        this.prodotti = prodotti;
    }
}