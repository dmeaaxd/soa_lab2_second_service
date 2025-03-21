package ru.danmax.soa_lab2_second_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.danmax.soa_lab2_second_service.dto.KillerDTO;
import ru.danmax.soa_lab2_second_service.dto.KillersDTO;
import ru.danmax.soa_lab2_second_service.entity.Cave;
import ru.danmax.soa_lab2_second_service.entity.Person;
import ru.danmax.soa_lab2_second_service.entity.Team;
import ru.danmax.soa_lab2_second_service.service.KillerService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/killer")
public class KillerController {
    private final KillerService killerService;

    public KillerController(KillerService killerService) {
        this.killerService = killerService;
    }

    @GetMapping
    public List<Person> getAllKillers() {
        return killerService.getAllKillers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getKillerById(@PathVariable Integer id) throws Exception {
        return killerService.getKillerById(id);
    }

    @GetMapping("/{id}/killed-dragons")
    public ResponseEntity<?> getDragonsKilledByKillerFindById(@PathVariable Integer id) throws Exception {
        return killerService.getDragonsKilledByKillerFindById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateKiller(@PathVariable Integer id, @RequestBody KillerDTO killerDTO) throws Exception {
        return killerService.updateKiller(id, killerDTO);
    }

    @PostMapping
    public ResponseEntity<?> createKiller(@RequestBody KillerDTO killerDTO) throws Exception {
        return killerService.createKiller(killerDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteKiller(@PathVariable Integer id) throws Exception {
        return killerService.deleteKiller(id);
    }

    @GetMapping("/teams")
    public List<Team> getKillerTeams() {
        return killerService.getKillerTeams();
    }

    @PostMapping("/teams/create/{teamId}/{teamName}/{teamSize}/{startCaveId}")
    public ResponseEntity<?> createKillerTeam(
            @PathVariable Integer teamId,
            @PathVariable String teamName,
            @PathVariable Integer teamSize,
            @PathVariable Integer startCaveId,
            @RequestBody KillersDTO killersDTO
    ) throws Exception {
        return killerService.createKillerTeam(teamId, teamName, teamSize, startCaveId, killersDTO);
    }


    @PutMapping("/team/{teamId}/move-to-cave/{caveId}")
    public ResponseEntity<?> moveKillerTeamToCave(@PathVariable Integer teamId, @PathVariable Integer caveId) {
        return killerService.moveKillerTeamToCave(teamId, caveId);
    }

    @GetMapping("/caves")
    public List<Cave> getCaves() {
        return killerService.getCaves();
    }
}
