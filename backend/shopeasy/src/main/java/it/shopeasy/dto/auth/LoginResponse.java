package it.shopeasy.dto.auth;

public class LoginResponse {

    private String token;
    private String email;
    private String ruolo;

    public LoginResponse() {
    }

    public LoginResponse(String token, String email, String ruolo) {
        this.token = token;
        this.email = email;
        this.ruolo = ruolo;
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
}