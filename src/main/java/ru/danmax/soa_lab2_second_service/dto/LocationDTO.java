package ru.danmax.soa_lab2_second_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationDTO {
    private Integer x;
    private Double y;
    private Integer z;

    private String name;

}
