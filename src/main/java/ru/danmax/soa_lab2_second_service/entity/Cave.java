package ru.danmax.soa_lab2_second_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "caves")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "difficulty_level")
    private Integer difficultyLevel;
}
