package uk.com.poodle;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
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
import static org.springframework.http.HttpStatus.OK;

@AutoConfigureWireMock(port = 0)
@ExtendWith(SpringExtension.class)
@Import(EmbeddedDatabaseTestConfig.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureEmbeddedDatabase(provider = ZONKY, refresh = AFTER_EACH_TEST_METHOD)
class PatientControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @TestWithData
    void shouldRetrievePatient() {
        String urlTemplate = "/patients/8674faf7-c2f8-4ba8-8aa8-11d95066610b";
        ResponseEntity<Patient> responseEntity = restTemplate.getForEntity(urlTemplate, Patient.class);

        Patient expected = Patient.builder()
            .id("8674faf7-c2f8-4ba8-8aa8-11d95066610b")
            .firstname("Joe")
            .lastname("Bloggs")
            .build();

        assertEquals(OK, responseEntity.getStatusCode());
        assertEquals(expected, responseEntity.getBody());
    }
}
