package ru.danmax.soa_lab2_second_service.service;

import jakarta.persistence.EntityExistsException;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.danmax.soa_lab2_second_service.dto.request.*;
import ru.danmax.soa_lab2_second_service.dto.response.*;
import ru.danmax.soa_lab2_second_service.entity.Location;
import ru.danmax.soa_lab2_second_service.entity.Person;
import ru.danmax.soa_lab2_second_service.exception.IncorrectDataException;
import ru.danmax.soa_lab2_second_service.repository.CaveRepository;
import ru.danmax.soa_lab2_second_service.repository.PersonRepository;
import ru.danmax.soa_lab2_second_service.repository.TeamRepository;

@Service
public class KillerCRUDService {

    private final TeamRepository teamRepository;
    private final PersonRepository personRepository;
    private final CaveRepository caveRepository;

    @Autowired
    public KillerCRUDService(TeamRepository teamRepository, PersonRepository personRepository, CaveRepository caveRepository) {
        this.teamRepository = teamRepository;
        this.personRepository = personRepository;
        this.caveRepository = caveRepository;
    }


    public PersonListResponse getAllKillers() {
        return new PersonListResponse(personRepository.findAll());
    }

    public PersonResponse getKillerById(GetKillerByIdRequest request) throws Exception {
        Integer id = request.getId();
        try {
            if (personRepository.existsById(id)) {
                Person killer = personRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Убийца"));
                return new PersonResponse(killer);
            } else {
                throw new ObjectNotFoundException("Убийца не найден", id);
            }
        } catch (Exception e) {
            throw new IncorrectDataException("Некорректные данные");
        }
    }

    public PersonResponse createKiller(CreateKillerRequest request) throws Exception {
        String passportId = request.getPassportId();
        if (personRepository.existsByPassportId(passportId)) {
            throw new EntityExistsException("Пользователь с id = " + passportId + " ID уже существует");
        }

        try {
            Location location = Location.builder()
                    .x(request.getLocation().getX())
                    .y(request.getLocation().getY())
                    .z(request.getLocation().getZ())
                    .name(request.getLocation().getName())
                    .build();

            Person killer = Person.builder()
                    .name(request.getName())
                    .passportId(request.getPassportId())
                    .location(location)
                    .build();

            if (killer.getPassportId() != null && (killer.getPassportId().length() < 10 || killer.getPassportId().length() > 32)) {
                throw new IncorrectDataException("Длинна passport id должна быть в интервале [10; 32]");
            }

            Person savedKiller = personRepository.save(killer);
            return new PersonResponse(savedKiller);
        } catch (Exception e) {
            throw new IncorrectDataException("Некорректные данные");
        }
    }

    public PersonResponse updateKiller(UpdateKillerRequest request) throws Exception {
        Integer id = request.getId();
        try {
            if (personRepository.existsById(id)) {
                Person killer = personRepository.findById(id).orElse(null);

                Location location = Location.builder()
                        .x(request.getLocation().getX())
                        .y(request.getLocation().getY())
                        .z(request.getLocation().getZ())
                        .name(request.getLocation().getName())
                        .build();

                killer.setName(request.getName());
                killer.setPassportId(request.getPassportId());
                killer.setLocation(location);

                Person savedKiller = personRepository.save(killer);
                return new PersonResponse(savedKiller);
            } else {
                throw new ObjectNotFoundException("Убийца не найден", id);

            }
        } catch (Exception e) {
            throw new IncorrectDataException("Некорректные данные");
        }
    }

    public PersonResponse deleteKiller(DeleteKillerRequest request) throws Exception{
        Integer id = request.getId();
        try {
            if (personRepository.existsById(id)) {
                Person killer = personRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Убийца"));
                personRepository.delete(killer);
                return new PersonResponse(killer);
            } else {
                throw new ObjectNotFoundException("Убийца не найден", id);
            }
        } catch (Exception e) {
            throw new IncorrectDataException("Некорректные данные");
        }
    }

    public TeamListResponse getKillerTeams() {
        return new TeamListResponse(teamRepository.findAll());
    }

    public CaveListResponse getCaves() {
        return new CaveListResponse(caveRepository.findAll());
    }
}
