package apshomebe.caregility.com.service;

import apshomebe.caregility.com.models.ApsTransfer;
import apshomebe.caregility.com.payload.ApsTransferParam;
import apshomebe.caregility.com.payload.ApsTransferRequest;
import apshomebe.caregility.com.repository.ApsTransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ApsTransferEnvironmentService {

    @Autowired
    ApsTransferRepository apsTransferRepository;


    public ApsTransferRequest getAPSTransferDetails(String fromMachineName, String socketId)
    {
        System.out.println("fromMachineName="+fromMachineName+" socketId="+socketId);
        ApsTransfer transferDetails=apsTransferRepository.findByFromMachineNameAndSocketId(fromMachineName,socketId);

        System.out.println("aps trans="+transferDetails);
        ApsTransferRequest request=new ApsTransferRequest();
        request.setCommand(transferDetails.getCommandName());
        request.setProcess_request_id(transferDetails.getProcessRequestId());
        request.setProcess_request_type(transferDetails.getProcessRequestType());
        ApsTransferParam param=new ApsTransferParam();
        param.setFrom_ip_address(transferDetails.getFromIpAddress());
        param.setFrom_machine_name(transferDetails.getFromMachineName());
        param.setTo_ip_address(transferDetails.getToIpAddress());
        param.setTo_machine_name(transferDetails.getToMachineName());
        request.setParams(param);
        System.out.println("request="+request);
        return request;
    }

    public boolean updateStatusAPSTransferStatus(String fromMachineName,String socketId,String status)
    {
        ApsTransfer updateStatus=apsTransferRepository.findByFromMachineNameAndSocketId(fromMachineName,socketId);
        updateStatus.setStatus(status);
        if(status.equals("Completed"))
            updateStatus.setCompletedAt(new Date());
        apsTransferRepository.save(updateStatus);

        return true;
    }
}
