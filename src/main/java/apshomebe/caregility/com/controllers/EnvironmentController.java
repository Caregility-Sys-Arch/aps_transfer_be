package apshomebe.caregility.com.controllers;

import apshomebe.caregility.com.models.ApsTransfer;
import apshomebe.caregility.com.models.Environment;
import apshomebe.caregility.com.models.EnvironmentMapping;
import apshomebe.caregility.com.payload.ApsTransferRequest;
import apshomebe.caregility.com.payload.ChannelRequest;
import apshomebe.caregility.com.payload.EnvironmentResList;
import apshomebe.caregility.com.payload.UserResponse;
import apshomebe.caregility.com.service.EnvironmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/observations")
public class EnvironmentController
{
    @Autowired

    EnvironmentService environmentService;


    @GetMapping("/")
    public ResponseEntity<List<EnvironmentResList>> getAllEnv() {
        return ResponseEntity.ok(environmentService.getAllEnvironment());
    }

    @PostMapping("/")
    public void save(@RequestBody Environment env) {
        environmentService.saveEnvironment(env);

    }

    @GetMapping("/mapping")
    public ResponseEntity<List<EnvironmentMapping>> getAllEnvMapping() {
        return ResponseEntity.ok(environmentService.getAllMappings());
    }

    @PostMapping("/mapping")
    public void saveMapping(@RequestBody EnvironmentMapping envmap) {
        environmentService.saveEnvironmentMappings(envmap);

    }

    @GetMapping("/channel")
    public List<UserResponse> getChannelList(@RequestBody ChannelRequest environment) throws SQLException {
        return environmentService.getChannelList(environment);
    }

    @GetMapping("/customers")
    public List<UserResponse> getCustomersList(@RequestBody ChannelRequest environment,@RequestParam(required = false) String channelId) throws SQLException {
        return environmentService.getChannelCustomersList(environment,channelId);
    }

    @GetMapping("/facilities")
    public List<UserResponse> getFacilityList(@RequestBody ChannelRequest environment,@RequestParam(required = false) String customerId) throws SQLException {
        return environmentService.getFacilitiesList(environment,customerId);
    }

    @GetMapping("/units")
    public List<UserResponse> getUnitList(@RequestBody ChannelRequest environment,@RequestParam(required = false) String facilityId) throws SQLException {
        return environmentService.getUnitList(environment,facilityId);
    }

    @GetMapping("/endpoints")
    public List<UserResponse> getEndpointList(@RequestBody ChannelRequest environment ,@RequestParam(required = false) String unitId) throws SQLException {
        return environmentService.getEndpointsList(environment,unitId);
    }


    @PostMapping("/aps/transfer")
    public String transferAps(@RequestBody ApsTransferRequest transferReq)
    {
     return environmentService.transfer(transferReq);
    }

//    @PostMapping("/aps/transfer/update/status/{fromMachineName}/{socketId}/{status}")
//    public boolean
//    transferAps(@PathVariable  String fromMachineName,@PathVariable String socketId, @PathVariable String status)
//    {
//        return environmentService.updateStatusAPSTransferStatus(fromMachineName,socketId,status);
//    }

}
