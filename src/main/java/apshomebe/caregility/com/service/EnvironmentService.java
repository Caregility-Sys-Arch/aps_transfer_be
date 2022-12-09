package apshomebe.caregility.com.service;

import apshomebe.caregility.com.payload.*;

import java.sql.SQLException;
import java.util.List;

public interface EnvironmentService {
    List<EnvironmentResList> getAllEnvironment();

    List<ListResponse> getChannelList(String envId, String email) throws SQLException;

    List<ListResponse> getChannelCustomersList(String envId, String channelId) throws SQLException;

    List<ListResponse> getFacilitiesList(String envId, String customerId) throws SQLException;

    List<ListResponse> getUnitList(String envId, String facilityId) throws SQLException;

    List<ListResponse> getEndpointsList(String envId, String unitId) throws SQLException;

    ApsTransferResponse transfer(ApsTransferRequest transfer);
    ApsTransferResponse transferCopy(ApsTransferRequest transfer);


    ApsTransferResponse bulkTransfer(ApsBulkTransferRequest bulkTransferRequest);

}
