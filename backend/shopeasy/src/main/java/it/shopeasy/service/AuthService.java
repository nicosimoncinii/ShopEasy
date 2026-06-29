package it.shopeasy.service;

import it.shopeasy.dto.auth.ForgotPasswordRequest;
import it.shopeasy.dto.auth.LoginRequest;
import it.shopeasy.dto.auth.LoginResponse;
import it.shopeasy.dto.auth.RegisterRequest;
import it.shopeasy.dto.auth.RegisterResponse;
import it.shopeasy.dto.auth.ResetPasswordRequest;
import it.shopeasy.enums.LanguagePreferences;
import it.shopeasy.enums.ThemePreferences;
import it.shopeasy.model.PasswordResetToken;
import it.shopeasy.model.Ruolo;
import it.shopeasy.model.Utente;
import it.shopeasy.model.Wishlist;
import it.shopeasy.repository.PasswordResetTokenRepository;
import it.shopeasy.repository.RuoloRepository;
import it.shopeasy.repository.UtenteRepository;
import it.shopeasy.repository.WishlistRepository;
import it.shopeasy.security.JwtService;
import it.shopeasy.enums.RuoloUtente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import it.shopeasy.enums.StatoUtente;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthService {
    @Autowired
    private WishlistRepository wishlistRepository;

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

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private EmailService emailService;

    @Value("${app.reset-token-expiration:30}")
    private int tokenExpirationMinutes;

    public RegisterResponse registra(RegisterRequest request) {
        if (utenteRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email gia registrata");
        }

        if (!request.getPassword().equals(request.getConfermaPassword())) {
            throw new RuntimeException("Le password non coincidono");
        }

        Ruolo ruolo = ruoloRepository.findByNome(RuoloUtente.CLIENTE)
                .orElseThrow(() -> new RuntimeException("Ruolo CLIENTE non trovato nel database"));


        Utente utente = new Utente();
        utente.setNome(request.getNome());
        utente.setCognome(request.getCognome());
        utente.setEmail(request.getEmail());
        utente.setPassword(passwordEncoder.encode(request.getPassword()));
        utente.setRuolo(ruolo);
        utente.setStato(StatoUtente.ATTIVO);
        utente.setLanguagePreference(LanguagePreferences.IT);
        utente.setThemePreference(ThemePreferences.LIGHT);

        Utente utenteSalvato =utenteRepository.save(utente);

        Wishlist wishlist = new Wishlist();
        wishlist.setUtente(utenteSalvato);
        wishlistRepository.save(wishlist);

        emailService.sendWelcomeEmail(utente);

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

    @Transactional
    public void forgotPassword(ForgotPasswordRequest request) {
        Utente utente = utenteRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Email non trovata"));

        tokenRepository.deleteByUtente(utente);

        String tokenValue = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken(
                tokenValue,
                utente,
                LocalDateTime.now().plusMinutes(tokenExpirationMinutes)
        );
        tokenRepository.save(resetToken);

        emailService.sendPasswordResetEmail(utente, tokenValue);
        System.out.println("Token reset: " + tokenValue);
    }

    @Transactional
    public void resetPassword(ResetPasswordRequest request) {
        PasswordResetToken resetToken = tokenRepository.findByToken(request.getToken())
                .orElseThrow(() -> new RuntimeException("Token non valido"));

        if (resetToken.isUsed()) {
            throw new RuntimeException("Token gia utilizzato");
        }

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token scaduto");
        }

        if (!request.getNuovaPassword().equals(request.getConfermaPassword())) {
            throw new RuntimeException("Le password non coincidono");
        }

        Utente utente = resetToken.getUtente();
        utente.setPassword(passwordEncoder.encode(request.getNuovaPassword()));
        utenteRepository.save(utente);

        resetToken.setUsed(true);
        tokenRepository.save(resetToken);
    }
}