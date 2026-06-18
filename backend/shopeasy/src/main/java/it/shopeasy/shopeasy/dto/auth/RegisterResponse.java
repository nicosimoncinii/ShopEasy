package it.shopeasy.shopeasy.dto.auth;

public class RegisterResponse {

    private String messaggio;
    private String email;
    private String nome;

    public RegisterResponse() {
    }

    public RegisterResponse(String messaggio, String email, String nome) {
        this.messaggio = messaggio;
        this.email = email;
        this.nome = nome;
    }

    public String getMessaggio() {
        return messaggio;
    }

    public void setMessaggio(String messaggio) {
        this.messaggio = messaggio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}