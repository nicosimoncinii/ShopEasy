package it.shopeasy.service;

import it.shopeasy.dto.OrdineRequestDTO;
import it.shopeasy.enums.RuoloUtente;
import it.shopeasy.enums.StatoOrdine;
import it.shopeasy.model.*;

import it.shopeasy.dto.OrdineResponseDTO;
import it.shopeasy.repository.OrdineRepository;
import it.shopeasy.repository.ProdottoRepository;
import it.shopeasy.repository.StatisticheRepository;
import it.shopeasy.repository.UtenteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.NoPermissionException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
    @Autowired
    private StatisticheRepository statisticheRepository;
    @Autowired
    private UtenteRepository utenteRepository;

    // MODIFICA 1: Iniettiamo il servizio Email che abbiamo aggiornato nello step precedente
    @Autowired
    private EmailService emailService;

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

    public void cancellaOrdine(Long id,String email) {
        Ordine ordine = prendiOrdinePerId(id);
        if (!ordine.getUtente().getEmail().equals(email) && !(utenteService.prendiUtentePerId(ordine.getUtente().getId()).getRuolo().getNome().equals(RuoloUtente.ADMIN))) {
            throw new RuntimeException("Non sei autorizzato a cancellare questo ordine");
        }

        ordineRepository.deleteById(id);
    }

    @Transactional
    private OrdineResponseDTO salvaOrdine(OrdineRequestDTO ordineR) {
        Ordine ordine = new Ordine();
        ordine.setData(LocalDateTime.now());
        ordine.setStato(StatoOrdine.ORDINATO);
        Utente utenteCaricato = utenteService.prendiUtentePerId(ordineR.getUtenteId());
        ordine.setUtente(utenteCaricato);

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
        Statistiche statistiche = statisticheRepository.findById(StatisticheRepository.ID)
                .orElseThrow(() -> new RuntimeException("Errore nella fetch dei dati nelle statistiche, riferirsi ad un amministratore"));
        statistiche.setFatturatoTotale(statistiche.getFatturatoTotale() + ordine.getTotale());
        statisticheRepository.save(statistiche);

        ordine.getDettagli().forEach(dettaglio -> {
            Prodotto prodotto = dettaglio.getProdotto();
            if (prodotto.getQuantita() < dettaglio.getQuantita())
                throw new IllegalArgumentException("Quantità insufficiente per il prodotto: " + prodotto.getNome());
            prodotto.setQuantita(prodotto.getQuantita() - dettaglio.getQuantita());
            prodottoRepository.save(prodotto);
        });

        // Salva definitivamente l'ordine sul database
        Ordine ordineSalvato = ordineRepository.save(ordine);

        // MODIFICA 2: Generiamo dinamicamente il testo HTML del riepilogo leggendo i dati dal Database
        StringBuilder riepilogoBuilder = new StringBuilder("<ul>");
        for (DettaglioOrdine d : ordineSalvato.getDettagli()) {
            riepilogoBuilder.append("<li>")
                    .append(d.getProdotto().getNome())
                    .append(" - x")
                    .append(d.getQuantita())
                    .append(" (€ ")
                    .append(String.format("%.2f", d.getPrezzoUnitario()))
                    .append(")</li>");
        }
        riepilogoBuilder.append("</ul>");

        String riepilogoProdottiHtml = riepilogoBuilder.toString();
        String totaleFormattato = String.format("%.2f", ordineSalvato.getTotale());

        // MODIFICA 3: Chiamiamo l'invio asincrono della mail reale all'utente
        try {
            emailService.sendOrderConfirmationEmail(
                    utenteCaricato,
                    riepilogoProdottiHtml,
                    totaleFormattato
            );
        } catch (Exception e) {
            // Se l'email dovesse fallire, stampiamo l'errore ma NON blocchiamo l'acquisto dell'utente
            System.err.println("Errore durante l'invio dell'email di conferma: " + e.getMessage());
        }

        return toResponse(ordineSalvato);
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
    public OrdineResponseDTO creaOrdine(OrdineRequestDTO ordine,String email){

        Statistiche statistiche = statisticheRepository.findById(StatisticheRepository.ID)
                .orElseThrow(() -> new RuntimeException("Errore nella fetch dei dati nelle statistiche, riferirsi ad un amministratore"));

        Utente utente = utenteRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utente non esistente"));

        if(utente.getRuolo().equals(RuoloUtente.ADMIN)){
            return salvaOrdine(ordine);
        }
        else {
            if(ordine.getUtenteId().equals(utente.getId()))
                return salvaOrdine(ordine);
            else
                throw new RuntimeException("Nessun permesso per creare il seguente ordine");
        }
    }
}