package apshomebe.caregility.com.models;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "refreshtoken")
public class RefreshToken {
	@Id
	private String id;
	private AuthMachine authMachine;

	private String token;
	private Instant expiryDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public AuthMachine getAuthMachine() {
		return authMachine;
	}

	public void setAuthMachine(AuthMachine authMachine) {
		this.authMachine = authMachine;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Instant getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Instant expiryDate) {
		this.expiryDate = expiryDate;
	}

	@Override
	public String toString() {
		return "RefreshToken [id=" + id  + ", authMachine=" + authMachine + ", token=" + token
				+ ", expiryDate=" + expiryDate + "]";
	}

}