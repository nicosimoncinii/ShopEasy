package it.shopeasy.service;

import it.shopeasy.model.Ordine;
import it.shopeasy.repository.OrdineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdineService {

    @Autowired
    private OrdineRepository ordineRepository;

    public List<Ordine> prendiTuttiOrdini() {
        return ordineRepository.findAll();
    }

    public List<Ordine> prendiOrdiniPerUtente(Long utente_id) {
        return ordineRepository.findByUtenteId(utente_id);
    }

    public Ordine salvaOrdine(Ordine ordine) {
        return ordineRepository.save(ordine);
    }

    public Ordine aggiornaOrdine(Long id, Ordine nuovoOrdine) {
        Ordine ordine = ordineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ordine non trovato con id: " + id));

        if (nuovoOrdine.getUtente() != null) {
            ordine.setUtente(nuovoOrdine.getUtente());
        }
        if (nuovoOrdine.getData() != null) {
            ordine.setData(nuovoOrdine.getData());
        }
        if (nuovoOrdine.getStato() != null) {
            ordine.setStato(nuovoOrdine.getStato());
        }
        if (nuovoOrdine.getTotale() != null) {
            ordine.setTotale(nuovoOrdine.getTotale());
        }

        return salvaOrdine(ordine);
    }
}