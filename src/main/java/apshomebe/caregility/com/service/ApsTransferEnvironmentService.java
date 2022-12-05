package apshomebe.caregility.com.service;

import apshomebe.caregility.com.payload.ApsTransferRequest;

public interface ApsTransferEnvironmentService {
    ApsTransferRequest getAPSTransferDetails(String fromMachineName, String socketId);
    boolean updateStatusAPSTransferStatus(String fromMachineName, String socketId, String status);
}
