package it.shopeasy.dto;

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

    private String telefono;
    private String indirizzo;
    private String languagePreference;
    private String themePreference;
    private String ruolo;
    private String stato;

    public UtenteUpdateRequestDTO() {
    }

    public UtenteUpdateRequestDTO(String nome, String cognome, String email, String password, String telefono, String indirizzo, String languagePreference, String themePreference, String ruolo, String stato) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.telefono = telefono;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getLanguagePreference() {
        return languagePreference;
    }

    public void setLanguagePreference(String languagePreference) {
        this.languagePreference = languagePreference;
    }

    public String getThemePreference() {
        return themePreference;
    }

    public void setThemePreference(String themePreference) {
        this.themePreference = themePreference;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }
}