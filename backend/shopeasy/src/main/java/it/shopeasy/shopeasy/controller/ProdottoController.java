package it.shopeasy.shopeasy.controller;


import it.shopeasy.shopeasy.service.ProdottoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import it.shopeasy.shopeasy.model.Prodotto;

import java.util.List;

@RestController
@RequestMapping("/api/prodotti")
public class ProdottoController {

    private final ProdottoService prodottoService;

    public ProdottoController(ProdottoService prodottoService){
        this.prodottoService = prodottoService;
    }

    @GetMapping
    public ResponseEntity<List<Prodotto>> prendiTuttiProdotti() {
        return ResponseEntity.ok(prodottoService.prendiTuttiProdotti());
    }

    @GetMapping("/api/prodotti")
    public ResponseEntity<Prodotto> prendiProdottoPerId(@PathVariable Long id){
        return ResponseEntity.ok(prodottoService.prendiProdottoPerId(id));
    }

    @PostMapping
    public ResponseEntity<Prodotto> creaProdotto(@RequestBody Prodotto prodotto) {
        Prodotto creato = prodottoService.salvaProdotto(prodotto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creato);
    }

    @PutMapping("{id}")
    public ResponseEntity<Prodotto> aggiornaProdotto(@RequestBody Prodotto prodotto, @PathVariable Long id) {
        Prodotto aggiornato = prodottoService.aggiornaProdotto(id,prodotto);
        return ResponseEntity.ok(aggiornato);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancellaProdotto(@PathVariable Long id){
        prodottoService.cancellaProdotto(id);
        return ResponseEntity.noContent().build();
    }



}
