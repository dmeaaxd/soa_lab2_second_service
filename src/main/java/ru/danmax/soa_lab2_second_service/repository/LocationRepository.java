package ru.danmax.soa_lab2_second_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.danmax.soa_lab2_second_service.entity.Location;

public interface LocationRepository extends JpaRepository<Location, Integer> {
}
