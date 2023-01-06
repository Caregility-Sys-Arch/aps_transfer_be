package apshomebe.caregility.com.models;

import apshomebe.caregility.com.audit.Auditable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "environments")
public class Environment extends Auditable<String> {
    @Id
    private String id;

    private String name;
    private String environmentIpAddress;
    private String port;
    private String databaseIp;
    private String databasePort;
    private String databaseName;
    private String databaseUserName;
    private String databasePassword;

    private String environmentUrl;

    public String getDatabaseIp() {
        return databaseIp;
    }

    public void setDatabaseIp(String databaseIp) {
        this.databaseIp = databaseIp;
    }

    public String getDatabasePort() {
        return databasePort;
    }

    public void setDatabasePort(String databasePort) {
        this.databasePort = databasePort;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnvironmentIpAddress() {
        return environmentIpAddress;
    }

    public void setEnvironmentIpAddress(String environmentIpAddress) {
        this.environmentIpAddress = environmentIpAddress;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }


    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getDatabaseUserName() {
        return databaseUserName;
    }

    public void setDatabaseUserName(String databaseUserName) {
        this.databaseUserName = databaseUserName;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public void setDatabasePassword(String databasePassword) {
        this.databasePassword = databasePassword;
    }

    public String getEnvironmentUrl() {
        return environmentUrl;
    }

    public void setEnvironmentUrl(String environmentUrl) {
        this.environmentUrl = environmentUrl;
    }

    @Override
    public String toString() {
        return "Environment{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", environmentIpAddress='" + environmentIpAddress + '\'' +
                ", port='" + port + '\'' +
                ", databaseIp='" + databaseIp + '\'' +
                ", databasePort='" + databasePort + '\'' +
                ", databaseName='" + databaseName + '\'' +
                ", databaseUserName='" + databaseUserName + '\'' +
                ", databasePassword='" + databasePassword + '\'' +
                ", environmentUrl='" + environmentUrl + '\'' +
                '}';
    }
}
