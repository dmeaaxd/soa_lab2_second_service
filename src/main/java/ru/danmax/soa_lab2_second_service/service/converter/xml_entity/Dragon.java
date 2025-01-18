package ru.danmax.soa_lab2_second_service.service.converter.xml_entity;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Dragon {
    private Integer age;
    private String color;
    private Coordinates coordinates;
    private Integer id;
    private Killer killer;
    private String name;
    private String creationDate;
    private String dragonCharacter;
    private String dragonType;
}
