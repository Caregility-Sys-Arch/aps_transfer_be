package apshomebe.caregility.com.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import apshomebe.caregility.com.audit.Auditable;

@Document(collection = "authmachine")
public class AuthMachine extends Auditable<String> {
	@Id
	private String id;
	private String machineName;
	private String machineUri;
	private String machineUriToDisplay;
	private String machineVin;
	private boolean isCloud;
	private boolean isPremises;

	public AuthMachine() {
		super();
	}

	public AuthMachine(String machineName, String machineUri, String machineUriToDisplay, String machineVin,
			boolean isCloud, boolean isPremises) {
		super();

		this.machineName = machineName;
		this.machineUri = machineUri;
		this.machineUriToDisplay = machineUriToDisplay;
		this.machineVin = machineVin;
		this.isCloud = isCloud;
		this.isPremises = isPremises;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getMachineUriToDisplay() {
		return machineUriToDisplay;
	}

	public void setMachineUriToDisplay(String machineUriToDisplay) {
		this.machineUriToDisplay = machineUriToDisplay;
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

	public void setCloud(boolean isCloud) {
		this.isCloud = isCloud;
	}

	public boolean isPremises() {
		return isPremises;
	}

	public void setPremises(boolean isPremises) {
		this.isPremises = isPremises;
	}

}
