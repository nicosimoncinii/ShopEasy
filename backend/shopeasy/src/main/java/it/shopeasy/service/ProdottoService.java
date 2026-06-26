package it.shopeasy.service;

import it.shopeasy.dto.ProdottoRequestDTO;
import it.shopeasy.dto.ProdottoResponseDTO;
import it.shopeasy.model.Categoria;
import it.shopeasy.model.Prodotto;
import it.shopeasy.repository.CategoriaRepository;
import it.shopeasy.repository.ProdottoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdottoService {
    
    @Autowired
    private ProdottoRepository prodottoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<ProdottoResponseDTO> prendiTuttiProdotti() {
        return prodottoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }
    public ProdottoResponseDTO prendiProdottoResponsePerId(Long id){
        return toResponse(prendiProdottoPerId(id));
    }

    private Prodotto prendiProdottoPerId(Long id) {
        return prodottoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prodotto non trovato con id: " + id));
    }

    public void cancellaProdotto(Long id)
    {
        prodottoRepository.deleteById(id);
    }


    public ProdottoResponseDTO salvaProdotto(ProdottoRequestDTO request) {

        Prodotto prodotto = new Prodotto();

        prodotto.setNome(request.getNome());
        prodotto.setDescrizione(request.getDescrizione());
        prodotto.setPrezzo(request.getPrezzo());
        prodotto.setImmagine(request.getImmagine());
        prodotto.setCategoria(prendiCategoriaPerId(request.getCategoriaId()));
        prodotto.setQuantita(request.getQuantita());

        return toResponse(prodottoRepository.save(prodotto));
    }

    private Categoria prendiCategoriaPerId(Long id){
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria non trovata con id " + id));
    }

    public ProdottoResponseDTO aggiornaProdotto(Long id, ProdottoRequestDTO nuovoProdotto) {

        Prodotto prodotto = prendiProdottoPerId(id);

        prodotto.setNome(nuovoProdotto.getNome());
        prodotto.setDescrizione(nuovoProdotto.getDescrizione());
        prodotto.setPrezzo(nuovoProdotto.getPrezzo());
        prodotto.setImmagine(nuovoProdotto.getImmagine());
        prodotto.setCategoria(prendiCategoriaPerId(nuovoProdotto.getCategoriaId()));
        prodotto.setQuantita(nuovoProdotto.getQuantita());

        return toResponse(prodottoRepository.save(prodotto));
    }


    private ProdottoResponseDTO toResponse(Prodotto prodotto)
    {
        return new ProdottoResponseDTO(
                prodotto.getId(),
                prodotto.getNome(),
                prodotto.getDescrizione(),
                prodotto.getPrezzo(),
                prodotto.getQuantita(),
                prodotto.getImmagine(),
                prodotto.getCategoria().getId(),
                prodotto.getCategoria().getNome()
        );
    }


}
