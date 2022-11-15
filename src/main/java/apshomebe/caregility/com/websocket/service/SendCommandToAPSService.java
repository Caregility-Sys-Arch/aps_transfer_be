package apshomebe.caregility.com.websocket.service;

import apshomebe.caregility.com.payload.ApsTransferRequest;

public interface SendCommandToAPSService {

	void sendCommandToApsToTransfer(ApsTransferRequest apsTransferRequest);

}
