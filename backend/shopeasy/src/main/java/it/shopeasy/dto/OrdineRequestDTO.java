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
    @NotNull(message = "Lo stato dell'ordine è necessario")
    private StatoOrdine stato;

    public OrdineRequestDTO() {}

    public OrdineRequestDTO(Long utenteId, Set<DettaglioOrdineRequestDTO> dettagli,StatoOrdine stato) {
        this.utenteId = utenteId;
        this.dettagli = dettagli;
        this.stato = stato;
    }

    public Long getUtenteId() {
        return utenteId;
    }

    public void setUtenteId(Long utenteId) {
        this.utenteId = utenteId;
    }

    public Set<DettaglioOrdine> getDettagli() {
        return dettagli;
    }

    public void setDettagli(Set<DettaglioOrdineRequestDTO> dettagli) {
        this.dettagli = dettagli;
    }

    public StatoOrdine getStato(){
        return stato;
    }
    public void setStato(StatoOrdine stato){
        this.stato = stato;
    }
}