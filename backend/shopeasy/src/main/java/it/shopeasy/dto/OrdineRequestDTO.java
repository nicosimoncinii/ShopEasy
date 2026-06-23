package it.shopeasy.dto;

import it.shopeasy.model.DettaglioOrdine;
import java.util.List;

public class OrdineRequestDTO {

    private Long utenteId;
    private List<DettaglioOrdine> dettagli;

    public OrdineRequestDTO() {}

    public OrdineRequestDTO(Long utenteId, List<DettaglioOrdine> dettagli) {
        this.utenteId = utenteId;
        this.dettagli = dettagli;
    }

    public Long getUtenteId() {
        return utenteId;
    }

    public void setUtenteId(Long utenteId) {
        this.utenteId = utenteId;
    }

    public List<DettaglioOrdine> getDettagli() {
        return dettagli;
    }

    public void setDettagli(List<DettaglioOrdine> dettagli) {
        this.dettagli = dettagli;
    }
}