package it.shopeasy.service;

import it.shopeasy.model.Prodotto;
import it.shopeasy.repository.ProdottoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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


    public Prodotto salvaProdotto(Prodotto prodotto) {

        return prodottoRepository.save(prodotto);
    }

    public Prodotto aggiornaProdotto(Long id, Prodotto nuovoProdotto) {
        Prodotto prodotto = prendiProdottoPerId(id);
        nuovoProdotto.setId(prodotto.getId());
        prodotto.setNome(nuovoProdotto.getNome());
        prodotto.setPrezzo(nuovoProdotto.getPrezzo());
        prodotto.setDescrizione(nuovoProdotto.getDescrizione());
        prodotto.setCategoria(nuovoProdotto.getCategoria());
        prodotto.setImmagine(nuovoProdotto.getImmagine());

        return salvaProdotto(nuovoProdotto);
    }




}
