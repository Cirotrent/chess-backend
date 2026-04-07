package com.esempio.ChessTournamentOnline.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/organizer")
public class OrganizerController {
	@GetMapping
    public String organizer() {
        return "Sei ORGANIZER";
    }
}
