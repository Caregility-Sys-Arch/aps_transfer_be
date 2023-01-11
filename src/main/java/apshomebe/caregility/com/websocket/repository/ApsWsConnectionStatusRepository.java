package apshomebe.caregility.com.websocket.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import apshomebe.caregility.com.websocket.model.ApsWsConnectionStatus;

@Repository
public interface ApsWsConnectionStatusRepository extends MongoRepository<ApsWsConnectionStatus, String> {
	@Query("{current_socket_id :?0}")
	public Optional<ApsWsConnectionStatus> findApsMachineNameBySocketId(String socketId);

	@Query("{current_machine_name :?0, status:'WS_CONNECTED'}")
	public Optional<ApsWsConnectionStatus> findSocketIdByApsMachineName(String apsMachineName);

	@Query("{current_machine_name :?0}")
	public Optional<ApsWsConnectionStatus> findByApsMachineName(String apsMachineName);

	public List<ApsWsConnectionStatus> findAllByStatus(String status);


}