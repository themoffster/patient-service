package uk.com.poodle.rest;

import uk.com.poodle.domain.Appointment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static uk.com.poodle.Constants.APPOINTMENT_DATE_TIME;
import static uk.com.poodle.Constants.APPOINTMENT_ID;
import static uk.com.poodle.Constants.APPOINTMENT_NOTES;
import static uk.com.poodle.Constants.PATIENT_ID;
import static uk.com.poodle.domain.DomainDataFactory.buildAddAppointmentNotesParams;
import static uk.com.poodle.domain.DomainDataFactory.buildAddAppointmentParams;

class AppointmentControllerIntegrationTest extends AbstractControllerIntegrationTest {

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
    void shouldAddAppointmentNotes() {
        var urlTemplate = "/patients/{patientId}/appointments/{appointmentId}/notes/add";
        var payload = buildAddAppointmentNotesParams();
        var urlParams = Map.of(
            "patientId", PATIENT_ID,
            "appointmentId", APPOINTMENT_ID
        );
        var responseEntity = restTemplate.postForEntity(urlTemplate, payload, Appointment.class, urlParams);

        var expected = Appointment.builder()
            .id(APPOINTMENT_ID)
            .dateTime(APPOINTMENT_DATE_TIME)
            .notes(APPOINTMENT_NOTES)
            .patientId(PATIENT_ID)
            .build();

        assertEquals(CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(expected, responseEntity.getBody());
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
