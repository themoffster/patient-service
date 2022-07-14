package uk.com.poodle.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends CrudRepository<PatientEntity, String> {

    List<PatientEntity> findAllByEducationEstablishmentId(String id);
}
