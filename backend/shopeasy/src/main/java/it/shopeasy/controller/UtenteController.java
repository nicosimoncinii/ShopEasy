package it.shopeasy.controller;

import it.shopeasy.model.Utente;
import it.shopeasy.service.UtenteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UtenteController {

    private final UtenteService utenteService;

    public UtenteController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @GetMapping
    public ResponseEntity<List<Utente>> prendiTuttiUtente() {
        return ResponseEntity.ok(utenteService.prendiTuttiUtenti());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Utente> prendiUtentePerId(@PathVariable Long id) {
        return ResponseEntity.ok(utenteService.prendiUtentePerId(id));
    }

    @PostMapping
    public ResponseEntity<Utente> salvaUtente(@RequestBody Utente Utente){
        Utente creato = utenteService.salvaUtente(Utente);
        return ResponseEntity.status(HttpStatus.CREATED).body(creato);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Utente> aggiornaUtente(@RequestBody Utente Utente, @PathVariable Long id)
    {
        Utente aggiornato = utenteService.aggiornaUtente(id,Utente);
        return ResponseEntity.ok(aggiornato);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Utente> cancellaUtente(@PathVariable Long id){
        utenteService.cancellaUtente(id);
        return ResponseEntity.noContent().build();
    }


}
