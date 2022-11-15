package apshomebe.caregility.com.service;

import apshomebe.caregility.com.models.ApsTransfer;
import apshomebe.caregility.com.models.Environment;
import apshomebe.caregility.com.models.EnvironmentMapping;
import apshomebe.caregility.com.payload.*;
import apshomebe.caregility.com.repository.ApsTransferRepository;
import apshomebe.caregility.com.repository.EnvironmentMappingRepository;
import apshomebe.caregility.com.repository.EnvironmentRepository;
import apshomebe.caregility.com.websocket.config.ActiveSessionIdAndAPSMachineNameMapComponent;
import apshomebe.caregility.com.websocket.service.SendCommandToAPSServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EnvironmentService {
    private static final Logger logger = LoggerFactory.getLogger(EnvironmentService.class);
    @Autowired
    private EnvironmentRepository environmentRepository;

    @Autowired
    EnvironmentMappingRepository environmentMappingRepository;

    @Autowired
    ApsTransferRepository apsTransferRepository;
    @Autowired
    SendCommandToAPSServiceImpl sendCommandToAPSServiceImpl;
    @Autowired
    ActiveSessionIdAndAPSMachineNameMapComponent activeSessionIdAPSMachineNameMapComponent;



    @Transactional
    public List<EnvironmentResList> getAllEnvironment() {
        return environmentRepository.findAllByIdAndName();
    }

    @Transactional
    public void saveEnvironment(Environment env) {
        environmentRepository.save(env);
    }

    @Transactional
    public List<EnvironmentMapping> getAllMappings() {
        return environmentMappingRepository.findAll();
    }

    @Transactional
    public void saveEnvironmentMappings(EnvironmentMapping envMap) {
        environmentMappingRepository.save(envMap);
    }

    //TODO add the SQl Query to fetch the data from the clients database and add the conditions
    @Transactional
    public List<UserResponse> getChannelList(ChannelRequest environment) throws SQLException {
        final String QUERY = "select id,name,email from user";
        Connection connection = null;
        try {

            final String database = "jdbc:mysql://";
            //connect to the database at runtime
            String url = database + environment.getDatabaseIp() + ":" + environment.getDatabasePort() + "/" + environment.getDatabaseName();
            String user = "root";
            String password = "root";

            connection = DriverManager.getConnection(url, user, password);

            if (connection != null) {
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(QUERY);

                List<UserResponse> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(new UserResponse(rs.getString(1), rs.getString(2), rs.getString(3)));
                }


                return list;
            } else {

                return null;
            }


        } catch (SQLException e) {
            logger.error("error " + e.getMessage());
            return null;
        } finally {
            connection.close();
        }

    }

    //TODO add the SQl Query to fetch the data from the clients database and add the conditions
    @Transactional
    public List<UserResponse> getChannelCustomersList(ChannelRequest environment, String envId) throws SQLException {
        final String QUERY = "select id,name,email from user";


        final String QUERY1 = "select ClientAlias.id,ClientAlias.client_name as name from clients as ClientAlias   join client_admins as ClientAdminAlias  on  ClientAlias.client_admin_id=ClientAdminAlias.id  where ClientAdminAlias.status=1 and ClientAlias.id in(1,2,3);";


        Connection connection = null;
        try {

            final String database = "jdbc:mysql://";
            // connect way #1
            String url = database + environment.getDatabaseIp() + ":" + environment.getDatabasePort() + "/" + environment.getDatabaseName();
            String user = "root";
            String password = "root";

            connection = DriverManager.getConnection(url, user, password);

            if (connection != null) {
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(QUERY);

                List<UserResponse> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(new UserResponse(rs.getString(1), rs.getString(2), rs.getString(3)));
                }


                return list;
            } else {

                return null;
            }


        } catch (SQLException e) {
            logger.error("error " + e.getMessage());
            return null;
        } finally {
            connection.close();
        }

    }

    //TODO add the SQl Query to fetch the data from the clients database and add the conditions
    @Transactional
    public List<UserResponse> getFacilitiesList(ChannelRequest environment, String customerId) throws SQLException {
        final String QUERY = "select id,name,email from user";
        final String QUERY1 = "select building.id,building.name from buildings as building join locations as locationAlias" +
                "  on building.location_id=locationAlias.id join clients as clientAlias " +
                "on  locationAlias.client_id=clientAlias.id join client_admins as clientAdminAlias" +
                " on clientAlias.client_admin_id=clientAdminAlias.id" +
                "where building.status=1 and " +
                "clientAdminAlias.status=1 and" +
                "clientAlias.status=1 and " +
                "clientAlias.id=" + customerId + "" +
                "order by building.name asc,building.id asc";
        Connection connection = null;
        try {

            final String database = "jdbc:mysql://";
            // connect way #1
            String url = database + environment.getDatabaseIp() + ":" + environment.getDatabasePort() + "/" + environment.getDatabaseName();
            String user = "root";
            String password = "root";

            connection = DriverManager.getConnection(url, user, password);

            if (connection != null) {
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(QUERY);

                List<UserResponse> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(new UserResponse(rs.getString(1), rs.getString(2), rs.getString(3)));
                }


                return list;
            } else {

                return null;
            }


        } catch (SQLException e) {
            logger.error("error " + e.getMessage());
            return null;
        } finally {
            connection.close();
        }

    }

    //TODO add the SQl Query to fetch the data from the clients database and add the conditions
    @Transactional
    public List<UserResponse> getUnitList(ChannelRequest environment, String facilityId) throws SQLException {
        final String QUERY = "select roomAlias.id,roomAlias.name from rooms  as roomAlias " +
                "join buildings as BuildingAlias on roomAlias.building_id=BuildingAlias.id" +
                "join locations as LocationAlias on BuildingAlias.location_id=LocationAlias.id" +
                "join clients as ClientAlias  on LocationAlias.client_id=ClientAlias.id" +
                "join client_admins as ClientAdminAlias on ClientAlias.client_admin_id=ClientAdminAlias.id" +
                "where BuildingAlias.id=" + facilityId + ";";

        Connection connection = null;
        try {

            final String database = "jdbc:mysql://";
            // connect way #1
            String url = database + environment.getDatabaseIp() + ":" + environment.getDatabasePort() + "/" + environment.getDatabaseName();
            String user = "root";
            String password = "root";

            connection = DriverManager.getConnection(url, user, password);

            if (connection != null) {
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(QUERY);

                List<UserResponse> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(new UserResponse(rs.getString(1), rs.getString(2), rs.getString(3)));
                }


                return list;
            } else {

                return null;
            }


        } catch (SQLException e) {
            logger.error("error " + e.getMessage());
            return null;
        } finally {
            connection.close();
        }

    }

    //endpointsList
    //TODO add the SQl Query to fetch the data from the clients database and add the conditions
    @Transactional
    public List<UserResponse> getEndpointsList(ChannelRequest environment, String unitId) throws SQLException {
        final String QUERY = "select id,name,email from user";
        Connection connection = null;
        try {

            final String database = "jdbc:mysql://";
            // connect way #1
            String url = database + environment.getDatabaseIp() + ":" + environment.getDatabasePort() + "/" + environment.getDatabaseName();
            String user = "root";
            String password = "root";

            connection = DriverManager.getConnection(url, user, password);

            if (connection != null) {
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(QUERY);

                List<UserResponse> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(new UserResponse(rs.getString(1), rs.getString(2), rs.getString(3)));
                }


                return list;
            } else {

                return null;
            }


        } catch (SQLException e) {
            logger.error("error " + e.getMessage());
            return null;
        } finally {
            connection.close();
        }

    }


    @Transactional
    public String transfer(ApsTransferRequest transfer) {
        ApsTransfer apsTransfer = new ApsTransfer();
        apsTransfer.setProcessRequestId(transfer.getProcess_request_id());
        apsTransfer.setProcessRequestType(transfer.getProcess_request_type());
        apsTransfer.setFromMachineName(transfer.getParams().getFrom_machine_name());
        apsTransfer.setFromIpAddress(transfer.getParams().getFrom_ip_address());
        apsTransfer.setToMachineName(transfer.getParams().getTo_machine_name());
        apsTransfer.setToIpAddress(transfer.getParams().getTo_ip_address());
        apsTransfer.setStatus("Verifying APS Availability");
        apsTransfer.setCommandName(transfer.getCommand());
        apsTransfer.setCreatedAt(new Date());
        //Getting SocketId/SessionIof the APS Device
        logger.info("from machine name "+transfer.getParams().getFrom_machine_name());
        String socketId= activeSessionIdAPSMachineNameMapComponent.getSessionIdByAPSMachineName(transfer.getParams().getFrom_machine_name());
        if(socketId==null|| socketId.equals("")) {
            logger.error("not getting the socket/session id ");
           // throw new RuntimeException("not getting the socket/session id ");

        }
        apsTransfer.setUserSessionId(socketId);

        //Todo get the socket Id from Ganesh Sir and set to transfer
        apsTransfer.setSocketId(socketId);
        apsTransfer.setStatus("");
        apsTransferRepository.save(apsTransfer);
        //Todo make a Request for
        ApsTransferParam apsTransferParamVO = new ApsTransferParam();
        apsTransferParamVO.setFrom_ip_address("from_ip_address");
        apsTransferParamVO.setTo_ip_address("to_ip_address");
        apsTransferParamVO.setFrom_machine_name("from_machine_name");
        apsTransferParamVO.setTo_machine_name("to_machine_name");



        ApsTransferRequest apsTransferDataVO = new ApsTransferRequest();
        apsTransferDataVO.setProcess_request_id("process_request_id");
        apsTransferDataVO.setProcess_request_type("process_request_type");
        apsTransferDataVO.setCommand(ServerCommand.transfer_aps.name());



        apsTransferDataVO.setParams(apsTransferParamVO);
        sendCommandToAPSServiceImpl.sendCommandToApsToTransfer(transfer);

        return "transfer completed";
    }






}






