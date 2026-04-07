package com.esempio.ChessTournamentOnline.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esempio.ChessTournamentOnline.Entity.Torneo;
import com.esempio.ChessTournamentOnline.Entity.Utente;
import com.esempio.ChessTournamentOnline.service.PlayService;
import com.esempio.ChessTournamentOnline.service.TorneoService;
import com.esempio.ChessTournamentOnline.service.UtenteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/play")
@RequiredArgsConstructor
public class PlayController {

    private final PlayService playService;
    private final UtenteService utenteService;
    private final TorneoService torneoService;

    private Utente getUtente() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return utenteService.findByUsername(username).orElseThrow();
    }

    @PostMapping("/ricarica")
    public Utente ricarica(@RequestBody Map<String, Double> body) {
        return playService.ricarica(getUtente(), body.get("importo"));
    }

    @GetMapping("/ultimo-torneo")
    public ResponseEntity<?> ultimoTorneo() {
        return playService.getTorneoAttivo(getUtente())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @DeleteMapping("/abbandona")
    public void abbandona() {
        playService.abbandona(getUtente());
    }

    @PostMapping("/iscriviti/{id}")
    public String iscrizione(@PathVariable Long id) {
        return playService.iscriviti(getUtente(), id);
    }

    @PostMapping("/gioca/{id}")
    public Map<String, String> gioca(@PathVariable Long id) {
        return playService.gioca(getUtente(), id);
    }
    
    @GetMapping("/lista-tornei")
    public List<Torneo> listaTornei() {
        return torneoService.getTorneiPlayer(getUtenteLoggato());
    }
    private Utente getUtenteLoggato() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return utenteService.findByUsername(username).orElseThrow();
    }
}
