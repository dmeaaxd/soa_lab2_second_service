package ru.danmax.soa_lab2_second_service.controller;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.danmax.soa_lab2_second_service.config.WebServiceConfig;
import ru.danmax.soa_lab2_second_service.dto.response.StringResponse;

@Endpoint
public class PingController {
    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "PingRequest")
    @ResponsePayload
    public StringResponse ping() {
        return new StringResponse("result");
    }
}
