package uk.com.poodle.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuardianRepository extends CrudRepository<GuardianEntity, String> {

    List<GuardianEntity> findAllByPatientId(String patientId);
}
