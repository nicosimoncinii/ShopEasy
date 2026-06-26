package it.shopeasy.controller;

import it.shopeasy.dto.OrdineRequestDTO;
import it.shopeasy.dto.OrdineResponseDTO;
import it.shopeasy.model.Ordine;
import it.shopeasy.service.OrdineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordini")
public class OrdineController {

    private final OrdineService service;

    @Autowired
    public OrdineController(OrdineService ordineService) {
        this.service = ordineService;
    }

    @GetMapping
    public ResponseEntity<List<OrdineResponseDTO>> prendiTuttiOrdini() {
        return ResponseEntity.ok(service.prendiTuttiOrdini());
    }

    @GetMapping("/my-orders/{utente_id}")
    public ResponseEntity<List<OrdineResponseDTO>> prendiOrdiniPerUtente(@PathVariable Long utente_id) {
        return ResponseEntity.ok(service.prendiOrdiniPerUtente(utente_id));
    }

    @PostMapping
    public ResponseEntity<OrdineResponseDTO> salvaOrdine(@RequestBody OrdineRequestDTO ordine) {
        OrdineResponseDTO creato = service.salvaOrdine(ordine);
        return ResponseEntity.status(HttpStatus.CREATED).body(creato);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdineResponseDTO> aggiornaOrdine(@RequestBody OrdineRequestDTO ordine, @PathVariable Long id) {
        OrdineResponseDTO aggiornato = service.aggiornaOrdine(id, ordine);
        return ResponseEntity.ok(aggiornato);
    }
}