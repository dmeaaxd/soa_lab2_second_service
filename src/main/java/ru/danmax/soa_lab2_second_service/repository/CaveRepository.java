package ru.danmax.soa_lab2_second_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.danmax.soa_lab2_second_service.entity.Cave;

public interface CaveRepository extends JpaRepository<Cave, Integer> {
}
