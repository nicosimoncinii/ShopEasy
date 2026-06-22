package it.shopeasy.service;

import it.shopeasy.dto.auth.LoginRequest;
import it.shopeasy.dto.auth.LoginResponse;
import it.shopeasy.dto.auth.RegisterRequest;
import it.shopeasy.dto.auth.RegisterResponse;
import it.shopeasy.model.Ruolo;
import it.shopeasy.model.Utente;
import it.shopeasy.repository.RuoloRepository;
import it.shopeasy.repository.UtenteRepository;
import it.shopeasy.security.JwtService;
import it.shopeasy.enums.RuoloUtente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import it.shopeasy.enums.StatoUtente;

@Service
public class AuthService {
    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private RuoloRepository ruoloRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public RegisterResponse registra(RegisterRequest request) {
        if (utenteRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email già registrata");
        }

        if (!request.getPassword().equals(request.getConfermaPassword())) {
            throw new RuntimeException("Le password non coincidono");
        }

        Ruolo ruolo = ruoloRepository.findByNome(RuoloUtente.UTENTE)
        .orElseThrow(() -> new RuntimeException("Ruolo UTENTE non trovato nel database"));

        Utente utente = new Utente();
        utente.setNome(request.getNome());
        utente.setCognome(request.getCognome());
        utente.setEmail(request.getEmail());
        utente.setPassword(passwordEncoder.encode(request.getPassword()));
        utente.setRuolo(ruolo);
        utente.setStato(StatoUtente.ATTIVO);
        utente.setLanguagePreference("it");
        utente.setThemePreference("light");

        utenteRepository.save(utente);

        return new RegisterResponse(
            "Registrazione completata con successo",
            utente.getEmail(),
            utente.getNome()
        );
    }

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
            )
        );

        Utente utente = utenteRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        String token = jwtService.generaToken(utente);

        return new LoginResponse(
            token,
            utente.getEmail(),
            utente.getRuolo().getNome().name()
        );
    }

}

