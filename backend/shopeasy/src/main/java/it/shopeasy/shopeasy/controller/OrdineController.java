package it.shopeasy.shopeasy.controller;

import it.shopeasy.shopeasy.model.Ordine;
import it.shopeasy.shopeasy.service.OrdineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
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
    public ResponseEntity<List<Ordine>> prendiTuttiOrdini() {
        return ResponseEntity.ok(service.prendiTuttiOrdini());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ordine> prendiOrdinePerId(@PathVariable Long id) {
        return ResponseEntity.ok(service.prendiOrdinePerId(id));
    }

    @PostMapping
    public ResponseEntity<Ordine> salvaOrdine(@RequestBody Ordine ordine) {
        Ordine creato = service.salvaOrdine(ordine);
        return ResponseEntity.status(HttpStatus.CREATED).body(creato);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Ordine> aggiornaOrdine(@RequestBody Ordine ordine, @PathVariable Long id) {
        Ordine aggiornato = service.aggiornaOrdine(id,ordine);
        return ResponseEntity.ok(aggiornato);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancellaOrdine(@PathVariable Long id) {
        service.cancellaOrdine(id);
        return ResponseEntity.noContent().build();
    }


}
