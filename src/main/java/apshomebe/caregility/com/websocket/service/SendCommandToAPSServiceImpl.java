package apshomebe.caregility.com.websocket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apshomebe.caregility.com.payload.ApsTransferRequest;

@Service
public class SendCommandToAPSServiceImpl implements SendCommandToAPSService {
	@Autowired
	ClientMessageTracker clientMessageTracker;

	@Override
	public void sendCommandToApsToTransfer(ApsTransferRequest apsTransferRequest) {
		clientMessageTracker.sendMessageToClientToInitiateTransfer(
				apsTransferRequest.getParams().getFrom_machine_name(), "Are you Ready for Configuration Update");

	}

}
