package ru.danmax.soa_lab2_second_service.service;

import jakarta.persistence.EntityExistsException;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.danmax.soa_lab2_second_service.dto.request.CreateKillerTeamRequest;
import ru.danmax.soa_lab2_second_service.dto.request.GetDragonsKilledByKillerFindByIdRequest;
import ru.danmax.soa_lab2_second_service.dto.request.MoveKillerTeamToCaveRequest;
import ru.danmax.soa_lab2_second_service.dto.response.*;
import ru.danmax.soa_lab2_second_service.entity.Cave;
import ru.danmax.soa_lab2_second_service.entity.Person;
import ru.danmax.soa_lab2_second_service.entity.Team;
import ru.danmax.soa_lab2_second_service.exception.IncorrectDataException;
import ru.danmax.soa_lab2_second_service.exception.TeamSizeErrorException;
import ru.danmax.soa_lab2_second_service.repository.*;

import java.util.List;
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

    public TeamResponse createKillerTeam(
            CreateKillerTeamRequest request
    ) throws Exception{
        List<Integer> killerIds = request.getKillers();

        if (teamRepository.existsById(request.getTeamId())) {
            throw new EntityExistsException("Команда с ID = " + request.getTeamId() + " уже существует");
        }

        if (killerIds.size() > request.getTeamSize()) {
            throw new TeamSizeErrorException("Количество человек превышает размер команды");
        }

        Optional<Cave> startCaveOptional = caveRepository.findById(request.getStartCaveId());
        if (startCaveOptional.isEmpty()) {
            throw new ObjectNotFoundException("Пещера не найдена", request.getStartCaveId());
        }
        Cave startCave = startCaveOptional.get();

        List<Person> killers = personRepository.findAllById(killerIds);
        if (killers.size() != killerIds.size()) {
            throw new IncorrectDataException("Некорректные данные");
        }

        Team newTeam = Team.builder()
                .id(request.getTeamId())
                .name(request.getTeamName())
                .size(request.getTeamSize())
                .currentCave(startCave)
                .killers(killers)
                .build();

        Team savedTeam = teamRepository.save(newTeam);
        return new TeamResponse(savedTeam);
    }

    public TeamResponse moveKillerTeamToCave(
            MoveKillerTeamToCaveRequest request
    ) throws Exception {
        Integer teamId = request.getTeamId();
        Integer caveId = request.getCaveId();

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
        return new TeamResponse(team);
    }

    public StringResponse getDragonsKilledByKillerFindById(
            GetDragonsKilledByKillerFindByIdRequest request
    ) throws Exception {
        try {
            String url = "http://85.192.48.69:8080/webModule-1.0-SNAPSHOT/dragons?killer-id=" + request.getKillerId();
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(url, String.class);
            return new StringResponse(result);
        } catch (Exception e) {
            throw new IncorrectDataException("Некорректные данные");
        }
    }
}
