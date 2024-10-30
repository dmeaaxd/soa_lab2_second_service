package ru.danmax.soa_lab2_second_service.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "teams")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "size")
    private Integer size;

    @ManyToOne
    @JoinColumn(name = "cave_id")
    private Cave currentCave;

    @ManyToMany
    @JoinColumn
    private List<Person> killers;
}
