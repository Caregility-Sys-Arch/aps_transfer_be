package apshomebe.caregility.com.repository;

import apshomebe.caregility.com.models.ApsTransfer;
import apshomebe.caregility.com.models.ApsTransferCopy;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApsTransferCopyRepository  extends MongoRepository<ApsTransferCopy,String> {

//
ApsTransferCopy findByParams_FromMachineNameAndParams_SessionId(String fromMachineName,String sessionId);


}
