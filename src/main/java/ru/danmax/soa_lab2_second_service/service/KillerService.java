package ru.danmax.soa_lab2_second_service.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.danmax.soa_lab2_second_service.dto.KillersDTO;
import ru.danmax.soa_lab2_second_service.entity.Cave;
import ru.danmax.soa_lab2_second_service.entity.Person;
import ru.danmax.soa_lab2_second_service.entity.Team;
import ru.danmax.soa_lab2_second_service.repository.CaveRepository;
import ru.danmax.soa_lab2_second_service.repository.PersonRepository;
import ru.danmax.soa_lab2_second_service.repository.TeamRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class KillerService {

    private final TeamRepository teamRepository;
    private final PersonRepository personRepository;
    private final CaveRepository caveRepository;

    @Autowired
    public KillerService(TeamRepository teamRepository, PersonRepository personRepository, CaveRepository caveRepository) {
        this.teamRepository = teamRepository;
        this.personRepository = personRepository;
        this.caveRepository = caveRepository;
    }

    public ResponseEntity<?> createKillerTeam(Integer teamId, String teamName, Integer teamSize, Integer startCaveId, KillersDTO killersDTO) {

        List<Integer> killerIds = killersDTO.getKillers();
        System.out.println(killersDTO);
        System.out.println(killerIds);

        if (teamRepository.existsById(teamId)) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", 409);
            errorResponse.put("message", "Конфликт, либо команда с таким ID уже существует");
            return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
        }

        if (killerIds.size() > teamSize){
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", 409);
            errorResponse.put("message", "Количество человек превышает размер команды");
            return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
        }

        Optional<Cave> startCaveOptional = caveRepository.findById(startCaveId);
        if (startCaveOptional.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", 400);
            errorResponse.put("message", "Пещера не найдена");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        Cave startCave = startCaveOptional.get();

        List<Person> killers = personRepository.findAllById(killerIds);
        if (killers.size() != killerIds.size()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", 400);
            errorResponse.put("message", "Некорректные данные");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        Team newTeam = Team.builder()
                .id(teamId)
                .name(teamName)
                .size(teamSize)
                .currentCave(startCave)
                .killers(killers)
                .build();

        Team savedTeam = teamRepository.save(newTeam);
        return new ResponseEntity<>(savedTeam, HttpStatus.CREATED);

    }


    public ResponseEntity<?> moveKillerTeamToCave(Integer teamId, Integer caveId) {

        Optional<Team> teamOptional = teamRepository.findById(teamId);
        if (teamOptional.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", 404);
            errorResponse.put("message", "Команда не найдена");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        Team team = teamOptional.get();


        Optional<Cave> newCaveOptional = caveRepository.findById(caveId);
        if (newCaveOptional.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", 404);
            errorResponse.put("message", "Пещера не найдена");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        Cave newCave = newCaveOptional.get();


        team.setCurrentCave(newCave);
        teamRepository.save(team);
        return ResponseEntity.noContent().build();
    }

}
