package it.shopeasy.service;


import it.shopeasy.model.Utente;
import it.shopeasy.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    public List<Utente> prendiTuttiUtenti(){
        return utenteRepository.findAll();
    }

    public Utente prendiUtentePerId(Long id) {
        return utenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utente non trovato con id: " + id));
    }

    public void cancellaUtente(Long id) {
        utenteRepository.deleteById(id);
    }

    public Utente salvaUtente(Utente utente) {

        return utenteRepository.save(utente);
    }

    public Utente aggiornaUtente(Long id, Utente nuovoUtente) {
        Utente utente = prendiUtentePerId(id);

        utente.setNome(nuovoUtente.getNome());
        utente.setCognome(nuovoUtente.getCognome());
        utente.setEmail(nuovoUtente.getEmail());
        utente.setIndirizzo(nuovoUtente.getIndirizzo());
        utente.setLanguagePreference(nuovoUtente.getLanguagePreference());
        utente.setThemePreference(nuovoUtente.getThemePreference());


        return salvaUtente(utente);
    }
    
}
