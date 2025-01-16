package ru.danmax.soa_lab2_second_service.dto.request;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;
import ru.danmax.soa_lab2_second_service.config.WebServiceConfig;

import java.util.List;

@XmlRootElement(namespace = WebServiceConfig.NAMESPACE_URI, name = "CreateKillerTeamRequest")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateKillerTeamRequest {
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private Integer teamId;

    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private String teamName;

    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private Integer teamSize;

    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private Integer startCaveId;

    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private List<Integer> killers;
}
