package apshomebe.caregility.com.payload;

public class MachineRegisterRequest {
	private String machineName;
	private String machineUri;
	private  String machineVin;
	private  boolean isCloud;
	private boolean isPremises;
	
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
	public String getMachineVin() {
		return machineVin;
	}
	public void setMachineVin(String machineVin) {
		this.machineVin = machineVin;
	}
	public boolean isCloud() {
		return isCloud;
	}
	public void setIsCloud(boolean isCloud) {
		this.isCloud = isCloud;
	}
	public boolean isPremises() {
		return isPremises;
	}
	public void setIsPremises(boolean isPremises) {
		this.isPremises = isPremises;
	}
}
