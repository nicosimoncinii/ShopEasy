package it.shopeasy.dto;

import it.shopeasy.enums.LanguagePreferences;
import it.shopeasy.enums.RuoloUtente;
import it.shopeasy.enums.StatoUtente;
import it.shopeasy.enums.ThemePreferences;
import org.springframework.ui.context.Theme;

public class UtenteResponseDTO {

    private Long id;
    private String nome;
    private String cognome;
    private String email;
    //private String telefono;
    private String indirizzo;
    private LanguagePreferences languagePreference;
    private ThemePreferences themePreference;
    private RuoloUtente ruolo;
    private StatoUtente stato;

    public UtenteResponseDTO() {
    }

    public UtenteResponseDTO(Long id, String nome, String cognome, String email,
                             /*String telefono,*/ String indirizzo,
                             LanguagePreferences languagePreference, ThemePreferences themePreference,
                             RuoloUtente ruolo, StatoUtente stato) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        //this.telefono = telefono;
        this.indirizzo = indirizzo;
        this.languagePreference = languagePreference;
        this.themePreference = themePreference;
        this.ruolo = ruolo;
        this.stato = stato;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public RuoloUtente getRuolo() {
        return ruolo;
    }

    public void setRuolo(RuoloUtente ruolo) {
        this.ruolo = ruolo;
    }

    public StatoUtente getStato() {
        return stato;
    }

    public void setStato(StatoUtente stato) {
        this.stato = stato;
    }
}