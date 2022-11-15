package apshomebe.caregility.com.websocket.config;

import java.security.Principal;
import java.util.Map;
import java.util.Set;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

public class HandshakeHandlerByDeviceVIN extends DefaultHandshakeHandler {
	@Override
	protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
			Map<String, Object> attributes) {
		// generate user name by UUID
		HttpHeaders headers = request.getHeaders();
		Set<String> headerKeys = headers.keySet();
		for (String string : headerKeys) {
			System.out.println("Header Key:" + string);
		}
		// return new StompPrincipal(headers.get("aps_vin").get(0));//
		// UUID.randomUUID().toString());
		return new StompPrincipal("ASSS");// UUID.randomUUID().toString());
	}
}