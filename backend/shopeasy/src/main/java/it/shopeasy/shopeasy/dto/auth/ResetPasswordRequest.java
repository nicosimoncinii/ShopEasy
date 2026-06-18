package it.shopeasy.shopeasy.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ResetPasswordRequest {

    @NotBlank(message = "Il token di reset è obbligatorio")
    private String token;

    @NotBlank(message = "La nuova password è obbligatoria")
    @Size(min = 6, message = "La nuova password deve contenere almeno 6 caratteri")
    private String nuovaPassword;

    @NotBlank(message = "La conferma della password è obbligatoria")
    private String confermaPassword;

    public ResetPasswordRequest() {
    }

    public ResetPasswordRequest(String token, String nuovaPassword, String confermaPassword) {
        this.token = token;
        this.nuovaPassword = nuovaPassword;
        this.confermaPassword = confermaPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNuovaPassword() {
        return nuovaPassword;
    }

    public void setNuovaPassword(String nuovaPassword) {
        this.nuovaPassword = nuovaPassword;
    }

    public String getConfermaPassword() {
        return confermaPassword;
    }

    public void setConfermaPassword(String confermaPassword) {
        this.confermaPassword = confermaPassword;
    }
}