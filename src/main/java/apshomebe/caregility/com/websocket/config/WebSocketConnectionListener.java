package apshomebe.caregility.com.websocket.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketConnectionListener {
	private static final Logger logger = LoggerFactory.getLogger(WebSocketConnectionListener.class);
	@Autowired
	ActiveSessionIdAndAPSMachineNameMapComponent activeSessionIdAndAPSMachineNameMapComponent;

	@EventListener
	private void handleSessionConnected(SessionConnectEvent event) {
		logger.info("Session Connected:");
		SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
		logger.info("APS_MACHINE_NAME:" + headers.getNativeHeader("APS_MACHINE_NAME"));
		logger.info("Session Id:" + headers.getSessionId());
		logger.info("UID:" + headers.getId());
		// @formatter:off
		/*
		Map<String, Object> headerMap = headers.toMap();
		for (Map.Entry<String, Object> entry : headerMap.entrySet()) {
			String key = entry.getKey();
			Object val = entry.getValue();
			logger.debug("Key: {} + "\t:{}",key, val);
		}
		*/
		// @formatter:on

		activeSessionIdAndAPSMachineNameMapComponent.addSessionBySessionIdAndMachineName(headers.getSessionId(),
				headers.getNativeHeader("APS_MACHINE_NAME").get(0));
		logger.info("APS_MACHINE_NAME:" + headers.getNativeHeader("APS_MACHINE_NAME"));

	}

	@EventListener
	private void handleSessionDisconnect(SessionDisconnectEvent event) {
		logger.info("Session Disconnected");
		SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
		logger.info("Session Id:" + headers.getSessionId());
		activeSessionIdAndAPSMachineNameMapComponent.removeSessionBySessionIdAndMachineName(headers.getSessionId());

	}
}