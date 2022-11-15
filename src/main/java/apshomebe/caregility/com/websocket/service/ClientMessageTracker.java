package apshomebe.caregility.com.websocket.service;

import apshomebe.caregility.com.payload.ClientMessage;

public interface ClientMessageTracker {

	void sendMessageToClientToInitiateTransfer(String apsMachineName, String msgString);

	void onReceiveClientMessage(String sessionId, ClientMessage clientMessage);

}
