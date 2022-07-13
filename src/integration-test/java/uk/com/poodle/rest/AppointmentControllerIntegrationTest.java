package uk.com.poodle.rest;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.com.poodle.config.EmbeddedDatabaseTestConfig;
import uk.com.poodle.domain.Appointment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY;
import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.RefreshMode.AFTER_EACH_TEST_METHOD;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static uk.com.poodle.Constants.APPOINTMENT_DATE_TIME;
import static uk.com.poodle.Constants.APPOINTMENT_ID;
import static uk.com.poodle.Constants.PATIENT_ID;
import static uk.com.poodle.domain.DomainDataFactory.buildAddAppointmentParams;

@AutoConfigureWireMock(port = 0)
@ExtendWith(SpringExtension.class)
@Import(EmbeddedDatabaseTestConfig.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureEmbeddedDatabase(provider = ZONKY, refresh = AFTER_EACH_TEST_METHOD)
class AppointmentControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @TestWithData
    void shouldAddAppointment() {
        var payload = buildAddAppointmentParams();
        var responseEntity = restTemplate.postForEntity("/patients/{patientId}/appointments/add", payload, Appointment.class, Map.of("patientId", PATIENT_ID));

        assertEquals(CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getId());
        assertEquals(APPOINTMENT_DATE_TIME, responseEntity.getBody().getDateTime());
        assertEquals(PATIENT_ID, responseEntity.getBody().getPatientId());
    }

    @TestWithData
    void shouldRetrieveAllAppointmentsForPatient() {
        var urlTemplate = "/patients/{patientId}/appointments?includeHistoric=true";
        var responseEntity = restTemplate.getForEntity(urlTemplate, Appointment[].class, Map.of("patientId", PATIENT_ID));

        var expected = List.of(
            Appointment.builder()
                .id(APPOINTMENT_ID)
                .patientId(PATIENT_ID)
                .dateTime(APPOINTMENT_DATE_TIME)
                .build(),
            Appointment.builder()
                .id("cf6c5228-31b4-411d-9cb7-f6ec73b7fee8")
                .patientId(PATIENT_ID)
                .dateTime(LocalDateTime.of(2022, 1, 1, 9, 0))
                .build()
        );

        assertEquals(OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(2, responseEntity.getBody().length);
        assertTrue(expected.containsAll(asList(responseEntity.getBody())));
    }

    @TestWithData
    void shouldRetrieveAllUpcomingAppointmentsForPatient() {
        var urlTemplate = "/patients/{patientId}/appointments";
        var responseEntity = restTemplate.getForEntity(urlTemplate, Appointment[].class, Map.of("patientId", PATIENT_ID));

        var expected = Appointment.builder()
            .id(APPOINTMENT_ID)
            .patientId(PATIENT_ID)
            .dateTime(APPOINTMENT_DATE_TIME)
            .build();

        assertEquals(OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(1, responseEntity.getBody().length);
        assertEquals(expected, responseEntity.getBody()[0]);
    }
}
