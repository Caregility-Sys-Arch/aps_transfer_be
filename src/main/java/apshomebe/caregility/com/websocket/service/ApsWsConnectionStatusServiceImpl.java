package apshomebe.caregility.com.websocket.service;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import apshomebe.caregility.com.websocket.model.ApsWsConnectionStatus;
import apshomebe.caregility.com.websocket.model.ApsWsConnectionStatusHistory;
import apshomebe.caregility.com.websocket.model.EConnectionStatus;
import apshomebe.caregility.com.websocket.model.EDisconnectReason;
import apshomebe.caregility.com.websocket.repository.ApsWsConnectionStatusHisotryRepository;
import apshomebe.caregility.com.websocket.repository.ApsWsConnectionStatusRepository;

@Service
public class ApsWsConnectionStatusServiceImpl implements ApsWsConnectionStatusService {
	@Autowired
	ApsWsConnectionStatusRepository apsWsConnectionStatusRepository;
	@Autowired
	ApsWsConnectionStatusHisotryRepository apsWsConnectionStatusHisotryRepository;

	@Override
	public ApsWsConnectionStatus markCleientDisconnected(String socketId, EDisconnectReason disconnectReason) {
		// TODO: Search connection status WS_CONNECTED as and Update as disconnected
		ApsWsConnectionStatus apsConnectionStatus = new ApsWsConnectionStatus();
		Optional<ApsWsConnectionStatus> apsWsConnectionStatusOptional = apsWsConnectionStatusRepository
				.findApsMachineNameBySocketId(socketId);
		if (apsWsConnectionStatusOptional.isPresent()) {
			apsConnectionStatus = apsWsConnectionStatusOptional.get();
			apsConnectionStatus.setUpdated_at(new Date());
		} else {
			apsConnectionStatus.setCreated_at(new Date());
		}

		// In History

		apsConnectionStatus.setStatus(EConnectionStatus.WS_DISCONNECTD);

		apsConnectionStatus.setComment(MessageFormat.format("Client Disconnected: {0}", disconnectReason));
		// apsConnectionStatus.setApsMachineName(apsMachineName);
		apsConnectionStatus = apsWsConnectionStatusRepository.save(apsConnectionStatus);
		insertToHistory(apsConnectionStatus);
		return apsConnectionStatus;

	}

	@Override
	public ApsWsConnectionStatus markCleientConnected(String apsMachineName, String apsIpAddress, String sessionId) {
		ApsWsConnectionStatus apsConnectionStatus = new ApsWsConnectionStatus();
		Optional<ApsWsConnectionStatus> apsWsConnectionStatusOptional = apsWsConnectionStatusRepository
				.findByApsMachineName(apsMachineName);
		if (apsWsConnectionStatusOptional.isPresent()) {
			apsConnectionStatus = apsWsConnectionStatusOptional.get();
			apsConnectionStatus.setUpdated_at(new Date());
		}

		apsConnectionStatus.setStatus(EConnectionStatus.WS_CONNECTED);
		apsConnectionStatus.setComment("Client Connected");
		apsConnectionStatus.setCurrent_machine_name(apsMachineName);
		apsConnectionStatus.setCurrent_ip_address(apsIpAddress);
		apsConnectionStatus.setCurrent_socket_id(sessionId);
		apsConnectionStatus.setCreated_at(new Date());
		apsConnectionStatus = apsWsConnectionStatusRepository.save(apsConnectionStatus);
		// Push to History
		insertToHistory(apsConnectionStatus);
		return apsConnectionStatus;

	}

	private void insertToHistory(ApsWsConnectionStatus apsConnectionStatus) {
		ApsWsConnectionStatusHistory apsConnectionStatusHistory = getDuplicateCopyForHistory(apsConnectionStatus);
		if (apsConnectionStatusHistory != null) {
			apsConnectionStatusHistory.setId(null);
			apsConnectionStatusHistory.setUpdated_at(new Date());
			apsWsConnectionStatusHisotryRepository.save(apsConnectionStatusHistory);
		}

	}

	@Override
	public List<ApsWsConnectionStatus> listAllWebSocketConnections() {
		return apsWsConnectionStatusRepository.findAll();

	}

	public ApsWsConnectionStatusHistory getDuplicateCopyForHistory(ApsWsConnectionStatus apsConnectionStatus) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			ApsWsConnectionStatusHistory apsWsConnectionStatusHistory = objectMapper.readValue(
					objectMapper.writeValueAsString(apsConnectionStatus), ApsWsConnectionStatusHistory.class);
			apsConnectionStatus.setId(null);// Making as new object
			return apsWsConnectionStatusHistory;
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Returns Connected APS Device Name for the given WebSocket Id. returns null if
	 * not found.
	 */
	@Override
	public String findApsMachineNameBySocketId(String sessionId) {

		Optional<ApsWsConnectionStatus> apsWsConnectionStatusOptional = apsWsConnectionStatusRepository
				.findApsMachineNameBySocketId(sessionId);
		if (apsWsConnectionStatusOptional.isPresent()) {
			return apsWsConnectionStatusOptional.get().getCurrent_machine_name();

		}
		return null;
	}

	@Override
	public ApsWsConnectionStatus findSocketIdByApsMachineName(String apsMachineName) {
		Optional<ApsWsConnectionStatus> apsWsConnectionStatusOptional = apsWsConnectionStatusRepository
				.findSocketIdByApsMachineName(apsMachineName);
		if (apsWsConnectionStatusOptional.isPresent()) {
			return apsWsConnectionStatusOptional.get();

		}
		return null;
	}
}
