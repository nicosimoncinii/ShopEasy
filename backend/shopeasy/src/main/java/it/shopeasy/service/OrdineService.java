package it.shopeasy.service;

import it.shopeasy.dto.OrdineRequestDTO;
import it.shopeasy.enums.StatoOrdine;
import it.shopeasy.model.Ordine;

import it.shopeasy.dto.OrdineResponseDTO;
import it.shopeasy.repository.OrdineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;




@Service
public class OrdineService {

    @Autowired
    private OrdineRepository ordineRepository;
    @Autowired
    private UtenteService utenteService;

    public List<OrdineResponseDTO> prendiTuttiOrdini() {
        return ordineRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
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

    public OrdineResponseDTO salvaOrdine(OrdineRequestDTO ordineR) {
        Ordine ordine = new Ordine();

        ordine.setData(LocalDateTime.now());
        ordine.setDettagli(ordineR.getDettagli());
        ordine.setStato(StatoOrdine.ORDINATO);
        ordine.setTotale(0.0);
        ordine.setUtente(utenteService.prendiUtentePerId(ordineR.getUtenteId()));

        return toResponse(ordineRepository.save(ordine));
    }

    public List<OrdineResponseDTO> prendiOrdiniPerUtente(Long utenteId) {
        return ordineRepository.findByUtenteId(utenteId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public OrdineResponseDTO aggiornaOrdine(Long id, OrdineRequestDTO nuovoOrdine) {
        Ordine ordine = prendiOrdinePerId(id);

        ordine.setDettagli(nuovoOrdine.getDettagli());

        return toResponse(ordineRepository.save(ordine));
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
