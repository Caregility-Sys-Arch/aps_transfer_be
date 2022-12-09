package apshomebe.caregility.com.websocket;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import apshomebe.caregility.com.websocket.model.ApsTransferTransactions;
import apshomebe.caregility.com.websocket.model.ApsWsConnectionStatus;
import apshomebe.caregility.com.websocket.service.ApsTransferTransactionsService;
import apshomebe.caregility.com.websocket.service.ApsWsConnectionStatusService;
import apshomebe.caregility.com.websocket.service.ClientMessageTrackerImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/admin/ws/api/v1")
public class APSSynchWSServerRESTController {
	private static final Logger logger = LoggerFactory.getLogger(ClientMessageTrackerImpl.class);

	@Autowired
	ApsWsConnectionStatusService clientConnectionStatusUpdaterService;
	@Autowired
	ApsTransferTransactionsService apsTransferTransactionsService;

	@GetMapping("/aboutMe")
	public ResponseEntity<?> rootPath() {
		return new ResponseEntity<>("APS Synch WebSocket Server", HttpStatus.OK);
	}

	@GetMapping("/listConnectedDeviceStatus")
	public ResponseEntity<?> listConnectedDeviceStatus() {
		List<ApsWsConnectionStatus> listConnectedDeviceStatus = clientConnectionStatusUpdaterService
				.listAllWebSocketConnections();
		return new ResponseEntity<>(listConnectedDeviceStatus, HttpStatus.OK);
	}

	@GetMapping("/listApsTransferTransaction")
	public ResponseEntity<?> listTransferTransaction() {
		List<ApsTransferTransactions> listApsTransferTransactions = apsTransferTransactionsService
				.listAllApsTransferTransactions();
		return new ResponseEntity<>(listApsTransferTransactions, HttpStatus.OK);
	}
}