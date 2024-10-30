package ru.danmax.soa_lab2_second_service.service;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.danmax.soa_lab2_second_service.entity.Cave;
import ru.danmax.soa_lab2_second_service.entity.Person;
import ru.danmax.soa_lab2_second_service.entity.Team;
import ru.danmax.soa_lab2_second_service.repository.CaveRepository;
import ru.danmax.soa_lab2_second_service.repository.PersonRepository;
import ru.danmax.soa_lab2_second_service.repository.TeamRepository;

import java.util.List;

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

    public Team createKillerTeam(Long teamId, String teamName, Integer teamSize, Long startCaveId, List<Long> killerIds) {

        Cave startCave = caveRepository.findById(startCaveId).orElseThrow(
                () -> new RuntimeException("Cave not found")
        );

        List<Person> killers = personRepository.findAllById(killerIds);

        Team newTeam = Team.builder()
                .id(teamId)
                .name(teamName)
                .size(teamSize)
                .currentCave(startCave)
                .killers(killers)
                .build();

        return teamRepository.save(newTeam);

    }


    public Team moveKillerTeamToCave(Long teamId, Long caveId) {

        Team team = teamRepository.findById(teamId).orElseThrow(
                () -> new RuntimeException("Team not found")
        );

        Cave newCave = caveRepository.findById(caveId).orElseThrow(
                () -> new RuntimeException("Cave not found")
        );

        team.setCurrentCave(newCave);

        return teamRepository.save(team);
    }

}
