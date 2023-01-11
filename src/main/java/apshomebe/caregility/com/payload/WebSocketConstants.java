package apshomebe.caregility.com.payload;

public class WebSocketConstants {
	public static String MESSAGE_BROKER_REGISTRY = "/topic";
	public static String WS_MESSAGE_TRANSFER_DESTINATION = "/topic/broadcastchanneld"; //
	public static String WS_END_POINT = "websocket";
	public static String CLIENT_MSG_SENT_TO_SERVER_TOPIC = "/app/message";
	public static String CLIENT_MSG_SUBSCRIBE_QUEUE = "/user/{0}/queue/notification";
	public static final String SEND_TO_USER_QUEUE = "/queue/notification";
	public static final String MESSAGE_MAPPING = "/message";

}
