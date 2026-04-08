package com.esempio.ChessTournamentOnline.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.esempio.ChessTournamentOnline.Entity.Torneo;
import com.esempio.ChessTournamentOnline.Entity.Utente;
import com.esempio.ChessTournamentOnline.repository.TorneoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TorneoService {

    private final TorneoRepository torneoRepository;

//    public List<Torneo> getAllTornei() {
//        return torneoRepository.findAll();
//    }
    public List<Torneo> getTornei(Utente utente) {

        if (utente.getRuolo() == Utente.Ruolo.ORGANIZER) {
        	List<Torneo> res = torneoRepository.findByUtenteCreazione(utente);
            return res;
        }else if  (utente.getRuolo() == Utente.Ruolo.PLAYER) {
        	return torneoRepository.findByEloMinimoLessThanEqual(utente.getEloRating());
        }

        return torneoRepository.findAll();
    }
    public List<Torneo> getTorneiPlayer(Utente utente) {
    	return torneoRepository.findByEloMinimoLessThanEqual(utente.getEloRating());
    }

    public Torneo createTorneo(Torneo torneo) {
        return torneoRepository.save(torneo);
    }
    public Torneo getById(Long id) {
        return torneoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Torneo non trovato"));
    }
    public Torneo create(Torneo torneo, Utente creatore) {
        torneo.setUtenteCreazione(creatore);
        return torneoRepository.save(torneo);
    }
    public Torneo update(Long id, Torneo torneo) {
        Torneo existing = getById(id);

        if (existing.getPartecipanti() != null && !existing.getPartecipanti().isEmpty()) {
            throw new RuntimeException("Non modificabile: ci sono partecipanti");
        }

        existing.setDenominazione(torneo.getDenominazione());
        existing.setEloMinimo(torneo.getEloMinimo());
        existing.setQuotaIscrizione(torneo.getQuotaIscrizione());
        existing.setMaxGiocatori(torneo.getMaxGiocatori());

        return torneoRepository.save(existing);
    }
    public void delete(Long id) {
        Torneo torneo = getById(id);

        if (torneo.getPartecipanti() != null && !torneo.getPartecipanti().isEmpty()) {
            throw new RuntimeException("Non eliminabile: ci sono partecipanti");
        }

        torneoRepository.delete(torneo);
    }
}
