package it.shopeasy.model;

import it.shopeasy.enums.LanguagePreferences;
import it.shopeasy.enums.StatoUtente;
import it.shopeasy.enums.ThemePreferences;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "utente")
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cognome;

    @Column(nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

   // private String telefono;

    private String indirizzo;

    @Enumerated(EnumType.STRING)
    @Column(name = "language_preference", nullable = false)
    private LanguagePreferences languagePreference;

    @Enumerated(EnumType.STRING)
    @Column(name = "theme_preference", nullable = false)
    private ThemePreferences themePreference;

    @ManyToOne
    @JoinColumn(name = "ruolo")
    private Ruolo ruolo;

    @Enumerated(EnumType.STRING)
    @Column(name = "stato")
    private StatoUtente stato;

    @OneToOne(mappedBy = "utente", cascade = CascadeType.ALL)
    @JsonIgnore
    private Wishlist wishlist;

    public Utente() {
    }

    public Utente(String nome, String cognome, String email, String password,
                 /* String telefono,*/ String indirizzo,
                  LanguagePreferences languagePreference, ThemePreferences themePreference,
                  Ruolo ruolo, StatoUtente stato) {

        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
      // this.telefono = telefono;
        this.indirizzo = indirizzo;
        this.languagePreference = languagePreference;
        this.themePreference = themePreference;
        this.ruolo = ruolo;
        this.stato = stato;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
/*
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


 */
    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public LanguagePreferences getLanguagePreference() {
        return languagePreference;
    }

    public void setLanguagePreference(LanguagePreferences languagePreference) {
        this.languagePreference = languagePreference;
    }

    public ThemePreferences getThemePreference() {
        return themePreference;
    }

    public void setThemePreference(ThemePreferences themePreference) {
        this.themePreference = themePreference;
    }

    public Ruolo getRuolo() {
        return ruolo;
    }

    public void setRuolo(Ruolo ruolo) {
        this.ruolo = ruolo;
    }

    public StatoUtente getStato() {
        return stato;
    }

    public void setStato(StatoUtente stato) {
        this.stato = stato;
    }

    public Wishlist getWishlist() {
        return wishlist;
    }

    public void setWishlist(Wishlist wishlist) {
        this.wishlist = wishlist;
    }
}