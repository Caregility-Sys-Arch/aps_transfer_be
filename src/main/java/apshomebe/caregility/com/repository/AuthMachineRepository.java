package apshomebe.caregility.com.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import apshomebe.caregility.com.models.AuthMachine;

@Repository
public interface AuthMachineRepository extends MongoRepository<AuthMachine, String> {
	Optional<AuthMachine> findByMachineName(String machineName);

	Boolean existsByMachineName(String machineName);

}
