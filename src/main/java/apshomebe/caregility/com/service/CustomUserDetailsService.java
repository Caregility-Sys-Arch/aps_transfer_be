package apshomebe.caregility.com.service;

import apshomebe.caregility.com.exception.NoDataFoundException;
import apshomebe.caregility.com.models.Environment;
import apshomebe.caregility.com.payload.ErrorConstants;
import apshomebe.caregility.com.repository.AuthMachineRepository;
import apshomebe.caregility.com.repository.EnvironmentRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
@Log
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    AuthMachineRepository authMachineRepository;
    @Autowired
    EnvironmentRepository environmentRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        String arr[] = username.split("]");
        String userEmail = arr[0];
        String environmentUrl = arr[1];

        Connection connection = null;
        final String QUERY = "select * from admins where email='" + userEmail + "';";

        final String database = "jdbc:mysql://";

        Environment environment = environmentRepository.findByEnvironmentUrl(environmentUrl).get();
        String url = database + environment.getDatabaseIp() + ":" + environment.getDatabasePort() + "/" + environment.getDatabaseName();

        String user = environment.getDatabaseUserName();
        String password = environment.getDatabasePassword();
        try {
            connection = DriverManager.getConnection(url, user, password);

            if (connection != null) {

                Statement stmt = connection.createStatement();

                ResultSet rs = stmt.executeQuery(QUERY);
                ResultSetMetaData rsmd = rs.getMetaData();

                if (!rs.isBeforeFirst()) {
                    connection.close();
                    throw new NoDataFoundException(ErrorConstants.ENVIRONMENT_USER_ERROR_MSG);
                } else {
                    CustomUserAuthDetails authDetails = new CustomUserAuthDetails();

                    while (rs.next()) {
                        authDetails = authDetails.build(rs.getString("id"), rs.getString("email"), environmentUrl);
                    }
                    return authDetails;

                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;


    }
}
