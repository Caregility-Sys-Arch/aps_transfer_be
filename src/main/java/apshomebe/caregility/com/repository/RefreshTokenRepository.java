package apshomebe.caregility.com.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import apshomebe.caregility.com.models.AuthMachine;
import apshomebe.caregility.com.models.RefreshToken;

public interface RefreshTokenRepository extends MongoRepository<RefreshToken,String>{
	@Override
	Optional<RefreshToken> findById(String id);
	Optional<RefreshToken> findByToken(String token);
	int deleteByAuthMachine(AuthMachine authMachine );

}
