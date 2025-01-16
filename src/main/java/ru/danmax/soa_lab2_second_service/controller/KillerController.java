package ru.danmax.soa_lab2_second_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.danmax.soa_lab2_second_service.config.WebServiceConfig;
import ru.danmax.soa_lab2_second_service.dto.request.CreateKillerTeamRequest;
import ru.danmax.soa_lab2_second_service.dto.response.CreateKillerTeamResponse;
import ru.danmax.soa_lab2_second_service.service.KillerService;


@Endpoint
public class KillerController {
    private final KillerService killerService;

    @Autowired
    public KillerController(KillerService killerService) {
        this.killerService = killerService;
    }

    //    @GetMapping("/{id}/killed-dragons")
//    public ResponseEntity<?> getDragonsKilledByKillerFindById(@PathVariable Integer id) throws Exception {
//        return killerService.getDragonsKilledByKillerFindById(id);
//    }
//
    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "CreateKillerTeamRequest")
    @ResponsePayload
    public CreateKillerTeamResponse createKillerTeam(
            @RequestPayload CreateKillerTeamRequest createKillerTeamRequest
    ) throws Exception {
        System.out.println(createKillerTeamRequest.getTeamId());
        return killerService.createKillerTeam(createKillerTeamRequest);
    }


//    @PutMapping("/team/{teamId}/move-to-cave/{caveId}")
//    public ResponseEntity<?> moveKillerTeamToCave(@PathVariable Integer teamId, @PathVariable Integer caveId) {
//        return killerService.moveKillerTeamToCave(teamId, caveId);
//    }
//
}
