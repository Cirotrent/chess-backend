package com.esempio.ChessTournamentOnline.repository;

import java.util.Optional;

import com.esempio.ChessTournamentOnline.Entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtenteRepository extends JpaRepository<Utente, Long> {
	Optional<Utente> findByUsername(String username);	
}
