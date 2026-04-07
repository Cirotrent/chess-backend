package com.esempio.ChessTournamentOnline.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.esempio.ChessTournamentOnline.Entity.Torneo;
import com.esempio.ChessTournamentOnline.Entity.Utente;
import com.esempio.ChessTournamentOnline.repository.TorneoRepository;
import com.esempio.ChessTournamentOnline.repository.UtenteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlayService {
	
	 private final UtenteRepository utenteRepository;
	 private final TorneoRepository torneoRepository;
//	    private final PasswordEncoder passwordEncoder;

	public Utente ricarica(Utente utente, double importo) {
	    utente.setMontePremi(utente.getMontePremi() + importo);
	    return utenteRepository.save(utente);
	}
	public Optional<Torneo> getTorneoAttivo(Utente utente) {
	    return torneoRepository.findAll().stream()
	            .filter(t -> t.getPartecipanti().contains(utente))
	            .findFirst();
	}
	public void abbandona(Utente utente) {
	    Torneo torneo = getTorneoAttivo(utente)
	            .orElseThrow(() -> new RuntimeException("Non sei iscritto"));

	    torneo.getPartecipanti().remove(utente);
	    torneoRepository.save(torneo);
	}
	public String iscriviti(Utente utente, Long idTorneo) {

	    if (getTorneoAttivo(utente).isPresent()) {
	        return "Sei già iscritto a un torneo";
	    }

	    Torneo torneo = torneoRepository.findById(idTorneo).orElseThrow();

	    if (utente.getMontePremi() < torneo.getQuotaIscrizione()) {
	        return "Credito insufficiente";
	    }

	    if (utente.getEloRating() < torneo.getEloMinimo()) {
	        return "ELO insufficiente";
	    }

	    torneo.getPartecipanti().add(utente);
	    torneoRepository.save(torneo);

	    return "Iscrizione completata";
	}
	
	public Map<String, String> gioca(Utente utente, Long idTorneo) {

	    Torneo torneo = getTorneoAttivo(utente)
	            .orElseThrow(() -> new RuntimeException("Non sei iscritto"));

	    if (!torneo.getId().equals(idTorneo)) {
	        throw new RuntimeException("Torneo non coerente");
	    }
	    Map<String, String> response = new HashMap<>();

	    double esito = Math.random();
	    int somma = (int)(Math.random() * 500);
	    int delta;

	    if (esito < 0.33) {
	        delta = -somma;
	        response.put("messaggio", "HAI PERSO");
	    } else if (esito < 0.66) {
	        delta = 0;
	        response.put("messaggio", "PAREGGIO");
	    } else {
	        delta = somma;
	        response.put("messaggio", "HAI VINTO");
	    }

	    double nuovoMontePremi = utente.getMontePremi() + delta;
	    response.put("credito", nuovoMontePremi + "");

	    

	    if (nuovoMontePremi < 0) {
	        utente.setMontePremi(0.0);
	        response.put("messaggio", "credito esaurito");
	    } else {
	        utente.setMontePremi(nuovoMontePremi);
	    }

	    utente.setEloRating(utente.getEloRating() + 5);

	    utenteRepository.save(utente);

	    return response;
	}
}
