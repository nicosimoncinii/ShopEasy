package it.shopeasy.dto;

import it.shopeasy.enums.LanguagePreferences;
import it.shopeasy.enums.RuoloUtente;
import it.shopeasy.enums.StatoUtente;
import it.shopeasy.enums.ThemePreferences;
import it.shopeasy.model.Ruolo;
import jakarta.validation.constraints.*;

public class UtenteUpdateRequestDTO {
    @NotNull
    @Size(min = 3, message = "Il nome deve avere almeno 3 caratteri")
    private String nome;
    @NotNull
    @Size(min = 3, message = "Il nome deve avere almeno 3 caratteri")
    private String cognome;
    @NotNull
    @Email(message = "Email non valida")
    private String email;
    @NotNull
    @NotBlank
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{6,}$",
            message = "La password deve contenere almeno 8 caratteri, una maiuscola, una minuscola, un numero e un carattere speciale"
    )
    private String password;

    /*@Size(min = 11, max = 11, message = "Inserisci un numero di telefono valdio")
    private String telefono;
    */

    @NotNull(message = "L'indirizzo è necessario")
    @NotBlank(message = "L'indirizzo non può essere vuoto")
    private String indirizzo;
    private LanguagePreferences languagePreference;
    private ThemePreferences themePreference;

    private RuoloUtente ruolo;
    private StatoUtente stato;

    public UtenteUpdateRequestDTO() {
    }

    public UtenteUpdateRequestDTO(String nome, String cognome, String email, String password, /*String telefono,*/ String indirizzo, LanguagePreferences languagePreference, ThemePreferences themePreference, RuoloUtente ruolo, StatoUtente stato) {
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

   /* public String getTelefono() {
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