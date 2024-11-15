package ru.danmax.soa_lab2_second_service.service;


import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.danmax.soa_lab2_second_service.dto.KillerDTO;
import ru.danmax.soa_lab2_second_service.dto.KillersDTO;
import ru.danmax.soa_lab2_second_service.dto.LocationDTO;
import ru.danmax.soa_lab2_second_service.entity.Cave;
import ru.danmax.soa_lab2_second_service.entity.Location;
import ru.danmax.soa_lab2_second_service.entity.Person;
import ru.danmax.soa_lab2_second_service.entity.Team;
import ru.danmax.soa_lab2_second_service.repository.CaveRepository;
import ru.danmax.soa_lab2_second_service.repository.LocationRepository;
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
    private final LocationRepository locationRepository;


    @Autowired
    public KillerService(TeamRepository teamRepository, PersonRepository personRepository, CaveRepository caveRepository, LocationRepository locationRepository) {
        this.teamRepository = teamRepository;
        this.personRepository = personRepository;
        this.caveRepository = caveRepository;
        this.locationRepository = locationRepository;
    }

    public ResponseEntity<?> createKillerTeam(Integer teamId, String teamName, Integer teamSize, Integer startCaveId, KillersDTO killersDTO) {

        List<Integer> killerIds = killersDTO.getKillers();

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

    public List<Person> getAllKillers() {
        return personRepository.findAll();
    }

    public ResponseEntity<?> createKiller(KillerDTO killerDTO) {

        if (personRepository.existsByPassportId(killerDTO.getPassportId())) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", 409);
            errorResponse.put("message", "Конфликт, пользователь с таким Passport ID уже существует");
            return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
        }

        try {
            Location location = Location.builder()
                    .x(killerDTO.getLocation().getX())
                    .y(killerDTO.getLocation().getY())
                    .z(killerDTO.getLocation().getZ())
                    .name(killerDTO.getLocation().getName())
                    .build();
            location = locationRepository.save(location);

            Person killer = Person.builder()
                    .name(killerDTO.getName())
                    .passportId(killerDTO.getPassportId())
                    .location(location)
                    .build();

            personRepository.save(killer);

            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", 400);
            errorResponse.put("message", "Некорректные данные");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

    }


    public ResponseEntity<?> getKillerById(Integer id) {
        try {
            if (personRepository.existsById(id)) {
                Person killer = personRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Убийца"));
                return new ResponseEntity<>(killer, HttpStatus.OK);
            }
            else {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("code", 409);
                errorResponse.put("message", "Убийца не найден");
                return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
            }
        }
        catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", 400);
            errorResponse.put("message", "Некорректный ID");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

        }
    }


    public ResponseEntity<?> updateKiller(Integer id, KillerDTO killerDTO) {

        try {
            if (personRepository.existsById(id)) {
                Person killer = personRepository.findById(id).orElse(null);

                Location location = Location.builder()
                        .x(killerDTO.getLocation().getX())
                        .y(killerDTO.getLocation().getY())
                        .z(killerDTO.getLocation().getZ())
                        .name(killerDTO.getLocation().getName())
                        .build();
                location = locationRepository.save(location);

                killer.setName(killerDTO.getName());
                killer.setPassportId(killerDTO.getPassportId());
                killer.setLocation(location);

                personRepository.save(killer);

                return ResponseEntity.ok().build();
            } else {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("code", 404);
                errorResponse.put("message", "Убийца не найден");
                return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

            }
        }
        catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", 400);
            errorResponse.put("message", "Некорректные данные");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> deleteKiller(Integer id) {
        try {
            if (personRepository.existsById(id)) {
                Person killer = personRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Убийца"));
                personRepository.delete(killer);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            else {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("code", 409);
                errorResponse.put("message", "Убийца не найден");
                return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
            }
        }
        catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", 400);
            errorResponse.put("message", "Некорректный ID");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

        }

    }
}
