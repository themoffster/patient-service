package uk.com.poodle;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.com.poodle.config.EmbeddedDatabaseTestConfig;
import uk.com.poodle.domain.Patient;

import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY;
import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.RefreshMode.AFTER_EACH_TEST_METHOD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static uk.com.poodle.Constants.PATIENT_FIRSTNAME;
import static uk.com.poodle.Constants.PATIENT_ID;
import static uk.com.poodle.Constants.PATIENT_LASTNAME;
import static uk.com.poodle.domain.DomainDataFactory.buildNewCreatePatientParams;

@AutoConfigureWireMock(port = 0)
@ExtendWith(SpringExtension.class)
@Import(EmbeddedDatabaseTestConfig.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureEmbeddedDatabase(provider = ZONKY, refresh = AFTER_EACH_TEST_METHOD)
class PatientControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @TestWithData
    void shouldRetrieveAllPatients() {
        String urlTemplate = "/patients";
        ResponseEntity<Patient[]> responseEntity = restTemplate.getForEntity(urlTemplate, Patient[].class);

        Patient expected = Patient.builder()
            .id(PATIENT_ID)
            .firstname("Joe")
            .lastname("Bloggs")
            .build();

        assertEquals(OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(1, responseEntity.getBody().length);
        assertEquals(expected, responseEntity.getBody()[0]);
    }

    @TestWithData
    void shouldRetrievePatient() {
        String urlTemplate = "/patients/" + PATIENT_ID;
        ResponseEntity<Patient> responseEntity = restTemplate.getForEntity(urlTemplate, Patient.class);

        Patient expected = Patient.builder()
            .id(PATIENT_ID)
            .firstname(PATIENT_FIRSTNAME)
            .lastname(PATIENT_LASTNAME)
            .build();

        assertEquals(OK, responseEntity.getStatusCode());
        assertEquals(expected, responseEntity.getBody());
    }

    @Test
    void shouldCreatePatient() {
        var payload = buildNewCreatePatientParams();
        ResponseEntity<Patient> responseEntity = restTemplate.postForEntity("/patients/create", payload, Patient.class);

        assertEquals(CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getId());
        assertEquals(PATIENT_FIRSTNAME, responseEntity.getBody().getFirstname());
        assertEquals(PATIENT_LASTNAME, responseEntity.getBody().getLastname());
    }
}
