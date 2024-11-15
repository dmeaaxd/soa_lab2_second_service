package ru.danmax.soa_lab2_second_service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;


@Entity
@Table(name = "persons")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    private String name;

    private String passportId;

    @NotNull
    @OneToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

}
