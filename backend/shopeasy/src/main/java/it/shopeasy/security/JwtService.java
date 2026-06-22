package it.shopeasy.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import it.shopeasy.model.Utente;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
    private final SecretKey secretKey = Keys.hmacShaKeyFor(
        "chiave-32".getBytes()
    );
    private final long durata = 3000 * 60 * 60;

    private Claims estraiClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String generaToken(Utente utente) {
        return Jwts.builder()
                .subject(utente.getEmail())
                .claim("ruolo", utente.getRuolo().getNome())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + durata))
                .signWith(secretKey)
                .compact();
    }

    public String estraiEmail(String token) {
        return estraiClaims(token).getSubject();
    }

    private boolean isTokenScaduto(String token) {
        return estraiClaims(token).getExpiration().before(new Date());
    }

    public boolean isTokenValido(String token, String email) {
        return estraiEmail(token).equals(email) && !isTokenScaduto(token);
    }

}

