package apshomebe.caregility.com.repository;

import apshomebe.caregility.com.models.Environment;
import apshomebe.caregility.com.payload.EnvironmentResList;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnvironmentRepository extends MongoRepository<Environment,String> {
    @Query("select e.id, e.name,e.environmentIpAddress from Environment e")
    List<EnvironmentResList> findAllByIdAndNameAndEnvironmentIp();
      Optional<Environment> findByEnvironmentUrl(String url);
}

