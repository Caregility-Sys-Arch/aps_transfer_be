package apshomebe.caregility.com.models;

import apshomebe.caregility.com.audit.Auditable;

public class VinAudit extends Auditable<String> {

	private String id;

	private String vinNumber;

	private AuthMachine authMachine;

	private boolean isDeleted;

	public VinAudit( String vinNumber, AuthMachine authMachine, boolean isDeleted) {
		super();
		this.vinNumber = vinNumber;
		this.authMachine = authMachine;
		this.isDeleted = isDeleted;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVinNumber() {
		return vinNumber;
	}

	public void setVinNumber(String vinNumber) {
		this.vinNumber = vinNumber;
	}

	public AuthMachine getAuthMachine() {
		return authMachine;
	}

	public void setAuthMachine(AuthMachine authMachine) {
		this.authMachine = authMachine;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

}
