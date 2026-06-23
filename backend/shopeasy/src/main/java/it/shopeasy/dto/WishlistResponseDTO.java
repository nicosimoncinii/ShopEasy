package it.shopeasy.dto;

public class WishlistResponseDTO {

    private Long id;
    private Long utenteId;
    private Long prodottoId;
    private String prodottoNome;
    private Double prodottoPrezzo;
    private String immagine;

    public WishlistResponseDTO() {
    }

    public WishlistResponseDTO(Long id, Long utenteId, Long prodottoId,
            String prodottoNome, Double prodottoPrezzo, String immagine) {
        this.id = id;
        this.utenteId = utenteId;
        this.prodottoId = prodottoId;
        this.prodottoNome = prodottoNome;
        this.prodottoPrezzo = prodottoPrezzo;
        this.immagine = immagine;
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

    public Long getProdottoId() {
        return prodottoId;
    }

    public void setProdottoId(Long prodottoId) {
        this.prodottoId = prodottoId;
    }

    public String getProdottoNome() {
        return prodottoNome;
    }

    public void setProdottoNome(String prodottoNome) {
        this.prodottoNome = prodottoNome;
    }

    public Double getProdottoPrezzo() {
        return prodottoPrezzo;
    }

    public void setProdottoPrezzo(Double prodottoPrezzo) {
        this.prodottoPrezzo = prodottoPrezzo;
    }

    public String getImmagine() {
        return immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }
}
