package uk.com.poodle.data;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.com.poodle.data.EntityDataFactory.buildGuardianEntity;
import static uk.com.poodle.data.EntityDataFactory.buildPatientEntity;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@AutoConfigureEmbeddedDatabase(provider = ZONKY)
class GuardianRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private GuardianRepository guardianRepository;

    @Test
    void shouldFindAllByPatientId() {
        var savedPatient = patientRepository.save(buildPatientEntity());
        var guardian = guardianRepository.save(buildGuardianEntity().withPatientId(savedPatient.getId()));

        var guardians = guardianRepository.findAllByPatientId(savedPatient.getId());

        assertEquals(1, guardians.size());
        assertEquals(guardian, guardians.get(0));
    }
}
