package it.shopeasy.controller;

import it.shopeasy.dto.OrdineRequestDTO;
import it.shopeasy.dto.OrdineResponseDTO;
import it.shopeasy.model.Ordine;
import it.shopeasy.service.OrdineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<List<OrdineResponseDTO>> prendiTuttiOrdini() {
        return ResponseEntity.ok(service.prendiTuttiOrdini());
    }

    @GetMapping("/my-orders")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENTE')")
    public ResponseEntity<List<OrdineResponseDTO>> prendiOrdiniPerUtente(Principal principal) {
        return ResponseEntity.ok(service.prendiOrdiniPerUtente(principal.getName()));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<OrdineResponseDTO> salvaOrdine(@RequestBody OrdineRequestDTO ordine) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvaOrdine(ordine));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<OrdineResponseDTO> aggiornaOrdine(@RequestBody OrdineRequestDTO ordine, @PathVariable Long id) {
        OrdineResponseDTO aggiornato = service.aggiornaOrdine(id, ordine);
        return ResponseEntity.ok(aggiornato);
    }
}