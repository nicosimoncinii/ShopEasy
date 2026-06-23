package it.shopeasy.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/api/auth/**",
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/swagger-initializer.js",
                    "/v3/api-docs/**",
                    "/api-docs/**",
                    "/webjars/**"
                ).permitAll()
                .requestMatchers(HttpMethod.GET, "/api/products", "/api/products/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/products/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/products/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/products/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/users", "/api/users/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/users").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/users/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasAuthority("ADMIN")
                .requestMatchers("/api/wishlist/me").authenticated()
                .requestMatchers("/api/wishlist/add/**").authenticated()
                .requestMatchers("/api/wishlist/remove/**").authenticated()
                .anyRequest().authenticated()
            )
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}