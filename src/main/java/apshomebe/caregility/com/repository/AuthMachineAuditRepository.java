package apshomebe.caregility.com.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import apshomebe.caregility.com.models.AuthMachineAudit;

public interface AuthMachineAuditRepository extends MongoRepository<AuthMachineAudit, String> {

}
