package it.shopeasy.service;


import it.shopeasy.dto.UtenteResponseDTO;
import it.shopeasy.dto.UtenteUpdateRequestDTO;
import it.shopeasy.enums.LanguagePreferences;
import it.shopeasy.enums.RuoloUtente;
import it.shopeasy.enums.StatoUtente;
import it.shopeasy.enums.ThemePreferences;
import it.shopeasy.model.Ruolo;
import it.shopeasy.model.Statistiche;
import it.shopeasy.model.Utente;
import it.shopeasy.model.Wishlist;
import it.shopeasy.repository.RuoloRepository;
import it.shopeasy.repository.StatisticheRepository;
import it.shopeasy.repository.UtenteRepository;
import it.shopeasy.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtenteService {
    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private RuoloRepository ruoloRepository;
    @Autowired
    private WishlistRepository wishlistRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private StatisticheRepository statisticheRepository;

    public List<UtenteResponseDTO> prendiTuttiUtenti() {
        return utenteRepository.findAll(Sort.by("id").ascending())
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public UtenteResponseDTO prendiUtenteResponsePerId(Long id) {
        return toResponse(prendiUtentePerId(id));
    }

    public Utente prendiUtentePerId(Long id) {
        return utenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utente non trovato con id: " + id));
    }



    public void cancellaUtente(Long id) {
        if (!utenteRepository.existsById(id)) {
            throw new RuntimeException("Utente non trovato con id: " + id);
        }
        utenteRepository.deleteById(id);
    }

    private UtenteResponseDTO salvaUtente(UtenteUpdateRequestDTO request) {
        if (utenteRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email già registrata: " + request.getEmail());
        }
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new RuntimeException("La password è obbligatoria");
        }

        Utente utente = new Utente();
        utente.setNome(request.getNome());
        utente.setCognome(request.getCognome());
        utente.setEmail(request.getEmail());
        utente.setTelefono(request.getTelefono());
        utente.setPassword(passwordEncoder.encode(request.getPassword()));
        utente.setIndirizzo(request.getIndirizzo());
        utente.setLanguagePreference(request.getLanguagePreference() != null ? request.getLanguagePreference() : LanguagePreferences.IT);
        utente.setThemePreference(request.getThemePreference() != null ? request.getThemePreference() : ThemePreferences.LIGHT);
        utente.setStato(request.getStato() != null ? request.getStato() : StatoUtente.ATTIVO);

        RuoloUtente ruoloEnum = request.getRuolo() != null ? request.getRuolo() : RuoloUtente.CLIENTE;
        Ruolo ruolo = ruoloRepository.findByNome(ruoloEnum)
                .orElseThrow(() -> new RuntimeException("Ruolo non trovato"));
        utente.setRuolo(ruolo);

        Wishlist wishlist = new Wishlist(utente);
        wishlistRepository.save(wishlist);

        return toResponse(utenteRepository.save(utente));
    }

    public UtenteResponseDTO aggiornaUtente(Long id, UtenteUpdateRequestDTO request) {
        Utente utente = prendiUtentePerId(id);

        if (request.getNome() != null) utente.setNome(request.getNome());
        if (request.getCognome() != null) utente.setCognome(request.getCognome());
        if (request.getEmail() != null) {
            if (!request.getEmail().equals(utente.getEmail()) &&
                    utenteRepository.existsByEmail(request.getEmail())) {
                throw new RuntimeException("Email già registrata: " + request.getEmail());
            }
            utente.setEmail(request.getEmail());
        }
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            utente.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        utente.setTelefono(request.getTelefono());
        utente.setIndirizzo(request.getIndirizzo());
        if (request.getLanguagePreference() != null) utente.setLanguagePreference(request.getLanguagePreference());
        if (request.getThemePreference() != null) utente.setThemePreference(request.getThemePreference());
        if (request.getStato() != null) utente.setStato(request.getStato());
        if (request.getRuolo() != null) {
            Ruolo ruolo = ruoloRepository.findByNome(request.getRuolo())
                    .orElseThrow(() -> new RuntimeException("Ruolo non trovato"));
            utente.setRuolo(ruolo);
        }

        return toResponse(utenteRepository.save(utente));
    }

    public UtenteResponseDTO prendiUtenteResponsePerEmail(String email) {
        return toResponse(utenteRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utente non trovato")));
    }

    public UtenteResponseDTO aggiornaUtentePerEmail(String email, UtenteUpdateRequestDTO request) {
        Utente utente = utenteRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        request.setRuolo(null);
        request.setStato(null);
        return aggiornaUtente(utente.getId(), request);
    }

    public void cancellaUtentePerEmail(String email) {
        Utente utente = utenteRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        cancellaUtente(utente.getId());
    }

    public UtenteResponseDTO creaUtente(UtenteUpdateRequestDTO utente){

        Statistiche statistiche = statisticheRepository.findById(StatisticheRepository.ID)
                .orElseThrow(() -> new RuntimeException("Errore nella fetch dei dati nelle statistiche, riferirsi ad un amministratore"));

        statistiche.setUtentiRegistrati(statistiche.getUtentiRegistrati() +1);

        statisticheRepository.save(statistiche);

        return salvaUtente(utente);
    }
    public UtenteResponseDTO disattivaUtente(Long id){
        Utente utente = utenteRepository.findById(id).orElseThrow(() -> new RuntimeException("Utente non trovato"));

        utente.setStato(StatoUtente.DISABILITATO);
        return toResponse(utenteRepository.save(utente));
    }
    public UtenteResponseDTO attivaUtente(Long id){
        Utente utente = utenteRepository.findById(id).orElseThrow(() -> new RuntimeException("Utente non trovato"));

        utente.setStato(StatoUtente.ATTIVO);
        return toResponse(utenteRepository.save(utente));
    }

    private UtenteResponseDTO toResponse(Utente utente) {
        return new UtenteResponseDTO(
                utente.getId(),
                utente.getNome(),
                utente.getCognome(),
                utente.getEmail(),
                utente.getTelefono(),
                utente.getIndirizzo(),
                utente.getLanguagePreference(),
                utente.getThemePreference(),
                utente.getRuolo().getNome(),
                utente.getStato()
                
        );
    }
}