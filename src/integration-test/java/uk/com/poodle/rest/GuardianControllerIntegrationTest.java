package uk.com.poodle.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.json.JSONException;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.com.poodle.config.EmbeddedDatabaseTestConfig;
import uk.com.poodle.domain.Guardian;

import java.util.Map;

import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY;
import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.RefreshMode.AFTER_EACH_TEST_METHOD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.skyscreamer.jsonassert.JSONCompareMode.STRICT;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static uk.com.poodle.Constants.PATIENT_ID;
import static uk.com.poodle.domain.DomainDataFactory.buildAddGuardianDetailsParams;
import static uk.com.poodle.domain.DomainDataFactory.buildGuardian;

@AutoConfigureWireMock(port = 0)
@ExtendWith(SpringExtension.class)
@Import(EmbeddedDatabaseTestConfig.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureEmbeddedDatabase(provider = ZONKY, refresh = AFTER_EACH_TEST_METHOD)
class GuardianControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper mapper;

    @TestWithData
    void shouldAddGuardianToPatient() throws JsonProcessingException, JSONException {
        var payload = buildAddGuardianDetailsParams();
        var responseEntity = restTemplate.postForEntity("/patients/{patientId}/guardians/add", payload, Guardian.class, Map.of("patientId", PATIENT_ID));

        assertEquals(CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getId());
        JSONAssert.assertEquals(
            mapper.writeValueAsString(buildGuardian()),
            mapper.writeValueAsString(responseEntity.getBody()),
            new CustomComparator(
                STRICT,
                new Customization("id", (o1, o2) -> true),
                new Customization("contactDetails.id", (o1, o2) -> true)));
    }

    @TestWithData
    void shouldGetGuardiansForPatient() throws JsonProcessingException, JSONException {
        var responseEntity = restTemplate.getForEntity("/patients/{patientId}/guardians", Guardian[].class, Map.of("patientId", PATIENT_ID));

        assertEquals(OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(1, responseEntity.getBody().length);
        var guardian = responseEntity.getBody()[0];
        assertNotNull(guardian.getId());
        JSONAssert.assertEquals(
            mapper.writeValueAsString(buildGuardian()),
            mapper.writeValueAsString(guardian),
            new CustomComparator(
                JSONCompareMode.STRICT,
                new Customization("id", (o1, o2) -> true),
                new Customization("contactDetails.id", (o1, o2) -> true)));
    }
}
