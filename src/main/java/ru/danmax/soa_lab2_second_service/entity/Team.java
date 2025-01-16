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
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "size")
    private Integer size;

    @ManyToOne
    @JoinColumn(name = "cave_id")
    private Cave currentCave;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "team_killers",
        joinColumns = @JoinColumn(name = "team_id"),
        inverseJoinColumns = @JoinColumn(name = "killer_id")
    )
    private List<Person> killers;
}
