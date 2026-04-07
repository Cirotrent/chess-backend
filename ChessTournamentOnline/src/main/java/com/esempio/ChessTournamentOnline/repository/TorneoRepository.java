package com.esempio.ChessTournamentOnline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esempio.ChessTournamentOnline.Entity.Torneo;
import com.esempio.ChessTournamentOnline.Entity.Utente;

public interface TorneoRepository extends JpaRepository<Torneo, Long> {

	List<Torneo> findByUtenteCreazione(Utente utente);
	
	List<Torneo> findByEloMinimoLessThanEqual(Integer eloRating);
}
