package apshomebe.caregility.com.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import apshomebe.caregility.com.payload.ApsBulkTransferRequest;
import apshomebe.caregility.com.payload.ApsTransferRequest;
import apshomebe.caregility.com.service.EnvironmentService;
import apshomebe.caregility.com.websocket.config.ActiveSessionIdAndAPSMachineNameMapComponent;
import apshomebe.caregility.com.websocket.service.ClientMessageTrackerImpl;
import apshomebe.caregility.com.websocket.service.SendCommandToAPSService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class APSSynchServerRESTController {
	private static final Logger logger = LoggerFactory.getLogger(APSSynchServerRESTController.class);
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@Autowired
	ActiveSessionIdAndAPSMachineNameMapComponent activeSessionIdAndAPSMachineNameMapComponent;
	@Autowired
	SendCommandToAPSService sendCommandToAPSService;
	@Autowired
	EnvironmentService environmentService;

	@GetMapping("/aboutMe")
	public ResponseEntity<?> rootPath() {
		return new ResponseEntity<>("APS Synch WebSocket Server", HttpStatus.OK);
	}

	@PostMapping("/sendMessageToAll")
	public ResponseEntity<?> sendMessage(@RequestBody String serverMessage) {
		logger.debug("Sending Message to Clients..");

		this.simpMessagingTemplate.convertAndSend("/message", serverMessage);
		return new ResponseEntity<>("Message published to All Devices", HttpStatus.OK);
	}

	@PostMapping("/sendCommandToAPS")
	public ResponseEntity<?> sendMessageToSepecificUser(@RequestBody String jsonString) {
		logger.info("Sending Message to Specific User..");
		// SpecificSpecificSpecificsendCommandToAPSService.sendCommandToApsToTransfer();

		return new ResponseEntity<>(environmentService.transfer(new ApsTransferRequest()), HttpStatus.OK);

	}

	@PostMapping("/sendBulkTransferCommandToAPS")
	public ResponseEntity<?> sendBulkTransferCommandToAPS(@RequestBody ApsBulkTransferRequest apsBulkTransferRequest) {
		logger.info("Transfering Bulk Transfer..");
		// SpecificSpecificSpecificsendCommandToAPSService.sendCommandToApsToTransfer();

		return new ResponseEntity<>(environmentService.bulkTransfer(apsBulkTransferRequest), HttpStatus.OK);

	}

	/*
	 * @GetMapping("/listConnectedDeviceStatus") public ResponseEntity<?>
	 * listConnectedDeviceStatus() {
	 * 
	 * ObjectMapper objectMapper = new ObjectMapper(); String jsonData = ""; try {
	 * jsonData = objectMapper
	 * .writeValueAsString(activeSessionIdAndAPSMachineNameMapComponent.
	 * getMachineNameAndSessionId());
	 * 
	 * } catch (JsonProcessingException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * return new ResponseEntity<>(jsonData, HttpStatus.OK); }
	 */

}