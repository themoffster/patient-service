package uk.com.poodle.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.auditing.DateTimeProvider;
import uk.com.poodle.data.AppointmentRepository;
import uk.com.poodle.data.EntityDataFactory;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static uk.com.poodle.Constants.APPOINTMENT_DATE_TIME;
import static uk.com.poodle.Constants.APPOINTMENT_ID;
import static uk.com.poodle.Constants.PATIENT_ID;
import static uk.com.poodle.data.EntityDataFactory.buildAppointmentEntity;
import static uk.com.poodle.domain.DomainDataFactory.buildAppointment;
import static uk.com.poodle.domain.DomainDataFactory.buildCreateAppointmentParams;
import static uk.com.poodle.domain.DomainDataFactory.buildPatient;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @Mock
    private AppointmentRepository mockRepository;

    @Mock
    private DateTimeProvider mockDateTimeProvider;

    @Mock
    private PatientService mockPatientService;

    @InjectMocks
    private AppointmentService service;

    @Test
    void shouldCreateAppointment() {
        var expected = buildAppointment();
        var entity = EntityDataFactory.buildAppointmentEntity();
        var params = buildCreateAppointmentParams();
        when(mockPatientService.getPatient(PATIENT_ID)).thenReturn(Optional.of(buildPatient()));
        when(mockRepository.save(entity)).thenReturn(entity.withId(APPOINTMENT_ID));

        var actual = service.createAppointment(params);

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionCreatingAppointmentIfPatientNotFound() {
        when(mockPatientService.getPatient(PATIENT_ID)).thenReturn(Optional.empty());

        var thrown = assertThrows(IllegalArgumentException.class, () -> service.createAppointment(buildCreateAppointmentParams()));

        assertEquals("Patient not found.", thrown.getMessage());
        verifyNoInteractions(mockRepository);
    }

    @Test
    void shouldRetrieveAllAppointments() {
        when(mockRepository.findAllByPatientId(PATIENT_ID)).thenReturn(List.of(buildAppointmentEntity(APPOINTMENT_ID)));

        var patients = service.getAppointments(PATIENT_ID, true);

        assertEquals(1, patients.size());
        assertEquals(buildAppointment(), patients.get(0));
    }

    @Test
    void shouldRetrieveAllUpcomingAppointments() {
        when(mockDateTimeProvider.getNow()).thenReturn(Optional.of(APPOINTMENT_DATE_TIME));
        when(mockRepository.findAllByPatientIdAndDateTimeGreaterThanEqual(PATIENT_ID, APPOINTMENT_DATE_TIME)).thenReturn(List.of(buildAppointmentEntity(APPOINTMENT_ID)));

        var patients = service.getAppointments(PATIENT_ID, false);

        assertEquals(1, patients.size());
        assertEquals(buildAppointment(), patients.get(0));
    }
}
