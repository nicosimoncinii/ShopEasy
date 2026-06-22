    package it.shopeasy.model;


    import jakarta.persistence.*;
    import java.util.List;
    import java.util.ArrayList;


    @Entity
    @Table(name = "prodotto")
    public class Prodotto {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(updatable = false)
        private Long id;

        @Column(nullable = false)
        private String nome;

        private String descrizione;

        @Column(nullable = false)
        private Double prezzo;

        @Column(nullable = false)
        private Integer quantita;

        private String immagine;

        @ManyToOne
        @JoinColumn(name = "categoria_id")
        private Categoria categoria;

        @ManyToMany(mappedBy = "prodotti")
        private List<Wishlist> wishlist = new ArrayList<>();

        public Prodotto() {
        }

        public Prodotto(String nome, String descrizione, Double prezzo,
                        Integer quantita, String immagine, Categoria categoria) {
            this.nome = nome;
            this.descrizione = descrizione;
            this.prezzo = prezzo;
            this.quantita = quantita;
            this.immagine = immagine;
            this.categoria = categoria;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getDescrizione() {
            return descrizione;
        }

        public void setDescrizione(String descrizione) {
            this.descrizione = descrizione;
        }

        public Double getPrezzo() {
            return prezzo;
        }

        public void setPrezzo(Double prezzo) {
            this.prezzo = prezzo;
        }

        public Integer getQuantita() {
            return quantita;
        }

        public void setQuantita(Integer quantita) {
            this.quantita = quantita;
        }

        public String getImmagine() {
            return immagine;
        }

        public void setImmagine(String immagine) {
            this.immagine = immagine;
        }

        public Categoria getCategoria() {
            return categoria;
        }

        public void setCategoria(Categoria categoria) {
            this.categoria = categoria;
        }

        public List<Wishlist> getWishlist() {
            return wishlist;
        }
        
        public void setWishlist(List<Wishlist> wishlist) {
            this.wishlist = wishlist;
        }
    }