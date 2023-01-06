package apshomebe.caregility.com.websocket.service;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import apshomebe.caregility.com.service.EnvironmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apshomebe.caregility.com.payload.ApsBulkTransferRequest;
import apshomebe.caregility.com.payload.ApsTransferParamRequest;
import apshomebe.caregility.com.payload.ApsTransferRequest;
import apshomebe.caregility.com.payload.ServerCommand;
import apshomebe.caregility.com.websocket.model.ApsTransferTransactions;
import apshomebe.caregility.com.websocket.model.EApsTransferTransactionName;
import apshomebe.caregility.com.websocket.model.EApsTransferTransactionStatus;
import apshomebe.caregility.com.websocket.repository.ApsTransferTransactionsRepository;

@Service
public class SendCommandToAPSServiceImpl implements SendCommandToAPSService {
	private static final Logger logger = LoggerFactory.getLogger(SendCommandToAPSServiceImpl.class);
	@Autowired
	ClientMessageTracker clientMessageTracker;
	@Autowired
	ApsTransferTransactionsRepository apsTransferTransactionsRepository;

	@Override
	public void sendCommandToApsToTransfer(ApsTransferRequest apsTransferRequest) {
		logger.debug("Transfer initiate Command Received {}" ,apsTransferRequest);
		// Push in DB for Tracking
		if (apsTransferRequest != null) {
			logger.debug(
					MessageFormat.format("Processing transfer command with Command:{0} and Process Request Id:{1}",
							apsTransferRequest.getCommand(), apsTransferRequest.getProcess_request_id()));

			if ("transfer_aps".equalsIgnoreCase(apsTransferRequest.getCommand())) {
				ApsTransferTransactions apsTransferTransactionsForAps = getApsTransferTransactionsModel(
						apsTransferRequest, EApsTransferTransactionName.PREPARE_DATA);
				apsTransferTransactionsForAps.setApsTransferRequest(apsTransferRequest);
				apsTransferTransactionsForAps = apsTransferTransactionsRepository.save(apsTransferTransactionsForAps);

				// Setting UniqueTransaction Id
				apsTransferTransactionsForAps.setTransactionId(
						apsTransferRequest.getProcess_request_id() + "-" + apsTransferTransactionsForAps.getId());
				apsTransferTransactionsForAps = apsTransferTransactionsRepository.save(apsTransferTransactionsForAps);
				// Sending
				clientMessageTracker.sendMessageToClientToInitiateTransfer(apsTransferTransactionsForAps);
				return;
			}
			logger.debug("Command not matching for (Single) Transfer");

		} else {
			logger.debug("Transfer initiate Command Received with null information");
		}

	}

	@Override
	public void sendCommandToApsToTransfer(ApsBulkTransferRequest apsBulkTransferRequest) {
		logger.debug("Transfer Bulk initiate Command Received:{}" , apsBulkTransferRequest);
		// Push in DB for Tracking
		if (apsBulkTransferRequest != null) {
			logger.debug(
					MessageFormat.format("Processing transfer command with Command:{0} and Process Request Id:{1}",
							apsBulkTransferRequest.getCommand(), apsBulkTransferRequest.getProcess_request_id()));

			if ("bulk_transfer_aps".equalsIgnoreCase(apsBulkTransferRequest.getCommand())) {
				ApsTransferTransactions apsTransferTransactions = getApsTransferTransactionsModel(
						apsBulkTransferRequest, EApsTransferTransactionName.INITIATED_APS_TRNSACTION);
				apsTransferTransactions.setTransactionStatus(EApsTransferTransactionStatus.PROCESSING);
				apsTransferTransactions = apsTransferTransactionsRepository.save(apsTransferTransactions);

				logger.debug("Id:{}" ,apsTransferTransactions.getId());
				// Preparing Records
				List<ApsTransferParamRequest> apsTransferParamList = apsBulkTransferRequest.getTransfer_list();
				int count = 0;
				logger.debug("Total Records Found:{}" ,apsTransferParamList.size());
				for (ApsTransferParamRequest apsTransferParam : apsTransferParamList) {
					// Todo make a Request for
					logger.debug("Processing Record:{}" , ++count);
					ApsTransferParamRequest apsTransferParamVO = new ApsTransferParamRequest();
					apsTransferParamVO.setFrom_ip_address(apsTransferParam.getFrom_ip_address());
					apsTransferParamVO.setTo_ip_address(apsTransferParam.getTo_ip_address());
					apsTransferParamVO.setFrom_machine_name(apsTransferParam.getFrom_machine_name());
					apsTransferParamVO.setTo_machine_name(apsTransferParam.getTo_machine_name());

					ApsTransferRequest apsTransferDataVO = new ApsTransferRequest();
					apsTransferDataVO.setProcess_request_id(apsBulkTransferRequest.getProcess_request_id());
					apsTransferDataVO.setProcess_request_type(apsBulkTransferRequest.getProcess_request_type());
					apsTransferDataVO.setCommand(ServerCommand.transfer_aps.name());
					apsTransferDataVO.setParams(apsTransferParamVO);

					ApsTransferTransactions apsTransferTransactionsForAps = getApsTransferTransactionsModel(
							apsBulkTransferRequest, EApsTransferTransactionName.PREPARE_DATA);

					apsTransferTransactionsForAps.setApsTransferRequest(apsTransferDataVO);
					apsTransferTransactionsForAps = apsTransferTransactionsRepository
							.save(apsTransferTransactionsForAps);
					// Setting UniqueTransaction Id

					apsTransferTransactionsForAps
							.setTransactionId(generateTransactionId(apsTransferDataVO, apsTransferTransactionsForAps));
					// Update Transaction Id
					apsTransferTransactionsForAps.getApsTransferRequest()
							.setTransactionId(generateTransactionId(apsTransferDataVO, apsTransferTransactionsForAps));

					apsTransferTransactionsForAps = apsTransferTransactionsRepository
							.save(apsTransferTransactionsForAps);

				}
				apsTransferTransactions.setTransactionStatus(EApsTransferTransactionStatus.COMPLETED);
				apsTransferTransactions.setUpdatedAt(new Date());
				apsTransferTransactions = apsTransferTransactionsRepository.save(apsTransferTransactions);

				clientMessageTracker.sendMessageToClientToInitiateTransfer(apsTransferTransactions);

				return;
			}
			logger.debug("Command not matching for bulk Transfer");

		} else {
			logger.debug("Bulk Transfer initiate Command Received with null information");
		}

	}

	private String generateTransactionId(ApsTransferRequest apsBulkTransferRequest,
			ApsTransferTransactions apsTransferTransactionsForAps) {
		return apsBulkTransferRequest.getProcess_request_id() + "-" + apsTransferTransactionsForAps.getId();
	}

	private ApsTransferTransactions getApsTransferTransactionsModel(ApsTransferRequest apsTransferRequest,
			EApsTransferTransactionName apsTransferTransactionName) {
		ApsTransferTransactions apsTransferTransactions = new ApsTransferTransactions();
		apsTransferTransactions.setProcess_request_id(apsTransferRequest.getProcess_request_id());
		apsTransferTransactions.setComment(apsTransferRequest.getCommand());
		apsTransferTransactions.setTransactionName(apsTransferTransactionName);
		apsTransferTransactions.setCreatedAt(new Date());
		apsTransferTransactions.setUpdatedAt(new Date());
		return apsTransferTransactions;
	}

	private ApsTransferTransactions getApsTransferTransactionsModel(final ApsBulkTransferRequest apsBulkTransferRequest,
			final EApsTransferTransactionName apsTransferTransactionName) {
		ApsTransferTransactions apsTransferTransactions = new ApsTransferTransactions();
		apsTransferTransactions.setProcess_request_id(apsBulkTransferRequest.getProcess_request_id());
		apsTransferTransactions.setComment(apsBulkTransferRequest.getCommand());
		apsTransferTransactions.setTransactionName(apsTransferTransactionName);
		apsTransferTransactions.setCreatedAt(new Date());
		apsTransferTransactions.setUpdatedAt(new Date());

		return apsTransferTransactions;
	}
}
