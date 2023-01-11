package apshomebe.caregility.com.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import apshomebe.caregility.com.payload.ApsTransferRequest;
import apshomebe.caregility.com.payload.ClientMessage;
import apshomebe.caregility.com.payload.ServerCommand;
import apshomebe.caregility.com.payload.WebSocketConstants;
import apshomebe.caregility.com.websocket.service.ClientMessageTracker;
import apshomebe.caregility.com.websocket.service.ClientMessageTrackerImpl;

@Controller
public class APSSynchServerWebSocketController {
	private static final Logger logger = LoggerFactory.getLogger(ClientMessageTrackerImpl.class);
	@Autowired
	ClientMessageTracker clientMessageTracker;

	@MessageMapping(WebSocketConstants.MESSAGE_MAPPING)
	@SendToUser(WebSocketConstants.SEND_TO_USER_QUEUE)
	public ApsTransferRequest replyToMessageFromClien(StompHeaderAccessor stompHeaderAccessor,
			@Payload String clientMessage) {

		try {
			ClientMessage clientMessageConverted = new ObjectMapper().readValue(clientMessage, ClientMessage.class);
			// Note: Other than SessionId other header values are null....
			String sessionId = stompHeaderAccessor.getSessionId();
			logger.info("Received from Session Id:{} message:{}", sessionId, clientMessage);
			clientMessageTracker.onReceiveClientMessage(sessionId, clientMessageConverted);
			return new ApsTransferRequest(ServerCommand.server_acknowledgement.name());
		} catch (JsonMappingException e) {
			logger.error("Error ocurred while processing Received Client Message:", e);
		} catch (JsonProcessingException e) {
			logger.error("Error ocurred while processing Received Client Message:", e);
		}
		return new ApsTransferRequest(ServerCommand.server_acknowledgement.name());
	}

	@MessageExceptionHandler
	@SendTo("/queue/errors")
	public String handleException(Throwable exception) {
		return exception.getMessage();
	}
}