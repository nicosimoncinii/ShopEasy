package it.shopeasy.controller;

import it.shopeasy.dto.StatisticheResponseDTO;
import it.shopeasy.service.StatisticheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class StatisticheController {

    @Autowired
    private StatisticheService statisticheService;

    public StatisticheController(StatisticheService service){
        this.statisticheService = service;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<StatisticheResponseDTO> prendiStatistiche(){
        return ResponseEntity.ok(statisticheService.prendiStatistiche());
    }

}
