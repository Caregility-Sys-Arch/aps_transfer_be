package apshomebe.caregility.com.repository;

import apshomebe.caregility.com.models.Environment;
import apshomebe.caregility.com.payload.EnvironmentResList;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnvironmentRepository extends MongoRepository<Environment,String> {
    @Query("select e.id, e.name from Environment e")
    List<EnvironmentResList> findAllByIdAndName();
}

