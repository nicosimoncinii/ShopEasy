package it.shopeasy.model;

import jakarta.persistence.*;
import it.shopeasy.enums.RuoloUtente;

@Entity
@Table(name = "ruolo")
public class Ruolo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private RuoloUtente nome;

    public Ruolo() {
    }

    public Ruolo(RuoloUtente nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RuoloUtente getNome() {
        return nome;
    }

    public void setNome(RuoloUtente nome) {
        this.nome = nome;
    }
}