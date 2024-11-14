package ru.danmax.soa_lab2_second_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "locations")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int x;

    private double y;

    private int z;

    @NonNull
    @Column(nullable = false)
    private String name;
}