package ru.danmax.soa_lab2_second_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.danmax.soa_lab2_second_service.config.WebServiceConfig;
import ru.danmax.soa_lab2_second_service.dto.request.*;
import ru.danmax.soa_lab2_second_service.dto.response.*;
import ru.danmax.soa_lab2_second_service.service.KillerCRUDService;

@Endpoint
public class KillerCRUDController {
    private final KillerCRUDService killerCRUDService;

    @Autowired
    public KillerCRUDController(KillerCRUDService killerCRUDService) {
        this.killerCRUDService = killerCRUDService;
    }

    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "GetAllKillersRequest")
    @ResponsePayload
    public PersonListResponse getAllKillers() {
        return killerCRUDService.getAllKillers();
    }

    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "GetKillerByIdRequest")
    @ResponsePayload
    public PersonResponse getKillerById(
            @RequestPayload GetKillerByIdRequest request
    ) throws Exception {
        return killerCRUDService.getKillerById(request);
    }

    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "CreateKillerRequest")
    @ResponsePayload
    public PersonResponse createKiller(
            @RequestPayload CreateKillerRequest request
    ) throws Exception {
        return killerCRUDService.createKiller(request);
    }

    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "UpdateKillerRequest")
    @ResponsePayload
    public PersonResponse updateKiller(
            @RequestPayload UpdateKillerRequest request
    ) throws Exception {
        return killerCRUDService.updateKiller(request);
    }

    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "DeleteKillerRequest")
    @ResponsePayload
    public PersonResponse deleteKiller(
            @RequestPayload DeleteKillerRequest request
    ) throws Exception {
        return killerCRUDService.deleteKiller(request);
    }

    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "GetKillerTeamsRequest")
    @ResponsePayload
    public TeamListResponse getKillerTeams() {
        return killerCRUDService.getKillerTeams();
    }

    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "GetCavesRequest")
    @ResponsePayload
    public CaveListResponse getCaves() {
        return killerCRUDService.getCaves();
    }
}
