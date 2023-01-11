package apshomebe.caregility.com.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import apshomebe.caregility.com.models.Vin;
import apshomebe.caregility.com.payload.VinResponce;

@Repository
public interface VinRepository extends MongoRepository<Vin, String> {
	Boolean existsByVinNumber(String vinNumber);
	Optional<Vin> findByVinNumber(String vinNumber);
	Boolean existsByAuthMachine_MachineName(String machineName);
	Optional<Vin> findByIsDeletedAndAuthMachine_MachineName(Boolean isDeleted,String machineName);
    Page<VinResponce>findAllByIsDeletedAndAuthMachineIsNotNull(Boolean isDeleted,Pageable pageable);
}
