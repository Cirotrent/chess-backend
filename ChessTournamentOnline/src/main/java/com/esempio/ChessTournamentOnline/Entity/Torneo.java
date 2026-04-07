package com.esempio.ChessTournamentOnline.Entity;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tornei")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Torneo {

	 public enum Stato { APERTO, IN_CORSO, CONCLUSO }

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @NotBlank
	    private String denominazione;

	    @Builder.Default
	    private LocalDate dataCreazione = LocalDate.now();

	    @Enumerated(EnumType.STRING)
	    @Builder.Default
	    private Stato stato = Stato.APERTO;

	    @Min(0)
	    private Integer eloMinimo;

	    @Min(0)
	    private Double quotaIscrizione;

	    @Min(2)
	    private Integer maxGiocatori;

	    @ManyToMany
	    @Builder.Default
	    private Set<Utente> partecipanti = Set.of();

	    @ManyToOne
	    private Utente utenteCreazione;
}
