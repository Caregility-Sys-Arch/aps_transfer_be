package apshomebe.caregility.com.service;

import apshomebe.caregility.com.exception.NoDataFoundException;
import apshomebe.caregility.com.models.ApsTransfer;
import apshomebe.caregility.com.models.Environment;
import apshomebe.caregility.com.models.EnvironmentMapping;
import apshomebe.caregility.com.payload.*;
import apshomebe.caregility.com.repository.ApsTransferRepository;
import apshomebe.caregility.com.repository.EnvironmentMappingRepository;
import apshomebe.caregility.com.repository.EnvironmentRepository;
import apshomebe.caregility.com.websocket.config.ActiveSessionIdAndAPSMachineNameMapComponent;
import apshomebe.caregility.com.websocket.service.SendCommandToAPSServiceImpl;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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


    @Transactional
    public List<ListResponse> getChannelList(String envId, String email) throws SQLException {

        Environment environment = getDatabaseInfo(envId);


        logger.info("inside the getChannelList() of environment service ");
        final String QUERY = "select " + "c.id as id, " + "c.name as name " + "from admins a " + "inner join admins_to_clients atc on a.id=atc.admin_id " + "inner join client_admins c on atc.client_id=c.id " + "where (c.status=1 or c.status is null) " + "and a.email='" + email + "' " + "order   by c.name  asc";
        Connection connection = null;
        try {
            String user, password;
            final String database = "jdbc:mysql://";
            //connect to the database at runtime
            String url = database + environment.getDatabaseIp() + ":" + environment.getDatabasePort() + "/" + environment.getDatabaseName();
            boolean envFlag = "env1".equalsIgnoreCase(environment.getName()) ? true : false;
            if (envFlag) {
                user = "root";
                password = "root";
            } else {
                user = "root";
                password = "root";
            }

            connection = DriverManager.getConnection(url, user, password);

            if (connection != null) {
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(QUERY);
                if (!rs.isBeforeFirst()) {
                    logger.warn("No data");
                    throw new NoDataFoundException(ErrorConstants.CHANNEL_ERROR_MSG);
                }
                List<ListResponse> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(new ListResponse(rs.getString(1), rs.getString(2)));
                }


                return list;
            } else {

                logger.warn("Error While Establishing The Connection ");
                throw new SQLException("Error While Establishing The Connection ");
            }

        } finally {
            connection.close();
            logger.info("exiting from  the getChannelList() of environment service ");
        }

    }


    @Transactional
    public List<ListResponse> getChannelCustomersList(String envId, String channelId) throws SQLException {

        logger.info("inside the getChannelCustomersList() of environment service ");
        Environment environment = getDatabaseInfo(envId);
        final String QUERY = "select c.id as id,c.client_name as name from client_admins ca  inner join clients c " + "on c.client_admin_id=ca.id " + "where (ca.status=1 or ca.status is null) " + "and (c.status=1 or c.status is null) " + "and ca.id=" + channelId + " " + "order by  c.client_name asc;";


        Connection connection = null;
        try {
            String user, password;
            final String database = "jdbc:mysql://";
            // connect way #1
            String url = database + environment.getDatabaseIp() + ":" + environment.getDatabasePort() + "/" + environment.getDatabaseName();
            boolean envFlag = "env1".equalsIgnoreCase(environment.getName()) ? true : false;
            if (envFlag) {
                user = "root";
                password = "root";
            } else {
                user = "root";
                password = "root";
            }

            connection = DriverManager.getConnection(url, user, password);

            if (connection != null) {
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(QUERY);
                if (!rs.isBeforeFirst()) {
                    logger.warn("No data");
                    connection.close();
                    throw new NoDataFoundException(ErrorConstants.CUSTOMER_ERROR_MSG);
                }
                List<ListResponse> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(new ListResponse(rs.getString(1), rs.getString(2)));
                }

                logger.info("exiting from  the getChannelList() of environment service ");
                return list;


            } else {
                logger.warn("Error While Establishing The Connection ");
                throw new SQLException("Error While Establishing The Connection ");
            }

        } finally {
            connection.close();

        }
    }


    @Transactional
    public List<ListResponse> getFacilitiesList(String envId, String customerId) throws SQLException {
        logger.info("inside the getFacilitiesList() of environment service ");
        Environment environment = getDatabaseInfo(envId);
        final String QUERY = "select b.id as id ,b.name as name  from locations l inner join buildings b " + "on b.location_id=l.id " + "inner join clients c on " + "l.client_id=c.id " + "inner join client_admins ca " + "on c.client_admin_id=ca.id " + "where c.id =" + customerId + " " + "and (b.status=1 or b.status is null) " + "and (c.status=1 or c.status is null) " + "and (ca.status=1 or ca.status is null) " + "order by b.name asc;";

        Connection connection = null;
        try {
            String user, password;
            final String database = "jdbc:mysql://";
            // connect way #1
            String url = database + environment.getDatabaseIp() + ":" + environment.getDatabasePort() + "/" + environment.getDatabaseName();
            boolean envFlag = "env1".equalsIgnoreCase(environment.getName()) ? true : false;
            if (envFlag) {
                user = "root";
                password = "root";
            } else {
                user = "root";
                password = "root";
            }

            connection = DriverManager.getConnection(url, user, password);

            if (connection != null) {
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(QUERY);
                if (!rs.isBeforeFirst()) {
                    logger.warn("No data");
                    throw new NoDataFoundException(ErrorConstants.FACILITY_ERROR_MSG);
                }
                List<ListResponse> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(new ListResponse(rs.getString(1), rs.getString(2)));
                }
                logger.info("exiting from  the getFacilitiesList() of environment service ");
                return list;
            } else {
                logger.warn("Error While Establishing The Connection ");
                throw new SQLException("Error While Establishing The Connection ");
            }


        } finally {
            connection.close();
        }

    }

    @Transactional
    public List<ListResponse> getUnitList(String envId, String facilityId) throws SQLException, NoDataFoundException {
        logger.info("inside the getUnitList() of environment service ");
        Environment environment = getDatabaseInfo(envId);
        final String QUERY = "select r.id as id ,r.name as name  from rooms as r  inner join buildings as b  " + " on r.building_id=b.id " + " inner join locations as l " + " on b.location_id=l.id " + " inner join clients as c " + " on l.client_id=c.id " + " inner join client_admins as ca " + " on c.client_admin_id=ca.id " + " where b.id= " + facilityId + " " + " and (r.status is null  or r.status=1) " + " and ( b.status is null or b.status=1) " + " and (c.status is null or c.status=1) " + " and (ca.status=1 or ca.status is null) " + " order by r.name asc";

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
                if (!rs.isBeforeFirst()) {
                    logger.warn("No data");
                    throw new NoDataFoundException(ErrorConstants.UNIT_ERROR_MSG);
                }
                List<ListResponse> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(new ListResponse(rs.getString(1), rs.getString(2)));
                }

                logger.info("exiting from  the getUnitList() of environment service ");
                return list;
            } else {

                logger.warn("Error While Establishing The Connection ");
                throw new SQLException("Error While Establishing The Connection ");
            }


        } finally {
            connection.close();
        }

    }

    @Transactional
    public List<ListResponse> getEndpointsList(String envId, String unitId) throws SQLException {
        logger.info("inside the getEndpointsList() of environment service ");
        Environment environment = getDatabaseInfo(envId);
        final String QUERY = "select e.id as id ,e.name as name  from rooms as r inner join endpoints e " + "on e.room_id=r.id " + "inner join  buildings as  b " + "on r.building_id=b.id " + "inner join locations as l " + "on b.location_id=l.id inner join clients as c " + "on l.client_id=c.id inner join client_admins ca  " + "on c.client_admin_id=ca.id " + "where r.id=" + unitId + " " + "and (r.status is null or r.status=1)  " + "and (e.status is null or e.status =1) " + "and(b.status is null or b.status=1) " + "and(c.status is null or c.status=1) " + "and(ca.status is null or ca.status =1) " + "order by e.name asc;";
        Connection connection = null;
        try {

            final String database = "jdbc:mysql://";
            String url = database + environment.getDatabaseIp() + ":" + environment.getDatabasePort() + "/" + environment.getDatabaseName();
            String user = "root";
            String password = "root";

            connection = DriverManager.getConnection(url, user, password);

            if (connection != null) {
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(QUERY);

                if (!rs.isBeforeFirst()) {
                    logger.warn("No data");
                    throw new NoDataFoundException(ErrorConstants.ENDPOINT_ERROR_MSG);
                }

                List<ListResponse> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(new ListResponse(rs.getString(1), rs.getString(2)));

                }

                logger.info("exiting from  the getEndpointsList() of environment service ");
                connection.close();
                return list;
            } else {
                connection.close();
                throw new NoDataFoundException(ErrorConstants.ENDPOINT_ERROR_MSG);
            }


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
        logger.info("from machine name " + transfer.getParams().getFrom_machine_name());
        String socketId = activeSessionIdAPSMachineNameMapComponent.getSessionIdByAPSMachineName(transfer.getParams().getFrom_machine_name());
        if (socketId == null || socketId.equals("")) {
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


    private Environment getDatabaseInfo(String envId) {
        Optional<Environment> environmentOptional = environmentRepository.findById(envId);
        if (!ObjectUtils.isEmpty(environmentOptional)) {
            Environment environment = environmentOptional.get();
            return environment;
        } else {
            logger.error("No database found with given id " + envId);
            return null;
        }
    }

}






