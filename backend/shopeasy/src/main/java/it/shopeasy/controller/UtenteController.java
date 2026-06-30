package it.shopeasy.controller;

import it.shopeasy.dto.UtenteResponseDTO;
import it.shopeasy.dto.UtenteUpdateRequestDTO;

import it.shopeasy.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class UtenteController {

    @Autowired
    private UtenteService utenteService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<UtenteResponseDTO>> prendiTuttiUtente() {
        return ResponseEntity.ok(utenteService.prendiTuttiUtenti());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UtenteResponseDTO> prendiUtentePerId(@PathVariable Long id) {
        return ResponseEntity.ok(utenteService.prendiUtenteResponsePerId(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UtenteResponseDTO> salvaUtente(@RequestBody UtenteUpdateRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(utenteService.salvaUtente(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UtenteResponseDTO> aggiornaUtente(@PathVariable Long id,
                                                            @RequestBody UtenteUpdateRequestDTO request) {
        return ResponseEntity.ok(utenteService.aggiornaUtente(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> cancellaUtente(@PathVariable Long id) {
        utenteService.cancellaUtente(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENTE')")
    public ResponseEntity<UtenteResponseDTO> prendiProfiloCorrente(Principal principal) {
        return ResponseEntity.ok(utenteService.prendiUtenteResponsePerEmail(principal.getName()));
    }

    @PutMapping("/me")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENTE')")
    public ResponseEntity<UtenteResponseDTO> aggiornaProfiloCorrente(@RequestBody UtenteUpdateRequestDTO request,
                                                                     Principal principal) {
        return ResponseEntity.ok(utenteService.aggiornaUtentePerEmail(principal.getName(), request));
    }







    @DeleteMapping("/me")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENTE')")
    public ResponseEntity<Void> cancellaProfiloCorrente(Principal principal) {
        utenteService.cancellaUtentePerEmail(principal.getName());
        return ResponseEntity.noContent().build();
    }
}
