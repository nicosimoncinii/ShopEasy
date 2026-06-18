package it.shopeasy.shopeasy.service;

import it.shopeasy.shopeasy.controller.ProdottoController;
import it.shopeasy.shopeasy.model.Ordine;
import it.shopeasy.shopeasy.model.Prodotto;
import it.shopeasy.shopeasy.repository.ProdottoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdottoService {
    
    @Autowired
    private ProdottoRepository prodottoRepository;
    
    public List<Prodotto> prendiTuttiProdotti() {
        return prodottoRepository.findAll();
    }

    public Prodotto prendiProdottoPerId(Long id) {
        return prodottoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prodotto non trovato con id: " + id));
    }

    public void cancellaProdotto(Long id)
    {
        prodottoRepository.deleteById(id);
    }
}
