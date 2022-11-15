package apshomebe.caregility.com.repository;

import apshomebe.caregility.com.models.ApsTransfer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApsTransferRepository extends MongoRepository<ApsTransfer,String> {

    public ApsTransfer findByFromMachineNameAndSocketId(String fromMachineName,String socketId);
}
