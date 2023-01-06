package apshomebe.caregility.com.websocket.config;

import java.security.Principal;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

public class HandshakeHandlerByDeviceVIN extends DefaultHandshakeHandler {
	private static final Logger logger = LoggerFactory.getLogger(HandshakeHandlerByDeviceVIN.class);

	@Override
	protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
			Map<String, Object> attributes) {
		// generate user name by UUID
		HttpHeaders headers = request.getHeaders();
		Set<String> headerKeys = headers.keySet();
		if (logger.isDebugEnabled()) {
			for (String string : headerKeys) {
				logger.debug("Header Key:{}", string);
			}
		}

		// return new StompPrincipal(headers.get("aps_vin").get(0));//
		// UUID.randomUUID().toString());
		return new StompPrincipal("DUMMY_NEED_TO_USE_AUTHENTICATED");// UUID.randomUUID().toString());
	}
}