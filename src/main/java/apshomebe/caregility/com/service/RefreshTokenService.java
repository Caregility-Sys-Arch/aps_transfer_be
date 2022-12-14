package apshomebe.caregility.com.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import apshomebe.caregility.com.models.UserDetailsInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import apshomebe.caregility.com.exception.TokenRefreshException;
import apshomebe.caregility.com.models.RefreshToken;
import apshomebe.caregility.com.repository.AuthMachineRepository;
import apshomebe.caregility.com.repository.RefreshTokenRepository;

@Service
public class RefreshTokenService {
  @Value("${aps.app.jwtRefreshExpirationMs}")
  private Long refreshTokenDurationMs;
  @Autowired
  private RefreshTokenRepository refreshTokenRepository;
 
  @Autowired
  private AuthMachineRepository authMachineRepository;
  public Optional<RefreshToken> findByToken(String token) {
    return refreshTokenRepository.findByToken(token);
  }


  public RefreshToken createRefreshToken(CustomUserAuthDetails userInfo) {
    RefreshToken refreshToken = new RefreshToken();
    UserDetailsInfo user=new UserDetailsInfo(userInfo.getAdminId(),userInfo.getEnvironmentUri(),userInfo.getEmail());
    //refreshToken.setUser(userRepository.findById(userId).get());
    refreshToken.setUser(user);
//     refreshToken.setAuthMachine(authMachineRepository.findById(userId).get());

    refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
    refreshToken.setToken(UUID.randomUUID().toString());
    refreshToken = refreshTokenRepository.save(refreshToken);
    return refreshToken;
  }

  public RefreshToken verifyExpiration(RefreshToken token) {
    if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
      refreshTokenRepository.delete(token);
      throw new TokenRefreshException( "Refresh Token Was Expired. Please Make a New Sign In Request");
    }
    return token;
  }
  @Transactional
  public int deleteByUserId(String userId) {
	  
    return refreshTokenRepository.deleteByAuthMachine(authMachineRepository.findById(userId).get());
  }
}