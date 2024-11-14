package ru.danmax.soa_lab2_second_service.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.danmax.soa_lab2_second_service.dto.KillersDTO;
import ru.danmax.soa_lab2_second_service.service.KillerService;

import java.util.List;

@RestController
@RequestMapping("/killer")
public class KillerController {

    private final KillerService killerService;

    public KillerController(KillerService killerService) {
        this.killerService = killerService;
    }


    @PostMapping("/teams/create/{teamId}/{teamName}/{teamSize}/{startCaveId}")
    public ResponseEntity<?> createKillerTeam(
            @PathVariable Integer teamId,
            @PathVariable String teamName,
            @PathVariable Integer teamSize,
            @PathVariable Integer startCaveId,
            @RequestBody KillersDTO killersDTO
    ) {
        return killerService.createKillerTeam(teamId, teamName, teamSize, startCaveId, killersDTO);
    }



    @PutMapping("/team/{teamId}/move-to-cave/{caveId}")
    public ResponseEntity<?> moveKillerTeamToCave(@PathVariable Integer teamId, @PathVariable Integer caveId) {
        return killerService.moveKillerTeamToCave(teamId, caveId);
    }
}
