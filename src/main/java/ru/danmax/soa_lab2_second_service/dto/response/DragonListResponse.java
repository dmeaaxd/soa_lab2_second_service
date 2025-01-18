package ru.danmax.soa_lab2_second_service.dto.response;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.danmax.soa_lab2_second_service.config.WebServiceConfig;
import ru.danmax.soa_lab2_second_service.service.converter.xml_entity.Dragon;

import java.util.List;


@XmlRootElement(name = "DragonListResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DragonListResponse {
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, name = "dragons")
    private List<Dragon> dragons;
}
