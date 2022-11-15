package apshomebe.caregility.com.payload;

public class JwtAuthResponce {
	private String accessToken;
	private String machineName;
	private String machineUri;
	private String refreshToken;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public JwtAuthResponce(String accessToken, String machineName, String machineUri, String refreshToken) {
		this.accessToken = accessToken;
		this.machineName = machineName;
		this.machineUri = machineUri;
		this.refreshToken = refreshToken;
	}

	public String getMachineName() {
		return machineName;
	}

	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	public String getMachineUri() {
		return machineUri;
	}

	public void setMachineUri(String machineUri) {
		this.machineUri = machineUri;
	}

}
