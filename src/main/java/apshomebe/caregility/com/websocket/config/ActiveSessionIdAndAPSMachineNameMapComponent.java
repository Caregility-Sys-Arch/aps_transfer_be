package apshomebe.caregility.com.websocket.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import apshomebe.caregility.com.websocket.service.ClientMessageTrackerImpl;

@Component
@Qualifier("activeSessionIdAPSMachineNameMapCompoent")
public class ActiveSessionIdAndAPSMachineNameMapComponent {
	private static final Logger logger = LoggerFactory.getLogger(ClientMessageTrackerImpl.class);
	private Map<String, String> sessionIdAndAPSMachineNameMap = new ConcurrentHashMap<>();
	private Map<String, String> apsMachineNameAndSessionIdMap = new ConcurrentHashMap<>();

	public void addSessionBySessionIdAndMachineName(String sessionId, String apsMachineName) {
		sessionIdAndAPSMachineNameMap.put(sessionId, apsMachineName);
		apsMachineNameAndSessionIdMap.put(apsMachineName, sessionId);
	}

	public void removeSessionBySessionIdAndMachineName(String sessionId) {
		String apsMachineName = sessionIdAndAPSMachineNameMap.remove(sessionId);
		apsMachineNameAndSessionIdMap.remove(apsMachineName);
	}

	/**
	 * Returns APS Machine Name by SessionId/SocketId if mapped with sessionId, if
	 * not returns null
	 * 
	 * @param sessionId
	 * @return
	 */
	public String getMachineNameBySessionId(String sessionId) {

		logger.debug("SessionID->MachineName Map:{}", sessionIdAndAPSMachineNameMap);
		logger.debug("Looking By SessionID/Socket Id:{}", sessionId);
		return sessionIdAndAPSMachineNameMap.get(sessionId);
	}

	/**
	 * Returns SocketId/SessionId by Machine Name if mapped with Machine Name, if
	 * not returns null
	 * 
	 * @param sessionId
	 * @return
	 */
	public String getSessionIdByAPSMachineName(String apsMachineName) {
		logger.debug("MachineName->SessionID Map:{}", apsMachineNameAndSessionIdMap);
		logger.debug("Looking By Machine Name:{}", apsMachineName);
		return apsMachineNameAndSessionIdMap.get(apsMachineName);
	}

	public Map<String, String> getMachineNameAndSessionId() {
		return apsMachineNameAndSessionIdMap;
	}

}
