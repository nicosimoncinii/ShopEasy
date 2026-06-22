package it.shopeasy.security;

import it.shopeasy.model.Utente;
import it.shopeasy.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utente utente = utenteRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato: " + email));
        return new User(
            utente.getEmail(),
            utente.getPassword(),
            List.of(new SimpleGrantedAuthority("ROLE_" + utente.getRuolo().getNome().name()))
    );
    }
}
