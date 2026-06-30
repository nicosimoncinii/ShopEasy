package it.shopeasy.dto;

public class StatisticheResponseDTO {
    private Double fatturatoTotale;
    private Long visualizzazioni;
    private Long utentiRegistrati;
    private Long ordiniTotali;
    private Long prodottiTotali;

    public StatisticheResponseDTO(Double fatturatoTotale, Long visualizzazioni, Long utentiRegistrati, Long ordiniTotali, Long prodottiTotali) {
        this.fatturatoTotale = fatturatoTotale;
        this.visualizzazioni = visualizzazioni;
        this.utentiRegistrati = utentiRegistrati;
        this.ordiniTotali = ordiniTotali;
        this.prodottiTotali = prodottiTotali;
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
}
