package it.shopeasy.service;

import it.shopeasy.dto.OrdineRequestDTO;
import it.shopeasy.enums.StatoOrdine;
import it.shopeasy.model.DettaglioOrdine;
import it.shopeasy.model.Ordine;

import it.shopeasy.dto.OrdineResponseDTO;
import it.shopeasy.model.Prodotto;
import it.shopeasy.repository.OrdineRepository;
import it.shopeasy.repository.ProdottoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class OrdineService {

    @Autowired
    private OrdineRepository ordineRepository;
    @Autowired
    private UtenteService utenteService;
    @Autowired
    private ProdottoRepository prodottoRepository;

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

    @Transactional
    public OrdineResponseDTO salvaOrdine(OrdineRequestDTO ordineR) {
        Ordine ordine = new Ordine();
        ordine.setData(LocalDateTime.now());
        ordine.setStato(StatoOrdine.ORDINATO);
        ordine.setUtente(utenteService.prendiUtentePerId(ordineR.getUtenteId()));

        Set<DettaglioOrdine> dettagli = ordineR.getDettagli()
                .stream()
                .map(d -> {
                    Prodotto prodotto = prodottoRepository.findById(d.getProdottoId())
                            .orElseThrow(() -> new RuntimeException("Prodotto non trovato con id: " + d.getProdottoId()));
                    DettaglioOrdine dettaglio = new DettaglioOrdine();
                    dettaglio.setOrdine(ordine);
                    dettaglio.setProdotto(prodotto);
                    dettaglio.setQuantita(d.getQuantita());
                    dettaglio.setPrezzoUnitario(prodotto.getPrezzo() * d.getQuantita());
                    return dettaglio;
                })
                .collect(Collectors.toSet());

        ordine.setDettagli(dettagli);

        // calcola il totale sommando i prezzi dei dettagli
        Double totale = dettagli.stream()
                .mapToDouble(DettaglioOrdine::getPrezzoUnitario)
                .sum();
        ordine.setTotale(totale);

        ordine.getDettagli().forEach(dettaglio -> {
            Prodotto prodotto = dettaglio.getProdotto();
            if (prodotto.getQuantita() < dettaglio.getQuantita())
                throw new IllegalArgumentException("Quantità insufficiente per il prodotto: " + prodotto.getNome());
            prodotto.setQuantita(prodotto.getQuantita() - dettaglio.getQuantita());
            prodottoRepository.save(prodotto);
        });


        return toResponse(ordineRepository.save(ordine));




    }

    public List<OrdineResponseDTO> prendiOrdiniPerUtente(Long utenteId) {
        return ordineRepository.findByUtenteId(utenteId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<OrdineResponseDTO> prendiOrdiniPerUtente(String email) {
        return ordineRepository.findByUtenteEmail(email)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public OrdineResponseDTO aggiornaOrdine(Long id, OrdineRequestDTO nuovoOrdine) {
        Ordine ordine = prendiOrdinePerId(id);

        ordine.setDettagli(nuovoOrdine.getDettagli()
                .stream()

                .map(d -> {
                    Prodotto prodotto = prodottoRepository.findById(d.getProdottoId())
                            .orElseThrow(() -> new RuntimeException("Prodotto non trovato con id: " + d.getProdottoId()));
                    DettaglioOrdine dettaglio = new DettaglioOrdine();
                    dettaglio.setOrdine(ordine);
                    dettaglio.setProdotto(prodotto);
                    dettaglio.setQuantita(d.getQuantita());
                    dettaglio.setPrezzoUnitario(prodotto.getPrezzo() * d.getQuantita());
                    return dettaglio;
                })
                .collect(Collectors.toSet()));
        Double totale = ordine.getDettagli().stream()
                .mapToDouble(DettaglioOrdine::getPrezzoUnitario)
                .sum();
        ordine.setTotale(totale);

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
