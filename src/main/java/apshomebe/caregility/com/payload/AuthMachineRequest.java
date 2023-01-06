package apshomebe.caregility.com.payload;

import javax.validation.constraints.NotBlank;

public class AuthMachineRequest {
	@NotBlank
	private String envUri;

	@NotBlank
	private String machineName;
	
	private String machineToConnect;

	public AuthMachineRequest() {

	}

	public AuthMachineRequest(@NotBlank String envUri, @NotBlank String machineName) {

		this.envUri = envUri;
		this.machineName = machineName;
	}

	public String getEnvUri() {
		return envUri;
	}

	public void setEnvUri(String envUri) {
		this.envUri = envUri;
	}

	public String getMachineName() {
		return machineName;
	}
	
	

	public String getMachineToConnect() {
		return machineToConnect;
	}

	public void setMachineToConnect(String machineToConnect) {
		this.machineToConnect = machineToConnect;
	}

	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

}
