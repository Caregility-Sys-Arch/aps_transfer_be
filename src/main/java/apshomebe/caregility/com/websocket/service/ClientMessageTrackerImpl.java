package apshomebe.caregility.com.websocket.service;

import apshomebe.caregility.com.payload.ApsTransferRequest;
import apshomebe.caregility.com.payload.ClientMessage;
import apshomebe.caregility.com.payload.ServerCommand;
import apshomebe.caregility.com.service.ApsTransferEnvironmentService;
import apshomebe.caregility.com.websocket.config.ActiveSessionIdAndAPSMachineNameMapComponent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Map;

@Service
public class ClientMessageTrackerImpl implements ClientMessageTracker {
    private static final Logger logger = LoggerFactory.getLogger(ClientMessageTrackerImpl.class);
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private ActiveSessionIdAndAPSMachineNameMapComponent activeSessionIdAndAPSMachineNameMapComponent;

    @Autowired
    ApsTransferEnvironmentService apsTransferEnvironmentService;

    @Override
    public void sendMessageToClientToInitiateTransfer(String apsMachineName, String string) {
        this.simpMessagingTemplate.convertAndSendToUser(apsMachineName, "/queue/notification",
                new ApsTransferRequest(ServerCommand.are_ready_config_update.name()), createHeaders(null));
    }

    @Override
    public void onReceiveClientMessage(String sessionId, ClientMessage clientMessage) {
        System.out.println("Active Session:" + activeSessionIdAndAPSMachineNameMapComponent);
        System.out.println("Received from Client Acknowledgement. Initiate Transfer Configuration");
        switch (clientMessage.getCommand()) {
            case i_am_ready_config_update:
                String apsMachineName = activeSessionIdAndAPSMachineNameMapComponent.getMachineNameBySessionId(sessionId);
                System.out.println("APS Machine Name ID Got:" + apsMachineName);
                if (apsMachineName != null) {
                    // Get Transfer Payload Details and Send for the Machine Name
                    ApsTransferRequest apsTransferRequest = apsTransferEnvironmentService.getAPSTransferDetails(apsMachineName,
                            sessionId);

                    logger.info("APS trasnfer request {}", apsTransferRequest);

                    if (apsTransferRequest != null) {
                        ObjectMapper objectMapper = new ObjectMapper();
                        try {
                            String jsonString = objectMapper.writeValueAsString(apsTransferRequest);
                            System.out.println("Server side payload:" + jsonString);
                            this.simpMessagingTemplate.convertAndSendToUser(apsMachineName, "/queue/notification",
                                    apsTransferRequest, createHeaders(null));

                            apsTransferEnvironmentService.updateStatusAPSTransferStatus(apsMachineName, sessionId,
                                    "APS Device ready for Update");

                        } catch (JsonProcessingException e) {
                            logger.error("Exception occured while processing Client Request/Resobse:{}", e);
                        }
                    } else {
                        logger.warn("APS Request Paylout Cound not find for the Given:Machine Name{}-Session Id:{}",
                                apsMachineName, sessionId);
                    }

                }
                break;
            case config_update_started:
                apsMachineName = activeSessionIdAndAPSMachineNameMapComponent.getMachineNameBySessionId(sessionId);
                logger.info("APS Machine Name ID Got:{}", apsMachineName);
                if (apsMachineName != null) {

                    boolean statusUpdateFlag = apsTransferEnvironmentService.updateStatusAPSTransferStatus(apsMachineName, sessionId,
                            "Config update started");

                    if (statusUpdateFlag) {

                        logger.info("Info Updated successfully for : Machine {} - Session Id:{} ", apsMachineName,
                                sessionId);
                    } else {
                        logger.info("Info Updated failed for : Machine {} - Session Id:{} ", apsMachineName, sessionId);

                    }

                }
                break;
            case config_update_failed:
                apsMachineName = activeSessionIdAndAPSMachineNameMapComponent.getMachineNameBySessionId(sessionId);
                System.out.println("APS Machine Name ID Got:" + apsMachineName);
                if (apsMachineName != null) {

                    boolean statusUpdateFlag = apsTransferEnvironmentService.updateStatusAPSTransferStatus(apsMachineName, sessionId,
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
                apsMachineName = activeSessionIdAndAPSMachineNameMapComponent.getMachineNameBySessionId(sessionId);
                System.out.println("APS Machine Name ID Got:" + apsMachineName);
                if (apsMachineName != null) {

                    boolean statusUpdateFlag = apsTransferEnvironmentService.updateStatusAPSTransferStatus(apsMachineName, sessionId,
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
                System.out.println(MessageFormat.format("Client Command {0} not Implemented.", clientMessage.getCommand()));
        }

    }

    private Map<String, Object> createHeaders(String sessionId) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);
        return headerAccessor.getMessageHeaders();
    }

}
