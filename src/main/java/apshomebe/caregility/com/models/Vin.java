package apshomebe.caregility.com.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import apshomebe.caregility.com.audit.Auditable;

@Document(collection = "vin")
public class Vin  extends Auditable<String>{
	@Id
	private String id;
	
	private String vinNumber;
	
	private AuthMachine authMachine;
	
	private boolean isDeleted;

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
