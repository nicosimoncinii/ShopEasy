package it.shopeasy.dto;

import org.springframework.data.domain.Page;

import java.util.List;

public class StatisticheResponseDTO {
    private Double fatturatoTotale;
    private Long visualizzazioni;
    private Long utentiRegistrati;
    private Long ordiniTotali;
    private Long prodottiTotali;
    private List<UtenteResponseDTO> utentiAmministratori;

    public StatisticheResponseDTO(Double fatturatoTotale, Long visualizzazioni, Long utentiRegistrati, Long ordiniTotali, Long prodottiTotali, List<UtenteResponseDTO> utentiAmministratori) {
        this.fatturatoTotale = fatturatoTotale;
        this.visualizzazioni = visualizzazioni;
        this.utentiRegistrati = utentiRegistrati;
        this.ordiniTotali = ordiniTotali;
        this.prodottiTotali = prodottiTotali;
        this.utentiAmministratori = utentiAmministratori;
    }

    public Double getFatturatoTotale() {
        return fatturatoTotale;
    }

    public void setFatturatoTotale(Double fatturatoTotale) {
        this.fatturatoTotale = fatturatoTotale;
    }

    public Long getVisualizzazioni() {
        return visualizzazioni;
    }

    public void setVisualizzazioni(Long visualizzazioni) {
        this.visualizzazioni = visualizzazioni;
    }

    public Long getUtentiRegistrati() {
        return utentiRegistrati;
    }

    public void setUtentiRegistrati(Long utentiRegistrati) {
        this.utentiRegistrati = utentiRegistrati;
    }

    public Long getOrdiniTotali() {
        return ordiniTotali;
    }

    public void setOrdiniTotali(Long ordiniTotali) {
        this.ordiniTotali = ordiniTotali;
    }

    public Long getProdottiTotali() {
        return prodottiTotali;
    }

    public void setProdottiTotali(Long prodottiTotali) {
        this.prodottiTotali = prodottiTotali;
    }

    public List<UtenteResponseDTO> getUtentiAmministratori() {
        return utentiAmministratori;
    }

    public void setUtentiAmministratori(List<UtenteResponseDTO> utentiAmministratori) {
        this.utentiAmministratori = utentiAmministratori;
    }
}
