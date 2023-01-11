package apshomebe.caregility.com.payload;

public class ApsWebSocketConnectionRequest {
    private String currentSocketId;
    private String currentMachineName;
    private String currentIpAddress;
    private String status;


    public String getCurrentSocketId() {
        return currentSocketId;
    }

    public void setCurrentSocketId(String currentSocketId) {
        this.currentSocketId = currentSocketId;
    }

    public String getCurrentMachineName() {
        return currentMachineName;
    }

    public void setCurrentMachineName(String currentMachineName) {
        this.currentMachineName = currentMachineName;
    }

    public String getCurrentIpAddress() {
        return currentIpAddress;
    }

    public void setCurrentIpAddress(String currentIpAddress) {
        this.currentIpAddress = currentIpAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
