package uk.com.poodle.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuardianRepository extends CrudRepository<GuardianEntity, String> {
}
