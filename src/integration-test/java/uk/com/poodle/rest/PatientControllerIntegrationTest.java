package uk.com.poodle.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import uk.com.poodle.domain.Patient;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static uk.com.poodle.Constants.PATIENT_ID;
import static uk.com.poodle.domain.DomainDataFactory.buildAddPatientParams;
import static uk.com.poodle.domain.DomainDataFactory.buildPatient;

class PatientControllerIntegrationTest extends AbstractControllerIntegrationTest {

    @Test
    void shouldAddPatient() throws JsonProcessingException, JSONException {
        var payload = buildAddPatientParams();
        var responseEntity = restTemplate.postForEntity("/patients/add", payload, Patient.class);

        assertEquals(CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getId());
        JSONAssert.assertEquals(
            mapper.writeValueAsString(buildPatient()),
            mapper.writeValueAsString(responseEntity.getBody()),
            new CustomComparator(
                JSONCompareMode.STRICT,
                new Customization("id", (o1, o2) -> true),
                new Customization("address.id", (o1, o2) -> true)));
    }

    @TestWithData
    void shouldRetrieveAllPatients() {
        var urlTemplate = "/patients";
        var responseEntity = restTemplate.getForEntity(urlTemplate, Patient[].class);

        assertEquals(OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(1, responseEntity.getBody().length);
        assertEquals(buildPatient(), responseEntity.getBody()[0]);
    }

    @TestWithData
    void shouldRetrievePatient() {
        var urlTemplate = "/patients/{patientId}";
        var responseEntity = restTemplate.getForEntity(urlTemplate, Patient.class, Map.of("patientId", PATIENT_ID));

        assertEquals(OK, responseEntity.getStatusCode());
        assertEquals(buildPatient(), responseEntity.getBody());
    }
}
