package it.shopeasy.controller;

import it.shopeasy.dto.ProdottoRequestDTO;
import it.shopeasy.dto.ProdottoResponseDTO;
import it.shopeasy.service.ProdottoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProdottoController {

    private final ProdottoService prodottoService;

    public ProdottoController(ProdottoService prodottoService){
        this.prodottoService = prodottoService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENTE')")
    public ResponseEntity<List<ProdottoResponseDTO>> prendiTuttiProdotti() {
        return ResponseEntity.ok(prodottoService.prendiTuttiProdotti());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENTE')")
    public ResponseEntity<ProdottoResponseDTO> prendiProdottoPerId(@PathVariable Long id){
        return ResponseEntity.ok(prodottoService.prendiProdottoResponsePerId(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ProdottoResponseDTO> creaProdotto(@RequestBody ProdottoRequestDTO prodotto) {
        ProdottoResponseDTO creato = prodottoService.salvaProdotto(prodotto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creato);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ProdottoResponseDTO> aggiornaProdotto(@RequestBody ProdottoRequestDTO prodotto, @PathVariable Long id) {
        ProdottoResponseDTO aggiornato = prodottoService.aggiornaProdotto(id, prodotto);
        return ResponseEntity.ok(aggiornato);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> cancellaProdotto(@PathVariable Long id){
        prodottoService.cancellaProdotto(id);
        return ResponseEntity.noContent().build();
    }
}