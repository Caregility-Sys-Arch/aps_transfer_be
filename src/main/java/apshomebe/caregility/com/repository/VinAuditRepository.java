package apshomebe.caregility.com.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import apshomebe.caregility.com.models.VinAudit;
import apshomebe.caregility.com.payload.VinResponce;

public interface VinAuditRepository extends MongoRepository<VinAudit, String> {

	List<VinResponce> findAllByVinNumberAndAuthMachineIsNotNull(String vinNumber);
}
