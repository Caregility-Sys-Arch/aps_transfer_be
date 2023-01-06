package apshomebe.caregility.com.websocket.service;

import java.util.List;

import apshomebe.caregility.com.websocket.model.ApsWsConnectionStatus;
import apshomebe.caregility.com.websocket.model.EDisconnectReason;

public interface ApsWsConnectionStatusService {

	ApsWsConnectionStatus markCleientDisconnected(String socketId, EDisconnectReason disconnectReason);

	ApsWsConnectionStatus markCleientConnected(String apsMachineName, String apsIpAddress, String sessionId);

	List<ApsWsConnectionStatus> listAllWebSocketConnections();

	String findApsMachineNameBySocketId(String sessionId);

	ApsWsConnectionStatus findSocketIdByApsMachineName(String apsMachineName);

}
