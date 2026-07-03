package it.shopeasy.dto.auth;

public class ForgotPasswordResponse {
    private String token;

    public ForgotPasswordResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
