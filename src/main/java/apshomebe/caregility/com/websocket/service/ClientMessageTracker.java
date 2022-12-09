package apshomebe.caregility.com.websocket.service;

import apshomebe.caregility.com.payload.ClientMessage;
import apshomebe.caregility.com.websocket.model.ApsTransferTransactions;

public interface ClientMessageTracker {

	void sendMessageToClientToInitiateTransfer(String apsMachineName, String msgString);

	void onReceiveClientMessage(String sessionId, ClientMessage clientMessage);

	void sendMessageToClientToInitiateTransfer(ApsTransferTransactions apsTransferTransactions);

}
