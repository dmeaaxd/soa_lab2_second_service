package ru.danmax.soa_lab2_second_service.dto.request;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;
import ru.danmax.soa_lab2_second_service.config.WebServiceConfig;

@XmlRootElement(namespace = WebServiceConfig.NAMESPACE_URI, name = "GetKillerByIdRequest")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetKillerByIdRequest {
    @XmlElement(namespace = WebServiceConfig.NAMESPACE_URI, required = true)
    private Integer id;
}