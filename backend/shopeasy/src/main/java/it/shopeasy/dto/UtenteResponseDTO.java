package it.shopeasy.dto;

public class UtenteResponseDTO {

    private Long id;
    private String nome;
    private String cognome;
    private String email;
    private String telefono;
    private String indirizzo;
    private String languagePreference;
    private String themePreference;
    private String ruolo;
    private String stato;

    public UtenteResponseDTO() {
    }

    public UtenteResponseDTO(Long id, String nome, String cognome, String email,
                             String telefono, String indirizzo,
                             String languagePreference, String themePreference,
                             String ruolo, String stato) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.telefono = telefono;
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