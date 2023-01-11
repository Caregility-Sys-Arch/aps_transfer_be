package apshomebe.caregility.com.models;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "aps_transfer_details")
public class ApsTransfer {
    @Id
    private String id;
    private String processRequestId;
    private String processRequestType;
    private String fromMachineName;
    private String fromIpAddress;
    private String toMachineName;
    private String userSessionId;
    private String socketId;
    private String toIpAddress;
    private String status;
    private String commandName;
    private Date createdAt;
    private Date completedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProcessRequestId() {
        return processRequestId;
    }

    public void setProcessRequestId(String processRequestId) {
        this.processRequestId = processRequestId;
    }

    public String getProcessRequestType() {
        return processRequestType;
    }

    public void setProcessRequestType(String processRequestType) {
        this.processRequestType = processRequestType;
    }

    public String getFromMachineName() {
        return fromMachineName;
    }

    public void setFromMachineName(String fromMachineName) {
        this.fromMachineName = fromMachineName;
    }

    public String getFromIpAddress() {
        return fromIpAddress;
    }

    public void setFromIpAddress(String fromIpAddress) {
        this.fromIpAddress = fromIpAddress;
    }

    public String getToMachineName() {
        return toMachineName;
    }

    public void setToMachineName(String toMachineName) {
        this.toMachineName = toMachineName;
    }

    public String getToIpAddress() {
        return toIpAddress;
    }

    public void setToIpAddress(String toIpAddress) {
        this.toIpAddress = toIpAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
    }

    public String getUserSessionId() {
        return userSessionId;
    }

    public void setUserSessionId(String userSessionId) {
        this.userSessionId = userSessionId;
    }

    public String getSocketId() {
        return socketId;
    }

    public void setSocketId(String socketId) {
        this.socketId = socketId;
    }

    @Override
    public String toString() {
        return "ApsTransfer{" +
                "id='" + id + '\'' +
                ", processRequestId='" + processRequestId + '\'' +
                ", processRequestType='" + processRequestType + '\'' +
                ", fromMachineName='" + fromMachineName + '\'' +
                ", fromIpAddress='" + fromIpAddress + '\'' +
                ", toMachineName='" + toMachineName + '\'' +
                ", userSessionId='" + userSessionId + '\'' +
                ", socketId='" + socketId + '\'' +
                ", toIpAddress='" + toIpAddress + '\'' +
                ", status='" + status + '\'' +
                ", commandName='" + commandName + '\'' +
                ", createdAt=" + createdAt +
                ", completedAt=" + completedAt +
                '}';
    }
}
