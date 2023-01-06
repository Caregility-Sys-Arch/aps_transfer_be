	package apshomebe.caregility.com.security;

import java.security.SignatureException;
import java.util.Date;

import apshomebe.caregility.com.service.AuthDetailsImpl;
import apshomebe.caregility.com.service.CustomUserAuthDetails;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class JwtAuthUtils {
	private static final Logger logger = LoggerFactory.getLogger(JwtAuthUtils.class);

	@Value("${aps.app.jwtSecret}")
	private String jwtSecret;
	@Value("${aps.app.jwtExpirationMs}")
	private int jwtExpirationMs;

	public String generateJwtToken( CustomUserAuthDetails userPrincipal) {
		return generateTokenFromUsername(  userPrincipal);
	}

	public String generateTokenFromUsername(CustomUserAuthDetails userPrincipal) {
		return Jwts.builder().
				setSubject(userPrincipal.getUsername()
				).setIssuedAt(new Date())
				.setIssuer(userPrincipal.getEnvironmentUri())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}
	public String generateTokenFromUsernameAndEnvironmentUrl(String userName,String environmentUrl) {

		return Jwts.builder().
				setSubject(userName
				).setIssuedAt(new Date())
				.setIssuer(environmentUrl)
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

	public String getUserNameFromJwtToken(String token) {

		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}
	public String getEnvironmentFromJwtToken(String token) {

		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getIssuer();
	}

	public boolean 	validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}
		return false;
	}

}
