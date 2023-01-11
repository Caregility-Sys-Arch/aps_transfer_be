package apshomebe.caregility.com.websocket.service;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import apshomebe.caregility.com.payload.ApsTransferRequest;
import apshomebe.caregility.com.payload.ClientMessage;
import apshomebe.caregility.com.payload.ServerCommand;
import apshomebe.caregility.com.service.ApsTransferEnvironmentService;
import apshomebe.caregility.com.websocket.model.ApsTransferTransactions;
import apshomebe.caregility.com.websocket.model.EApsTransferTransactionName;
import apshomebe.caregility.com.websocket.model.EApsTransferTransactionStatus;
import apshomebe.caregility.com.websocket.repository.ApsTransferTransactionsRepository;

@Service
public class ClientMessageTrackerImpl implements ClientMessageTracker {
	private static final Logger logger = LoggerFactory.getLogger(ClientMessageTrackerImpl.class);
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	@Autowired
	private ApsWsConnectionStatusService apsWsConnectionStatusService;

	@Autowired
	ApsTransferEnvironmentService environmentService;
	@Autowired
	ApsTransferTransactionsRepository apsTransferTransactionsRepository;

	@Override
	public void sendMessageToClientToInitiateTransfer(String apsMachineName, String string) {

		this.simpMessagingTemplate.convertAndSendToUser(apsMachineName, "/queue/notification",
				new ApsTransferRequest(ServerCommand.are_ready_config_update.name()), createHeaders(null));
	}

	@Override
	public void sendMessageToClientToInitiateTransfer(ApsTransferTransactions apsTransferTransactions) {
		logger.info("inside the transations one bye one ");
		logger.debug("Sending device Ready Validation command....TODO: Need to implement");
		// Get all Records with process_request_id and initiate Transfer for each device

		List<ApsTransferTransactions> apsTransferTransactionsFromDb = apsTransferTransactionsRepository
				.findByProcessRequestIdAndTransactionStatus(apsTransferTransactions.getProcess_request_id(),
						EApsTransferTransactionName.PREPARE_DATA, null);
		logger.info("list is "+apsTransferTransactionsFromDb);
		for (ApsTransferTransactions apsTransferTransactions2 : apsTransferTransactionsFromDb) {
			logger.debug("Checking Status for Bulk Transfer:{}"
					, apsTransferTransactions2.getApsTransferRequest().getParams().getFrom_machine_name());

			ApsTransferTransactions apsTransferTransactions3 = getDuplicateCopy(apsTransferTransactions2);
			if (apsTransferTransactions3 != null) {
				// Check for APS device connected based on Machine Name/Socket Id and Send the
				// message. If not mark in Transaction for the PREPARE_DATA Record as Device Not
				// Active
				ApsTransferRequest apsTransferRequest = new ApsTransferRequest(
						ServerCommand.are_ready_config_update.name());
				apsTransferRequest.setProcess_request_id(apsTransferTransactions2.getProcess_request_id());
				apsTransferRequest.setTransactionId(apsTransferTransactions2.getTransactionId());
				apsTransferRequest.setParams(apsTransferTransactions2.getApsTransferRequest().getParams());
				apsTransferTransactions3.setTransactionName(EApsTransferTransactionName.CHECKING_DEVICE_READY);
				String targetApsMachineName = apsTransferTransactions2.getApsTransferRequest().getParams()
						.getFrom_machine_name();
				if (apsWsConnectionStatusService.findSocketIdByApsMachineName(targetApsMachineName) != null) {

					logger.info("/queue/notification  apsTransferRequest"+apsTransferRequest);
					this.simpMessagingTemplate.convertAndSendToUser(
							apsTransferTransactions2.getApsTransferRequest().getParams().getFrom_machine_name(),
							"/queue/notification", apsTransferRequest, createHeaders(null));
					apsTransferTransactions3.setTransactionStatus(EApsTransferTransactionStatus.DEVICE_ACTIVE);

				} else {
					// Mark in Trx Table for Device Not Ready
					apsTransferTransactions3.setTransactionStatus(EApsTransferTransactionStatus.DEVICE_INACTIVE);
				}
                  logger.info("apsTransferTransactions3 "+apsTransferTransactions3);
				apsTransferTransactionsRepository.save(apsTransferTransactions3);
			}

		}

	}

	private ApsTransferTransactions getDuplicateCopy(ApsTransferTransactions apsTransferTransactions2) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			ApsTransferTransactions apsTransferTransactions3 = objectMapper.readValue(
					objectMapper.writeValueAsString(apsTransferTransactions2), ApsTransferTransactions.class);
			apsTransferTransactions3.setId(null);// Making as new object
			return apsTransferTransactions3;
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void onReceiveClientMessage(String sessionId, ClientMessage clientMessage) {

		logger.info("Received from Client Acknowledgement. Initiate Transfer Configuration");
		switch (clientMessage.getCommand()) {
		case i_am_ready_config_update:
			logger.info("inside the i_am_ready_config_update");
			logger.info("client messge apshome="+clientMessage);
			String apsMachineName = apsWsConnectionStatusService.findApsMachineNameBySocketId(sessionId);
			logger.info("APS Machine Name ID Got:" + apsMachineName);
			logger.info("Client Sent Transaction Id:" + clientMessage.getTransactionId() + ":");
			ApsTransferTransactions apsTransferTransactionsDB = getApsTransferTransactionsModel(clientMessage,
					EApsTransferTransactionName.DEVICE_READY_CONFIRMED);
			logger.info("vishal apsTransferTransactionsDB++ ="+apsTransferTransactionsDB);
			apsTransferTransactionsRepository.save(apsTransferTransactionsDB);
			if (apsMachineName != null) {
				// Get Transfer Payload Details and Send for the Machine Name

//				 ApsTransferRequest apsTransferRequest =
//				 environmentService.getAPSTransferDetails(apsMachineName,sessionId);
				logger.info("Checking Transaction details...");
				ApsTransferTransactions apsTransferTransactions = apsTransferTransactionsRepository
						.findTransferRequestDataByTransactionId(clientMessage.getTransactionId(),
								EApsTransferTransactionName.PREPARE_DATA, null);
				logger.info("APS Transaction Data:{}", apsTransferTransactions);
				if (apsTransferTransactions != null) {
					ApsTransferRequest apsTransferRequest = apsTransferTransactions.getApsTransferRequest();

					logger.info("APS trasnfer request {}", apsTransferRequest);

					if (apsTransferRequest != null) {
						ObjectMapper objectMapper = new ObjectMapper();
						try {
							String jsonString = objectMapper.writeValueAsString(apsTransferRequest);
							logger.debug("Server side payload:{}" ,jsonString);
							// Marking in transaction as Processed
							apsTransferTransactions.setTransactionStatus(EApsTransferTransactionStatus.PROCESSED);
							apsTransferTransactions.setUpdatedAt(new Date());
							logger.info("session id is  for update I am ready config update ="+sessionId);
							apsTransferTransactionsRepository.save(apsTransferTransactions);
							this.simpMessagingTemplate.convertAndSendToUser(apsMachineName, "/queue/notification",
									apsTransferRequest, createHeaders(null));

							environmentService.updateStatusAPSTransferStatus(apsMachineName, sessionId,
									"APS Device ready for Update");
							logger.debug("Marking as Processed..");

						} catch (JsonProcessingException e) {
							logger.error("Exception occured while processing Client Request/Resobse:{}", e);
						}
					}
				} else {
					logger.warn("APS Request Paylout Cound not find for the Given:Machine Name{}-Session Id:{}",
							apsMachineName, sessionId);
				}

			}
			break;
		case config_update_started:
			logger.info("inside the  config update started case");
			apsMachineName = apsWsConnectionStatusService.findApsMachineNameBySocketId(sessionId);
			apsTransferTransactionsDB = getApsTransferTransactionsModel(clientMessage,
					EApsTransferTransactionName.CONFIG_UPDATE_STARTED);
			logger.info("vishal apsTransferTransactionsDB++ config_update_started ="+apsTransferTransactionsDB);
			apsTransferTransactionsRepository.save(apsTransferTransactionsDB);
			logger.info("APS Machine Name ID Got:{}", apsMachineName);

			if (apsMachineName != null) {
				// Record in Transaction Info Table
//				apsTransferTransactionsDB = getApsTransferTransactionsModel(clientMessage,
//						EApsTransferTransactionName.CONFIG_UPDATE_STARTED);
				ApsTransferTransactions apsTransferTransactions = apsTransferTransactionsRepository
						.findTransferRequestDataByTransactionId(clientMessage.getTransactionId(),
								EApsTransferTransactionName.PREPARE_DATA, null);
				logger.info("APS Transaction Data:{} ", apsTransferTransactions);

				if (apsTransferTransactions != null) {
					ApsTransferRequest apsTransferRequest = apsTransferTransactions.getApsTransferRequest();

					logger.info("APS trasnfer request {}", apsTransferRequest);
					if (apsTransferRequest != null) {
						ObjectMapper objectMapper = new ObjectMapper();
						try {
							String jsonString = objectMapper.writeValueAsString(apsTransferRequest);
							apsTransferTransactions.setTransactionStatus(EApsTransferTransactionStatus.PROCESSED);
							apsTransferTransactions.setUpdatedAt(new Date());
							logger.info("session id is  for update started="+sessionId);
							apsTransferTransactionsRepository.save(apsTransferTransactions);
							//apsTransferTransactionsRepository.save(apsTransferTransactionsDB);


							boolean statusUpdateFlag = environmentService.updateStatusAPSTransferStatus(apsMachineName, sessionId,
									"Config update started");

							if (statusUpdateFlag) {

								logger.info("Info Updated successfully for : Machine {} - Session Id:{} ", apsMachineName,
										sessionId);
							} else {
								logger.info("Info Updated failed for : Machine {} - Session Id:{} ", apsMachineName, sessionId);

							}
						} catch (JsonProcessingException e) {
							throw new RuntimeException(e);
						}
					}
				}

					}
			break;
		case config_update_failed:
			logger.info("inside the  config update failed case");
			apsMachineName = apsWsConnectionStatusService.findApsMachineNameBySocketId(sessionId);
			logger.info("APS Machine Name ID Got:{}" , apsMachineName);
			// Record in Transaction Info Table
			apsTransferTransactionsDB = getApsTransferTransactionsModel(clientMessage,
					EApsTransferTransactionName.CONFIG_UPDATE_FAILED);
			apsTransferTransactionsRepository.save(apsTransferTransactionsDB);

			if (apsMachineName != null) {

				boolean statusUpdateFlag = environmentService.updateStatusAPSTransferStatus(apsMachineName, sessionId,
						"Config update failed");

				if (statusUpdateFlag) {

					logger.info("Info Updated successfully for : Machine {} - Session Id:{} ", apsMachineName,
							sessionId);
				} else {
					logger.info("Info Updated failed for : Machine {} - Session Id:{} ", apsMachineName, sessionId);

				}
			}
			break;
		case config_update_success:
			logger.info("inside the  config update success case");
			apsMachineName = apsWsConnectionStatusService.findApsMachineNameBySocketId(sessionId);
			logger.info("APS Machine Name ID Got:{}" ,apsMachineName);
			logger.info("inside the config update success");
			logger.info("client message for config update success");
			logger.info("++"+clientMessage);
			logger.info("session id is  for update success="+sessionId);
			// Record in Transaction Info Table
			apsTransferTransactionsDB = getApsTransferTransactionsModel(clientMessage,
					EApsTransferTransactionName.CONFIG_UPDATE_SUCCESS);
			apsTransferTransactionsRepository.save(apsTransferTransactionsDB);
			if (apsMachineName != null) {

				boolean statusUpdateFlag = environmentService.updateStatusAPSTransferStatus(apsMachineName, sessionId,
						"Completed");
				if (statusUpdateFlag) {

					logger.info("Info Updated successfully for : Machine {} - Session Id:{} ", apsMachineName,
							sessionId);
				} else {
					logger.info("Info Updated failed for : Machine {} - Session Id:{} ", apsMachineName, sessionId);

				}

			}
			break;

		default:
			logger.info(MessageFormat.format("Client Command {0} not Implemented.", clientMessage.getCommand()));
		}

	}

	private ApsTransferTransactions getApsTransferTransactionsModel(ClientMessage clientMessage,
			EApsTransferTransactionName apsTransferTransactionName) {
		ApsTransferTransactions apsTransferTransactions = new ApsTransferTransactions();
		apsTransferTransactions.setProcess_request_id(clientMessage.getProcessRequestId());
		apsTransferTransactions.setTransactionId(clientMessage.getTransactionId());
		apsTransferTransactions.setApsFromIpAddress(clientMessage.getApsFromIpAddress());
		apsTransferTransactions.setApsToIpAddress(clientMessage.getApsToIpAddress());
		apsTransferTransactions.setCreatedAt(new Date());
		apsTransferTransactions.setTransactionName(apsTransferTransactionName);
		return apsTransferTransactions;
	}

	private Map<String, Object> createHeaders(String sessionId) {
		SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
		headerAccessor.setSessionId(sessionId);
		headerAccessor.setLeaveMutable(true);
		return headerAccessor.getMessageHeaders();
	}

}
