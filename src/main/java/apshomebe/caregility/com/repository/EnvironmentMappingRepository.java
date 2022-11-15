package apshomebe.caregility.com.repository;

import apshomebe.caregility.com.models.EnvironmentMapping;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnvironmentMappingRepository extends MongoRepository<EnvironmentMapping,String> {
}
