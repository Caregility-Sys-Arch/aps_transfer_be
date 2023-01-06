package apshomebe.caregility.com.payload;

public class EnvironmentResList {

    private String id ;
    private String name ;
    private String environmentIpAddress;

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
}
