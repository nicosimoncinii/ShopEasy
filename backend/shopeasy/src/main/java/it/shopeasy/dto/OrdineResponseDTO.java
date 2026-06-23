package it.shopeasy.dto;

import it.shopeasy.enums.StatoOrdine;
import it.shopeasy.model.DettaglioOrdine;

import java.time.LocalDateTime;
import java.util.List;

public class OrdineResponseDTO {

    private Long id;
    private LocalDateTime data;
    private StatoOrdine stato;
    private Double totale;
    private Long utenteId;
    private String utenteNome;
   private List<DettaglioOrdine> dettagli;

    public OrdineResponseDTO() {}

    public OrdineResponseDTO(Long id, LocalDateTime data, StatoOrdine stato, Double totale,
                             Long utenteId, String utenteNome,
                             List<DettaglioOrdine> dettagli) {
        this.id = id;
        this.data = data;
        this.stato = stato;
        this.totale = totale;
        this.utenteId = utenteId;
        this.utenteNome = utenteNome;
        this.dettagli = dettagli;
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

    public List<DettaglioOrdine> getProdotti() {
        return dettagli;
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

    public void setProdotti(List<DettaglioOrdine> dettagli) {
        this.dettagli = dettagli;
    }
}
