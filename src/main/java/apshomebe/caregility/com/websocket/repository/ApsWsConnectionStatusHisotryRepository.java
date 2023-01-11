package apshomebe.caregility.com.websocket.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import apshomebe.caregility.com.websocket.model.ApsWsConnectionStatusHistory;

@Repository
public interface ApsWsConnectionStatusHisotryRepository extends MongoRepository<ApsWsConnectionStatusHistory, String> {

}