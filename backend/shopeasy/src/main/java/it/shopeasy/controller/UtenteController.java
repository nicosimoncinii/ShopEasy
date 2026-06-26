package it.shopeasy.controller;

import it.shopeasy.dto.UtenteUpdateRequestDTO;
import it.shopeasy.enums.RuoloUtente;
import it.shopeasy.enums.StatoUtente;
import it.shopeasy.model.Ruolo;
import it.shopeasy.model.Utente;
import it.shopeasy.repository.RuoloRepository;
import it.shopeasy.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UtenteController {

    private final UtenteService utenteService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RuoloRepository ruoloRepository;

    public UtenteController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Utente>> prendiTuttiUtente() {
        return ResponseEntity.ok(utenteService.prendiTuttiUtenti());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Utente> prendiUtentePerId(@PathVariable Long id) {
        return ResponseEntity.ok(utenteService.prendiUtentePerId(id));
    }

    @GetMapping("/me")
    public ResponseEntity<Utente> prendiUtenteCorrente(Principal principal) {
        Utente utente = utenteService.prendiUtentePerEmail(principal.getName());
        return ResponseEntity.ok(utente);
    }

    @PutMapping("/me")
    public ResponseEntity<Utente> aggiornaUtenteCorrente(@RequestBody UtenteUpdateRequestDTO request, Principal principal) {
        Utente utente = utenteService.prendiUtentePerEmail(principal.getName());
        
        if (request.getNome() != null) utente.setNome(request.getNome());
        if (request.getCognome() != null) utente.setCognome(request.getCognome());
        if (request.getTelefono() != null) utente.setTelefono(request.getTelefono());
        if (request.getIndirizzo() != null) utente.setIndirizzo(request.getIndirizzo());
        
        Utente aggiornato = utenteService.aggiornaUtente(utente.getId(), utente);
        return ResponseEntity.ok(aggiornato);
    }

    @PutMapping("/me/password")
    public ResponseEntity<Void> cambiaPasswordCorrente(@RequestBody UtenteUpdateRequestDTO request, Principal principal) {
        Utente utente = utenteService.prendiUtentePerEmail(principal.getName());
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            utente.setPassword(passwordEncoder.encode(request.getPassword()));
            utenteService.aggiornaUtente(utente.getId(), utente);
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> cancellaUtenteCorrente(Principal principal) {
        Utente utente = utenteService.prendiUtentePerEmail(principal.getName());
        utenteService.cancellaUtente(utente.getId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Utente> salvaUtente(@RequestBody UtenteUpdateRequestDTO request) {
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new RuntimeException("La password è obbligatoria");
        }

        Utente utente = new Utente();
        utente.setNome(request.getNome());
        utente.setCognome(request.getCognome());
        utente.setEmail(request.getEmail());
        utente.setPassword(passwordEncoder.encode(request.getPassword()));
        utente.setTelefono(request.getTelefono());
        utente.setIndirizzo(request.getIndirizzo());
        utente.setLanguagePreference(request.getLanguagePreference() != null ? request.getLanguagePreference() : "it");
        utente.setThemePreference(request.getThemePreference() != null ? request.getThemePreference() : "light");

        if (request.getRuolo() != null && request.getRuolo().equalsIgnoreCase("ADMIN")) {
            Ruolo ruolo = ruoloRepository.findByNome(RuoloUtente.ADMIN)
                    .orElseThrow(() -> new RuntimeException("Ruolo ADMIN non trovato"));
            utente.setRuolo(ruolo);
        } else {
            Ruolo ruolo = ruoloRepository.findByNome(RuoloUtente.CLIENTE)
                    .orElseThrow(() -> new RuntimeException("Ruolo CLIENTE non trovato"));
            utente.setRuolo(ruolo);
        }

        if (request.getStato() != null) {
            utente.setStato(StatoUtente.valueOf(request.getStato()));
        } else {
            utente.setStato(StatoUtente.ATTIVO);
        }

        Utente creato = utenteService.salvaUtente(utente);
        return ResponseEntity.status(HttpStatus.CREATED).body(creato);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Utente> aggiornaUtente(@RequestBody UtenteUpdateRequestDTO request, @PathVariable Long id) {
        Utente utente = utenteService.prendiUtentePerId(id);

        if (request.getNome() != null) {
            utente.setNome(request.getNome());
        }
        if (request.getCognome() != null) {
            utente.setCognome(request.getCognome());
        }
        if (request.getEmail() != null) {
            utente.setEmail(request.getEmail());
        }
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            utente.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        if (request.getTelefono() != null) {
            utente.setTelefono(request.getTelefono());
        }
        if (request.getIndirizzo() != null) {
            utente.setIndirizzo(request.getIndirizzo());
        }
        if (request.getLanguagePreference() != null) {
            utente.setLanguagePreference(request.getLanguagePreference());
        }
        if (request.getThemePreference() != null) {
            utente.setThemePreference(request.getThemePreference());
        }
        if (request.getRuolo() != null) {
            if (request.getRuolo().equalsIgnoreCase("ADMIN")) {
                Ruolo ruolo = ruoloRepository.findByNome(RuoloUtente.ADMIN)
                        .orElseThrow(() -> new RuntimeException("Ruolo ADMIN non trovato"));
                utente.setRuolo(ruolo);
            } else {
                Ruolo ruolo = ruoloRepository.findByNome(RuoloUtente.CLIENTE)
                        .orElseThrow(() -> new RuntimeException("Ruolo UTENTE non trovato"));
                utente.setRuolo(ruolo);
            }
        }
        if (request.getStato() != null) {
            utente.setStato(StatoUtente.valueOf(request.getStato()));
        }

        Utente aggiornato = utenteService.aggiornaUtente(id, utente);
        return ResponseEntity.ok(aggiornato);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> cancellaUtente(@PathVariable Long id) {
        utenteService.cancellaUtente(id);
        return ResponseEntity.noContent().build();
    }
}