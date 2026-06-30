package it.shopeasy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "statistiche")
public class Statistiche {
    @Id
    private Long id;


    @Column(name = "fatturato_totale")
    private Double fatturatoTotale;



}
