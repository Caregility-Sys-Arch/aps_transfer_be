package apshomebe.caregility.com.payload;

import java.util.List;

public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private String refreshToken;

	@Override
	public String toString() {
		return "JwtResponse{" +
				"token='" + token + '\'' +
				", type='" + type + '\'' +
				", refreshToken='" + refreshToken + '\'' +
				'}';
	}

	public JwtResponse(String accessToken, String refreshToken) {
		this.token = accessToken;
		this.refreshToken = refreshToken;
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}


	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

}