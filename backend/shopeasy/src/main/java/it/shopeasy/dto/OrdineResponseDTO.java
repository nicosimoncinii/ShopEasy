package it.shopeasy.dto;

import it.shopeasy.enums.StatoOrdine;
import java.time.LocalDateTime;
import java.util.List;

public class OrdineResponseDTO {

    private Long id;
    private LocalDateTime data;
    private StatoOrdine stato;
    private Double totale;
    private Long utenteId;
    private String utenteNome;
    private List<?> dettagli;

    public OrdineResponseDTO() {}

    public OrdineResponseDTO(Long id, LocalDateTime data, StatoOrdine stato, Double totale, Long utenteId, String utenteNome) {
        this.id = id;
        this.data = data;
        this.stato = stato;
        this.totale = totale;
        this.utenteId = utenteId;
        this.utenteNome = utenteNome;
    }

    public OrdineResponseDTO(Long id, LocalDateTime data, StatoOrdine stato, Double totale, Long utenteId, String utenteNome, List<?> dettagli) {
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

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public StatoOrdine getStato() {
        return stato;
    }

    public void setStato(StatoOrdine stato) {
        this.stato = stato;
    }

    public Double getTotale() {
        return totale;
    }

    public void setTotale(Double totale) {
        this.totale = totale;
    }

    public Long getUtenteId() {
        return utenteId;
    }

    public void setUtenteId(Long utenteId) {
        this.utenteId = utenteId;
    }

    public String getUtenteNome() {
        return utenteNome;
    }

    public void setUtenteNome(String utenteNome) {
        this.utenteNome = utenteNome;
    }

    public List<?> getDettagli() {
        return dettagli;
    }

    public void setDettagli(List<?> dettagli) {
        this.dettagli = dettagli;
    }
}