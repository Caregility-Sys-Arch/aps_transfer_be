package apshomebe.caregility.com.service;

import apshomebe.caregility.com.exception.TokenRefreshException;
import apshomebe.caregility.com.models.Environment;
import apshomebe.caregility.com.models.RefreshToken;
import apshomebe.caregility.com.payload.JwtResponse;
import apshomebe.caregility.com.payload.TokenRefreshRequest;
import apshomebe.caregility.com.payload.TokenRefreshResponse;
import apshomebe.caregility.com.payload.VerifyTokenRequest;
import apshomebe.caregility.com.repository.EnvironmentRepository;
import apshomebe.caregility.com.security.JwtAuthUtils;
import lombok.extern.java.Log;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Base64;
import java.util.Optional;

@Service
@Log
public class AuthServiceImpl {

    @Autowired
    EnvironmentRepository environmentRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    JwtAuthUtils authDetails;

    public JwtResponse verifyToken(VerifyTokenRequest request) throws SQLException {
        String[] parts = request.getAccessToken().split("\\.");
        JSONObject header = new JSONObject(decode(parts[0]));
        JSONObject payload = new JSONObject(decode(parts[1]));
        String userEmail = payload.getString("email");


        //TODO check the access of this user to users_to_apshome table

        //check if the given environment is in database or not
        boolean envExists = isEnvironmentExists(request.getEnvironmentUrl());

        // if the environment exists then go to the env database and check if user exists and generate  JWT token
        if (envExists) {

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userEmail + "]" + request.getEnvironmentUrl(), request.getEnvironmentUrl()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            CustomUserAuthDetails customUserAuthDetails = (CustomUserAuthDetails) authentication.getPrincipal();
            String jwt = authDetails.generateJwtToken(customUserAuthDetails);
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(customUserAuthDetails);
            JwtResponse response = new JwtResponse(jwt, refreshToken.getToken());
            return response;

        } else {
            return null;
        }

    }


    public ResponseEntity<TokenRefreshResponse> generateAccessTokenFromRefreshToken(TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken).map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser).map(user -> {
                    String token = authDetails.generateTokenFromUsernameAndEnvironmentUrl(user.getEmail(), user.getEnvironmentUri());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException( "Refresh token is invalid !"));

    }

    private static String decode(String encodedString) {
        return new String(Base64.getUrlDecoder().decode(encodedString));
    }

    private boolean isEnvironmentExists(String environmentName) {
        Optional<Environment> environment = environmentRepository.findByEnvironmentUrl(environmentName);
        if (environment.isPresent())
            return true;
        return false;
    }
}
