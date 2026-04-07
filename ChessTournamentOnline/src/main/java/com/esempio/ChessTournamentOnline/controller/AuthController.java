package com.esempio.ChessTournamentOnline.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.esempio.ChessTournamentOnline.Entity.Utente;
import com.esempio.ChessTournamentOnline.security.JwtUtil;
import com.esempio.ChessTournamentOnline.service.UtenteService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UtenteService utenteService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<Utente> register(@RequestBody Utente utente) {
        Utente saved = utenteService.register(utente);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Utente loginRequest) {
        return utenteService.findByUsername(loginRequest.getUsername())
                .filter(u -> loginRequest.getPassword() != null)
                .map(u -> {
                    // Password check
                	if (passwordEncoder.matches(loginRequest.getPassword(), u.getPassword())) {
                        String token = jwtUtil.generateToken(u.getUsername(), u.getRuolo().name());
                        return ResponseEntity.ok(token);
                    }
                    return ResponseEntity.status(401).body("Invalid credentials");
                })
                .orElse(ResponseEntity.status(401).body("User not found"));
    }
}
