package com.esempio.ChessTournamentOnline.Entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "utenti")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Utente {

    public enum Stato { ATTIVO, DISABILITATO }
    public enum Ruolo { ADMIN, ORGANIZER, PLAYER }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    private String cognome;

    @NotBlank
    @Column(unique = true)
    private String username;

    @NotBlank
    private String password; // BCrypt encoded

    @Builder.Default
    private LocalDate dataRegistrazione = LocalDate.now();

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Stato stato = Stato.ATTIVO;

    @Builder.Default
    private Integer eloRating = 0;

    @Builder.Default
    private Double montePremi = 0.0;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Ruolo ruolo = Ruolo.PLAYER;
}
