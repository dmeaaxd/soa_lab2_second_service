package ru.danmax.soa_lab2_second_service.service.converter;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import ru.danmax.soa_lab2_second_service.service.converter.xml_entity.Dragon;
import ru.danmax.soa_lab2_second_service.service.converter.xml_entity.Envelope;

import java.io.StringReader;
import java.util.List;

public class ResponseConverter {

    public static List<Dragon> convert(String xml) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Envelope.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            StringReader reader = new StringReader(xml);
            Envelope envelope = (Envelope) unmarshaller.unmarshal(reader);

            // Получаем список драконов
            return envelope.getBody().getDragonListResponse().getDragons();

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
