package it.shopeasy.controller;

import it.shopeasy.dto.auth.ForgotPasswordRequest;
import it.shopeasy.dto.auth.ForgotPasswordResponse;
import it.shopeasy.dto.auth.ResetPasswordRequest;
import it.shopeasy.service.ForgotPasswordService;
import it.shopeasy.service.PasswordResetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class PasswordResetController {

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @Autowired
    private PasswordResetService passwordResetService;

    @PostMapping("/forgot-password")
    public ResponseEntity<ForgotPasswordResponse> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        forgotPasswordService.creaToken(request);
        return ResponseEntity.ok().body(forgotPasswordService.creaToken(request));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        passwordResetService.resetPassword(request);
        return ResponseEntity.ok("Password reimpostata con successo");
    }
}
