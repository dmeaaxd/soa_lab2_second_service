package ru.danmax.soa_lab2_second_service.service;


import jakarta.persistence.EntityExistsException;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.danmax.soa_lab2_second_service.Exceptions.IncorrectDataException;
import ru.danmax.soa_lab2_second_service.Exceptions.TeamSizeErrorException;
import ru.danmax.soa_lab2_second_service.dto.KillerDTO;
import ru.danmax.soa_lab2_second_service.dto.KillersDTO;
import ru.danmax.soa_lab2_second_service.entity.Cave;
import ru.danmax.soa_lab2_second_service.entity.Location;
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

    public ResponseEntity<?> createKillerTeam(Integer teamId, String teamName, Integer teamSize, Integer startCaveId, KillersDTO killersDTO) throws Exception {

        List<Integer> killerIds = killersDTO.getKillers();

        if (teamRepository.existsById(teamId)) {
            throw new EntityExistsException("Команда с ID = " + teamId + " уже существует");
        }

        if (killerIds.size() > teamSize) {
            throw new TeamSizeErrorException("Количество человек превышает размер команды");
        }

        Optional<Cave> startCaveOptional = caveRepository.findById(startCaveId);
        if (startCaveOptional.isEmpty()) {
            throw new ObjectNotFoundException("Пещера не найдена", startCaveId);
        }
        Cave startCave = startCaveOptional.get();

        List<Person> killers = personRepository.findAllById(killerIds);
        if (killers.size() != killerIds.size()) {
            throw new IncorrectDataException("Некорректные данные");
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
            throw new ObjectNotFoundException("Команда не найдена", teamId);
        }
        Team team = teamOptional.get();


        Optional<Cave> newCaveOptional = caveRepository.findById(caveId);
        if (newCaveOptional.isEmpty()) {
            throw new ObjectNotFoundException("Пещера не найдена", caveId);
        }
        Cave newCave = newCaveOptional.get();


        team.setCurrentCave(newCave);
        teamRepository.save(team);
        return ResponseEntity.noContent().build();
    }

    public List<Person> getAllKillers() {
        return personRepository.findAll();
    }

    public ResponseEntity<?> createKiller(KillerDTO killerDTO) throws Exception {
        String passportId = killerDTO.getPassportId();
        if (personRepository.existsByPassportId(passportId)) {
            throw new EntityExistsException("Пользователь с id = " + passportId + " ID уже существует");
        }

        try {
            Location location = Location.builder()
                    .x(killerDTO.getLocation().getX())
                    .y(killerDTO.getLocation().getY())
                    .z(killerDTO.getLocation().getZ())
                    .locationName(killerDTO.getLocation().getLocationName())
                    .build();

            Person killer = Person.builder()
                    .name(killerDTO.getName())
                    .passportId(killerDTO.getPassportId())
                    .location(location)
                    .build();

            personRepository.save(killer);

            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            throw new IncorrectDataException("Некорректные данные");
        }

    }


    public ResponseEntity<?> getKillerById(Integer id) throws Exception {
        try {
            if (personRepository.existsById(id)) {
                Person killer = personRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Убийца"));
                return new ResponseEntity<>(killer, HttpStatus.OK);
            } else {
                throw new ObjectNotFoundException("Убийца не найден", id);
            }
        } catch (Exception e) {
            throw new IncorrectDataException("Некорректные данные");

        }
    }


    public ResponseEntity<?> updateKiller(Integer id, KillerDTO killerDTO) throws Exception {

        try {
            if (personRepository.existsById(id)) {
                Person killer = personRepository.findById(id).orElse(null);

                Location location = Location.builder()
                        .x(killerDTO.getLocation().getX())
                        .y(killerDTO.getLocation().getY())
                        .z(killerDTO.getLocation().getZ())
                        .locationName(killerDTO.getLocation().getLocationName())
                        .build();

                killer.setName(killerDTO.getName());
                killer.setPassportId(killerDTO.getPassportId());
                killer.setLocation(location);

                personRepository.save(killer);

                return ResponseEntity.ok().build();
            } else {
                throw new ObjectNotFoundException("Убийца не найден", id);

            }
        } catch (Exception e) {
            throw new IncorrectDataException("Некорректные данные");
        }
    }

    public ResponseEntity<?> deleteKiller(Integer id) throws Exception {
        try {
            if (personRepository.existsById(id)) {
                Person killer = personRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Убийца"));
                personRepository.delete(killer);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                throw new ObjectNotFoundException("Убийца не найден", id);
            }
        } catch (Exception e) {
            throw new IncorrectDataException("Некорректные данные");

        }

    }
}
