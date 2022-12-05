package apshomebe.caregility.com.service;

import apshomebe.caregility.com.payload.ApsTransferRequest;
import apshomebe.caregility.com.payload.EnvironmentResList;
import apshomebe.caregility.com.payload.ListResponse;

import java.sql.SQLException;
import java.util.List;

public interface EnvironmentService {
    List<EnvironmentResList> getAllEnvironment();

    List<ListResponse> getChannelList(String envId, String email) throws SQLException;

    List<ListResponse> getChannelCustomersList(String envId, String channelId) throws SQLException;

    List<ListResponse> getFacilitiesList(String envId, String customerId) throws SQLException;

    List<ListResponse> getUnitList(String envId, String facilityId) throws SQLException;

    List<ListResponse> getEndpointsList(String envId, String unitId) throws SQLException;

    String transfer(ApsTransferRequest transfer);
}
