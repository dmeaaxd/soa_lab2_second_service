package ru.danmax.soa_lab2_second_service.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.danmax.soa_lab2_second_service.entity.Location;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KillerDTO {
    private String name;
    private String passportId;
    private Location location;
}
