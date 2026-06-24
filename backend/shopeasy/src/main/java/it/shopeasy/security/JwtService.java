package it.shopeasy.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import it.shopeasy.model.Utente;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;


@Service
public class JwtService {

    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long durata = 1000 * 60 * 60;


    public String generaToken(Utente utente) {
        return Jwts.builder()
                .setSubject(utente.getEmail());
    }

}
