package apshomebe.caregility.com.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/queue", "/user");
		config.setApplicationDestinationPrefixes("/app");

	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		System.out.println("websocket cors");
		// RequestUpgradeStrategy upgradeStrategy = new TomcatRequestUpgradeStrategy();
		registry.addEndpoint("/websocket").setAllowedOriginPatterns("*").withSockJS();
		registry.addEndpoint("/websocket")
				// .setHandshakeHandler(new HandshakeHandlerByDeviceVIN())// mnew
				// DefaultHandshakeHandler(upgradeStrategy))
				.setAllowedOrigins("*");
		registry.setErrorHandler(new APSStompSubProtocolErrorHandler());
	}

}