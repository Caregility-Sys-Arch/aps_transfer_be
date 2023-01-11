package apshomebe.caregility.com.controllers;

import apshomebe.caregility.com.payload.JwtResponse;
import apshomebe.caregility.com.payload.TokenRefreshRequest;
import apshomebe.caregility.com.payload.TokenRefreshResponse;
import apshomebe.caregility.com.payload.VerifyTokenRequest;
import apshomebe.caregility.com.service.AuthServiceImpl;
import apshomebe.caregility.com.service.RefreshTokenService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;

@RestController
@Log4j2
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    AuthServiceImpl authService;

    @Autowired
    RefreshTokenService refreshTokenService;


    @PostMapping("/verify")
    public   JwtResponse verifyToken(@Valid @RequestBody VerifyTokenRequest verifyTokenRequest) throws SQLException {
        log.info("inside the verify controller");
        JwtResponse response=authService.verifyToken(verifyTokenRequest);
        log.warn("Return back from controller");
        return response;
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenRefreshResponse> refreshToken(@RequestBody TokenRefreshRequest request) {

        return authService.generateAccessTokenFromRefreshToken(request);
    }
    @PostMapping("/hi")
    public ResponseEntity<?> verifyTokenHi(@RequestBody VerifyTokenRequest verifyTokenRequest) throws SQLException {
        log.info("inside the verify controller");
        return ResponseEntity.ok("hi patya bhai");
    }

    @GetMapping("/hii")
    public ResponseEntity<?> verifyTokenHiString() throws SQLException {
        log.info("inside the verify controller");
        return ResponseEntity.ok("hi patya bhai");
    }

}
