package it.shopeasy.controller;

import it.shopeasy.dto.OrdineResponseDTO;
import it.shopeasy.model.Ordine;
import it.shopeasy.service.OrdineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import it.shopeasy.model.Utente;
import it.shopeasy.service.UtenteService;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/ordini")
public class OrdineController {

    private final OrdineService service;
    private final UtenteService utenteService;

    @Autowired
    public OrdineController(OrdineService ordineService, UtenteService utenteService) {
        this.service = ordineService;
        this.utenteService = utenteService;
    }

    @GetMapping
    public ResponseEntity<List<OrdineResponseDTO>> prendiTuttiOrdini() {
        return ResponseEntity.ok(service.prendiTuttiOrdini());
    }

    @GetMapping("/my-orders/{utente_id}")
    public ResponseEntity<List<Ordine>> prendiOrdiniPerUtente(@PathVariable Long utente_id) {
        return ResponseEntity.ok(service.prendiOrdiniPerUtente(utente_id));
    }

    @GetMapping("/me")
    public ResponseEntity<List<Ordine>> prendiOrdiniUtenteCorrente(Principal principal) {
        Utente utente = utenteService.prendiUtentePerEmail(principal.getName());
        return ResponseEntity.ok(service.prendiOrdiniPerUtente(utente.getId()));
    }

    @PostMapping
    public ResponseEntity<Ordine> salvaOrdine(@RequestBody Ordine ordine) {
        Ordine creato = service.salvaOrdine(ordine);
        return ResponseEntity.status(HttpStatus.CREATED).body(creato);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ordine> aggiornaOrdine(@RequestBody Ordine ordine, @PathVariable Long id) {
        Ordine aggiornato = service.aggiornaOrdine(id, ordine);
        return ResponseEntity.ok(aggiornato);
    }
}