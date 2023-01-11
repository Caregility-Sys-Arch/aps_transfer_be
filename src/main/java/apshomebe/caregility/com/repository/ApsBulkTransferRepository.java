package apshomebe.caregility.com.repository;

import apshomebe.caregility.com.models.ApsBulkTransfer;
import apshomebe.caregility.com.models.ApsTransfer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApsBulkTransferRepository extends MongoRepository<ApsBulkTransfer,String> {
}
