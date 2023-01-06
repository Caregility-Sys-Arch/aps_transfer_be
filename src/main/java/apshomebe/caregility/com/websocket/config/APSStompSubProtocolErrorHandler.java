package apshomebe.caregility.com.websocket.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler;

@Configuration
public class APSStompSubProtocolErrorHandler extends StompSubProtocolErrorHandler {
	private static final Logger logger = LoggerFactory.getLogger(APSStompSubProtocolErrorHandler.class);

	@Override
	public Message<byte[]> handleClientMessageProcessingError(Message<byte[]> clientMessage, Throwable exception) {
		logger.debug("Handle CUSTOM ERROR:{}", exception);

		exception.printStackTrace();
		if (exception instanceof MessageDeliveryException) {
			exception = exception.getCause();
		}

		return super.handleClientMessageProcessingError(clientMessage, exception);
	}

}
