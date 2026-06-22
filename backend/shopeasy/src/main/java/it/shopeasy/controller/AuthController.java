package it.shopeasy.controller;

import it.shopeasy.dto.auth.LoginRequest;
import it.shopeasy.dto.auth.LoginResponse;
import it.shopeasy.dto.auth.RegisterRequest;
import it.shopeasy.dto.auth.RegisterResponse;
import it.shopeasy.dto.auth.ForgotPasswordRequest;
import it.shopeasy.dto.auth.ResetPasswordRequest;
import it.shopeasy.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registra(@Valid @RequestBody RegisterRequest request) {
        RegisterResponse response = authService.registra(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        return ResponseEntity.ok("Email di reset inviata a: " + request.getEmail());
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        return ResponseEntity.ok("Password reimpostata con successo");
    }
}