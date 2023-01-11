package apshomebe.caregility.com.websocket.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import apshomebe.caregility.com.websocket.model.EDisconnectReason;
import apshomebe.caregility.com.websocket.service.ApsWsConnectionStatusService;
import apshomebe.caregility.com.websocket.service.ClientMessageTrackerImpl;

@Component
@Qualifier("activeSessionIdAPSMachineNameMapCompoent")
public class ActiveSessionIdAndAPSMachineNameMapComponent {

	private static final Logger logger = LoggerFactory.getLogger(ClientMessageTrackerImpl.class);
	// private Map<String, String> sessionIdAndAPSMachineNameMap = new
	// ConcurrentHashMap<>();
	// private Map<String, String> apsMachineNameAndSessionIdMap = new
	// ConcurrentHashMap<>();
	// Track in DB
	@Autowired
	private ApsWsConnectionStatusService clientConnectionStatusUpdaterService;

	public void addSessionBySessionIdAndMachineName(String apsMachineName, String apsIpAddress, String sessionId) {
		// sessionIdAndAPSMachineNameMap.put(sessionId, apsMachineName);
		// apsMachineNameAndSessionIdMap.put(apsMachineName, sessionId);
		clientConnectionStatusUpdaterService.markCleientConnected(apsMachineName, apsIpAddress, sessionId);
	}

	public void removeSessionBySessionIdAndMachineName(String socketId, int disconnectStatusCode) {
		// String apsMachineName = sessionIdAndAPSMachineNameMap.remove(sessionId);
		// apsMachineNameAndSessionIdMap.remove(apsMachineName);

		EDisconnectReason disconnectReason = null;
		switch (disconnectStatusCode) {
		case 1000:
			disconnectReason = EDisconnectReason.CLIENT_RESTARTING;
			break;
		case 1001:
			disconnectReason = EDisconnectReason.SERVER_INITIATED_DISCONNECT;
			break;
		case 1006:
			disconnectReason = EDisconnectReason.CLIENT_INITIATED_DISCONNECT;
			break;
		}
		clientConnectionStatusUpdaterService.markCleientDisconnected(socketId, disconnectReason);
	}

	/**
	 * Returns APS Machine Name by SessionId/SocketId if mapped with sessionId, if
	 * not returns null
	 * 
	 * @param sessionId
	 * @return
	 */
	/*
	 * public String getMachineNameBySessionId(String sessionId) {
	 * 
	 * //logger.debug("SessionID->MachineName Map:{}",
	 * sessionIdAndAPSMachineNameMap);
	 * logger.debug("Looking By SessionID/Socket Id:{}", sessionId);
	 * clientConnectionStatusUpdaterService.getMachineNameBySessionId(); return
	 * sessionIdAndAPSMachineNameMap.get(sessionId); }
	 */

	/**
	 * Returns SocketId/SessionId by Machine Name if mapped with Machine Name, if
	 * not returns null
	 * 
	 * @param sessionId
	 * @return
	 */
	/*
	 * public String getSessionIdByAPSMachineName(String apsMachineName) {
	 * logger.debug("MachineName->SessionID Map:{}", apsMachineNameAndSessionIdMap);
	 * logger.debug("Looking By Machine Name:{}", apsMachineName); return
	 * apsMachineNameAndSessionIdMap.get(apsMachineName); }
	 * 
	 * public Map<String, String> getMachineNameAndSessionId() { return
	 * apsMachineNameAndSessionIdMap; }
	 */

}
