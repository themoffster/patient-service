package uk.com.poodle.data;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static uk.com.poodle.data.EntityDataFactory.buildNewPatientEntity;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@AutoConfigureEmbeddedDatabase(provider = ZONKY)
class PatientRepositoryTest {

    @Autowired
    private PatientRepository repository;

    @Test
    void shouldFindByPatientId() {
        var entity = buildNewPatientEntity();
        repository.save(entity);

        var actual = repository.findById(entity.getId());

        assertTrue(actual.isPresent());
        assertEquals(entity, actual.get());
    }
}
