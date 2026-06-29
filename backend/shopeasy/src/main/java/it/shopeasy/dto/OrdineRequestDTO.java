package it.shopeasy.dto;

import it.shopeasy.enums.StatoOrdine;
import it.shopeasy.model.DettaglioOrdine;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public class OrdineRequestDTO {

    @NotNull
    private Long utenteId;
    @NotNull
    @NotEmpty
    private Set<DettaglioOrdineRequestDTO> dettagli;


    public OrdineRequestDTO() {}

    public OrdineRequestDTO(Long utenteId, Set<DettaglioOrdineRequestDTO> dettagli) {
        this.utenteId = utenteId;
        this.dettagli = dettagli;

    }

    public Long getUtenteId() {
        return utenteId;
    }

    public void setUtenteId(Long utenteId) {
        this.utenteId = utenteId;
    }

    public Set<DettaglioOrdineRequestDTO> getDettagli() {
        return dettagli;
    }

    public void setDettagli(Set<DettaglioOrdineRequestDTO> dettagli) {
        this.dettagli = dettagli;
    }


}