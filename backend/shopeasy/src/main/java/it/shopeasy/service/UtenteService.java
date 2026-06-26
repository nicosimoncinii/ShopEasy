package it.shopeasy.service;

import it.shopeasy.dto.UtenteResponseDTO;
import it.shopeasy.model.Utente;
import it.shopeasy.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UtenteResponseDTO> prendiTuttiUtenti() {
        return utenteRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public UtenteResponseDTO prendiUtenteResponsePerId(Long id){
        return toResponse(prendiUtentePerId(id));
    }

    private Utente prendiUtentePerId(Long id) {
        return utenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utente non trovato con id: " + id));
    }

    public void cancellaUtente(Long id) {
        if (!utenteRepository.existsById(id)) {
            throw new RuntimeException("Utente non trovato con id: " + id);
        }
        utenteRepository.deleteById(id);
    }

    public UtenteResponseDTO salvaUtente(Utente utente) {
        if (utenteRepository.existsByEmail(utente.getEmail())) {
            throw new RuntimeException("Email già registrata: " + utente.getEmail());
        }
        if (utente.getPassword() != null && !utente.getPassword().isEmpty()) {
            utente.setPassword(passwordEncoder.encode(utente.getPassword()));
        }
        return toResponse(utenteRepository.save(utente));
    }

    public UtenteResponseDTO aggiornaUtente(Long id, Utente nuovoUtente) {
        Utente utente = prendiUtentePerId(id);

        if (nuovoUtente.getNome() != null) {
            utente.setNome(nuovoUtente.getNome());
        }
        if (nuovoUtente.getCognome() != null) {
            utente.setCognome(nuovoUtente.getCognome());
        }
        if (nuovoUtente.getEmail() != null) {
            if (!nuovoUtente.getEmail().equals(utente.getEmail()) && 
                utenteRepository.existsByEmail(nuovoUtente.getEmail())) {
                throw new RuntimeException("Email già registrata: " + nuovoUtente.getEmail());
            }
            utente.setEmail(nuovoUtente.getEmail());
        }
        if (nuovoUtente.getPassword() != null && !nuovoUtente.getPassword().isEmpty()) {
            utente.setPassword(passwordEncoder.encode(nuovoUtente.getPassword()));
        }
        /*
        if (nuovoUtente.getTelefono() != null) {

            utente.setTelefono(nuovoUtente.getTelefono());
        }

         */
        if (nuovoUtente.getIndirizzo() != null) {
            utente.setIndirizzo(nuovoUtente.getIndirizzo());
        }
        if (nuovoUtente.getLanguagePreference() != null) {
            utente.setLanguagePreference(nuovoUtente.getLanguagePreference());
        }
        if (nuovoUtente.getThemePreference() != null) {
            utente.setThemePreference(nuovoUtente.getThemePreference());
        }
        if (nuovoUtente.getRuolo() != null) {
            utente.setRuolo(nuovoUtente.getRuolo());
        }
        if (nuovoUtente.getStato() != null) {
            utente.setStato(nuovoUtente.getStato());
        }

        return toResponse(utenteRepository.save(utente));
    }


    private UtenteResponseDTO toResponse(Utente utente){
        return new UtenteResponseDTO(
                utente.getId(),
                utente.getNome(),
                utente.getCognome(),
                utente.getEmail(),
                utente.getIndirizzo(),
                utente.getLanguagePreference(),
                utente.getThemePreference(),
                utente.getRuolo().getNome(),
                utente.getStato()
        );
    }

}