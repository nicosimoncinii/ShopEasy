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

    public Ordine prendiOrdinePerId(Long id) {
        return ordineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ordine non trovato con id: " + id));
    }

    public void cancellaOrdine(Long id) {
        ordineRepository.deleteById(id);
    }

    public Ordine salvaOrdine(Ordine ordine) {

        return ordineRepository.save(ordine);
    }

    public List<Ordine> prendiOrdiniPerUtente(Long utente_id){
        return ordineRepository.findByUtenteId(utente_id);
    }

    public Ordine aggiornaOrdine(Long id, Ordine nuovoOrdine) {
        Ordine ordine = prendiOrdinePerId(id);

        ordine.setUtente(nuovoOrdine.getUtente());
        ordine.setData(nuovoOrdine.getData());
        ordine.setStato(nuovoOrdine.getStato());
        ordine.setTotale(ordine.getTotale());


        return salvaOrdine(ordine);
    }
    
}
