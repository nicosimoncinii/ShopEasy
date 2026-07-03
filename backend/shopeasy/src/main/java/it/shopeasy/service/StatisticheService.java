package it.shopeasy.service;

import it.shopeasy.dto.UtenteResponseDTO;
import it.shopeasy.enums.RuoloUtente;
import it.shopeasy.model.Statistiche;
import it.shopeasy.dto.StatisticheResponseDTO;
import it.shopeasy.model.Utente;
import it.shopeasy.repository.StatisticheRepository;
import it.shopeasy.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import it.shopeasy.dto.UtenteResponseDTO;

import java.util.List;

@Service
public class StatisticheService {

    @Autowired
    private StatisticheRepository statisticheRepository;
    @Autowired
    private UtenteRepository utenteRepository;

    public StatisticheResponseDTO prendiStatistiche(){
        //dichiarazione oggetto statistiche e assegnazione prelevando dal repository tramite id
        Statistiche statistiche = statisticheRepository.findById(1L).orElse(null);

        List<UtenteResponseDTO> utentiAdmin = utenteRepository.findByRuoloNome(RuoloUtente.ADMIN, Sort.by("id").ascending())
                .stream()
                .map(this::toResponse)
                .toList();



        if (statistiche == null) {
            return null;
        }
        //ritorna oggetto StatisticheResponseDTO con i valori prelevati da statistiche
        return new StatisticheResponseDTO(
                statistiche.getFatturatoTotale(),
                statistiche.getVisualizzazioni(),
                statistiche.getUtentiRegistrati(),
                statistiche.getOrdiniTotali(),
                statistiche.getProdottiTotali(),
                utentiAdmin
        );
    }

    private UtenteResponseDTO toResponse(Utente utente) {
        return new UtenteResponseDTO(
                utente.getId(),
                utente.getNome(),
                utente.getCognome(),
                utente.getEmail(),
                utente.getTelefono(),
                utente.getIndirizzo(),
                utente.getLanguagePreference(),
                utente.getThemePreference(),
                utente.getRuolo().getNome(),
                utente.getStato()

        );
    }




}
