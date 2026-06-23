package it.shopeasy.service;

import it.shopeasy.model.Ordine;

import it.shopeasy.dto.OrdineResponseDTO;
import it.shopeasy.repository.OrdineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;



@Service
public class OrdineService {

    @Autowired
    private OrdineRepository ordineRepository;


    public List<OrdineResponseDTO> prendiTuttiOrdini() {
        return ordineRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public OrdineResponseDTO prendiOrdineResponsePerId(Long id) {
        return toResponse(prendiOrdinePerId(id));
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

    public List<Ordine> prendiOrdiniPerUtente(Long utente_id) {
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

    private OrdineResponseDTO toResponse(Ordine ordine) {
        return new OrdineResponseDTO(
            ordine.getId(),
            ordine.getData(),
            ordine.getStato(),
            ordine.getTotale(),
            ordine.getUtente().getId(),
            ordine.getUtente().getNome(),
            ordine.getDettagli()
        );
    }

}
