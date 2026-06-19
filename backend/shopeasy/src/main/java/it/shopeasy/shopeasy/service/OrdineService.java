package it.shopeasy.shopeasy.service;

import it.shopeasy.shopeasy.model.Ordine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import it.shopeasy.shopeasy.repository.OrdineRepository;
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

    public Ordine aggiornaOrdine(Long id, Ordine nuovoOrdine) {
        Ordine ordine = prendiOrdinePerId(id);

        ordine.setUtente(nuovoOrdine.getUtente());
        ordine.setData(nuovoOrdine.getData());
        ordine.setStato(nuovoOrdine.getStato());
        ordine.setTotale(ordine.getTotale());


        return salvaOrdine(ordine);
    }
    
}
