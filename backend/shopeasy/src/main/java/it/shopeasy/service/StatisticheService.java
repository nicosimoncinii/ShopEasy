package it.shopeasy.service;

import it.shopeasy.model.Statistiche;
import it.shopeasy.dto.StatisticheResponseDTO;
import it.shopeasy.repository.StatisticheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticheService {

    @Autowired
    private StatisticheRepository statisticheRepository;

    public StatisticheResponseDTO prendiStatistiche(){
        //dichiarazione oggetto statistiche e assegnazione prelevando dal repository tramite id
        Statistiche statistiche = statisticheRepository.findById(1L).orElse(null);

        if (statistiche == null) {
            return null;
        }
        //ritorna oggetto StatisticheResponseDTO con i valori prelevati da statistiche
        return new StatisticheResponseDTO(
                statistiche.getFatturatoTotale(),
                statistiche.getVisualizzazioni(),
                statistiche.getUtentiRegistrati(),
                statistiche.getOrdiniTotali(),
                statistiche.getProdottiTotali()
        );
    }




}
