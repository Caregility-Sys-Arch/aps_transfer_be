package apshomebe.caregility.com.service;

import apshomebe.caregility.com.models.ApsTransferCopy;
import apshomebe.caregility.com.payload.ApsTransferParamRequest;
import apshomebe.caregility.com.payload.ApsTransferRequest;
import apshomebe.caregility.com.repository.ApsTransferCopyRepository;
import apshomebe.caregility.com.repository.ApsTransferRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class ApsTransferEnvironmentServiceImpl implements ApsTransferEnvironmentService {
    private static final Logger logger = LoggerFactory.getLogger(ApsTransferEnvironmentServiceImpl.class);

    @Autowired
    ApsTransferRepository apsTransferRepository;

    @Autowired
    ApsTransferCopyRepository apsTransferCopyRepository;


    @Transactional
    @Override
    public ApsTransferRequest getAPSTransferDetails(String fromMachineName, String socketId) {
        logger.info("fromMachineName= {} socketId={}" , fromMachineName , socketId);
        // ApsTransfer transferDetails = apsTransferRepository.findByFromMachineNameAndSocketId(fromMachineName, socketId);

        ApsTransferCopy copy = apsTransferCopyRepository.findByParams_FromMachineNameAndParams_SessionId(fromMachineName, socketId);
//        logger.debug("aps trans={}" , transferDetails);

        ApsTransferRequest request = new ApsTransferRequest();

//        request.setCommand(transferDetails.getCommandName());
//          request.setProcess_request_id(transferDetails.getProcessRequestId());
//         request.setProcess_request_type(transferDetails.getProcessRequestType());
//        ApsTransferParamRequest param = new ApsTransferParamRequest();
//        param.setFrom_ip_address(transferDetails.getFromIpAddress());
//        param.setFrom_machine_name(transferDetails.getFromMachineName());
//        param.setTo_ip_address(transferDetails.getToIpAddress());
//        param.setTo_machine_name(transferDetails.getToMachineName());

        request.setCommand(copy.getCommand());
        request.setProcess_request_id(copy.getProcessRequestId());
        request.setProcess_request_type(copy.getProcessRequestType());
        ApsTransferParamRequest param = new ApsTransferParamRequest();
        param.setFrom_ip_address(copy.getParams().getFromIpAddress());
        param.setFrom_machine_name(copy.getParams().getFromMachineName());
        param.setTo_ip_address(copy.getParams().getToIpAddress());
        param.setTo_machine_name(copy.getParams().getToMachineName());
        request.setParams(param);
        logger.debug("request={}" , request);
        return request;
    }

    @Transactional
    @Override
    public boolean updateStatusAPSTransferStatus(String fromMachineName, String socketId, String status) {
        // ApsTransfer updateStatus = apsTransferRepository.findByFromMachineNameAndSocketId(fromMachineName, socketId);
        ApsTransferCopy updateStatus = apsTransferCopyRepository.findByParams_FromMachineNameAndParams_SessionId(fromMachineName, socketId);

        // updateStatus.setStatus(status);
        updateStatus.setStatus(status);
        if (status.equals("Completed"))
            updateStatus.setCompletedAt(new Date());
        // apsTransferRepository.save(updateStatus);
        apsTransferCopyRepository.save(updateStatus);

        return true;
    }
}
