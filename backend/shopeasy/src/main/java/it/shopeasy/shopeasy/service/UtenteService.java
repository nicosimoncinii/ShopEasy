package it.shopeasy.shopeasy.service;

import it.shopeasy.shopeasy.model.Utente;
import it.shopeasy.shopeasy.repository.UtenteRepository;
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

    public void cancellaUtente(Long id)
    {
        utenteRepository.deleteById(id);
    }
}
