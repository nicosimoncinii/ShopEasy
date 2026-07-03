package it.shopeasy.dto.auth;

import it.shopeasy.enums.RuoloUtente;
import it.shopeasy.enums.StatoUtente;

public class LoginResponse {

    private String token;
    private String email;
    private String ruolo;
    private StatoUtente statoUtente;

    public LoginResponse() {
    }



    public LoginResponse(String token, String email,String ruolo,StatoUtente statoUtente) {
        this.token = token;
        this.statoUtente = statoUtente;
        this.ruolo = ruolo;
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public StatoUtente getStatoUtente() {
        return statoUtente;
    }

    public void setStatoUtente(StatoUtente statoUtente) {
        this.statoUtente = statoUtente;
    }
}