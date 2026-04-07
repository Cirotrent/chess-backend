package com.esempio.ChessTournamentOnline.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.esempio.ChessTournamentOnline.Entity.Torneo;
import com.esempio.ChessTournamentOnline.Entity.Utente;
import com.esempio.ChessTournamentOnline.service.TorneoService;
import com.esempio.ChessTournamentOnline.service.UtenteService;

import java.util.List;


@RestController
@RequestMapping("/tornei")
@RequiredArgsConstructor
public class TorneoController {

    private final TorneoService torneoService;
    private final UtenteService utenteService;

//    @GetMapping
//    public List<Torneo> listTornei() {
//        return torneoService.getAllTornei();
//    }
//
//    @PostMapping
//    public Torneo createTorneo(@RequestBody Torneo torneo) {
//        return torneoService.createTorneo(torneo);
//    }
    
    private Utente getUtenteLoggato() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return utenteService.findByUsername(username).orElseThrow();
    }
    
    @GetMapping
    public List<Torneo> getAll() {
        return torneoService.getTornei(getUtenteLoggato());
    }

    @GetMapping("/{id}")
    public Torneo getById(@PathVariable Long id) {
        return torneoService.getById(id);
    }

    @PostMapping
    public Torneo create(@RequestBody Torneo torneo) {
        return torneoService.create(torneo, getUtenteLoggato());
    }

    @PutMapping("/{id}")
    public Torneo update(@PathVariable Long id, @RequestBody Torneo torneo) {
        return torneoService.update(id, torneo);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        torneoService.delete(id);
    }
}
