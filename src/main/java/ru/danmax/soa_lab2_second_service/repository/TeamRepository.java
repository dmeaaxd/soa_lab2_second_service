package ru.danmax.soa_lab2_second_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.danmax.soa_lab2_second_service.entity.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {
}
