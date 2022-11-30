package apshomebe.caregility.com.controllers;

import apshomebe.caregility.com.exception.NoDataFoundException;
import apshomebe.caregility.com.models.Environment;
import apshomebe.caregility.com.models.EnvironmentMapping;
import apshomebe.caregility.com.payload.ApsTransferRequest;
import apshomebe.caregility.com.payload.ChannelRequest;
import apshomebe.caregility.com.payload.EnvironmentResList;
import apshomebe.caregility.com.payload.ListResponse;
import apshomebe.caregility.com.service.EnvironmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/apshome")
public class EnvironmentController {
    private static final Logger logger = LoggerFactory.getLogger(EnvironmentController.class);
    @Autowired

    EnvironmentService environmentService;


    @GetMapping("/environment")
    public ResponseEntity<List<EnvironmentResList>> getAllEnv() {
        return ResponseEntity.ok(environmentService.getAllEnvironment());
    }

//    @PostMapping("/")
//    public void save(@RequestBody Environment env) {
//        environmentService.saveEnvironment(env);
//
//    }

//    @GetMapping("/mapping")
//    public ResponseEntity<List<EnvironmentMapping>> getAllEnvMapping() {
//        return ResponseEntity.ok(environmentService.getAllMappings());
//    }

//    @PostMapping("/mapping")
//    public void saveMapping(@RequestBody EnvironmentMapping envmap) {
//        environmentService.saveEnvironmentMappings(envmap);
//
//    }

    @GetMapping("/channel")
    public ResponseEntity<List<ListResponse>> getChannelList(@RequestParam(required = false) String envId, @RequestParam(required = false) String userEmail) throws SQLException {
        logger.info("inside the getChannelList() of controller layer ");
        List<ListResponse> res = environmentService.getChannelList(envId, userEmail);
        logger.info("exiting from the getChannelList() of controller layer ");
        return ResponseEntity.ok(res);
    }

    @GetMapping("/customers")
    public ResponseEntity<List<ListResponse>> getCustomersList(@RequestParam(required = false) String envId, @RequestParam(required = false) String channelId) throws SQLException {
        logger.info("inside the getCustomersList() of controller layer ");
        List<ListResponse> res = environmentService.getChannelCustomersList(envId, channelId);
        logger.info("exiting from the getCustomersList() of controller layer ");
        return ResponseEntity.ok(res);
    }

    @GetMapping("/facilities")
    public ResponseEntity<List<ListResponse>> getFacilityList(@RequestParam(required = false) String envId, @RequestParam(required = false) String customerId) throws SQLException {
        logger.info("inside the getCustomersList() of controller layer ");
        List<ListResponse> res = environmentService.getFacilitiesList(envId, customerId);
        logger.info("exiting from the getFacilityList() of controller layer ");
        return ResponseEntity.ok(res);
    }

    @GetMapping("/units")
    public ResponseEntity<List<ListResponse>> getUnitList(@RequestParam(required = false) String envId, @RequestParam(required = false) String facilityId) throws SQLException {
        logger.info("inside the getCustomersList() of controller layer ");
        List<ListResponse> res = environmentService.getUnitList(envId, facilityId);
        logger.info("exiting from the getUnitList() of controller layer ");
        return ResponseEntity.ok(res);
    }

    @GetMapping("/endpoints")
    public ResponseEntity<List<ListResponse>> getEndpointList(@RequestParam(required = false) String envId, @RequestParam(required = false) String unitId) throws SQLException {
        logger.info("inside the getCustomersList() of controller layer ");
        List<ListResponse> res = environmentService.getEndpointsList(envId, unitId);
        logger.info("exiting from the getEndpointList() of controller layer ");
        return ResponseEntity.ok(res);
    }


    @PostMapping("/aps/transfer")
    public String transferAps(@RequestBody ApsTransferRequest transferReq) {
        logger.info("inside the getCustomersList() of controller layer ");
        return environmentService.transfer(transferReq);
    }

}
