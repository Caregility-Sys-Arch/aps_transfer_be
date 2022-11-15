package apshomebe.caregility.com.payload;

import apshomebe.caregility.com.models.AuthMachine;

public class VinResponce {

	private String id;

	private String vinNumber;

	private AuthMachine authMachine;

	private boolean isDeleted;

	public VinResponce() {

	}

	public VinResponce(String id, String vinNumber, AuthMachine authMachine, boolean isDeleted) {
		super();
		this.id = id;
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
