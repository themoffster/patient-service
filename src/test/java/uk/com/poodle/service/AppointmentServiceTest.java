package uk.com.poodle.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.auditing.DateTimeProvider;
import uk.com.poodle.data.AppointmentRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static uk.com.poodle.Constants.APPOINTMENT_DATE_TIME;
import static uk.com.poodle.Constants.APPOINTMENT_ID;
import static uk.com.poodle.Constants.PATIENT_ID;
import static uk.com.poodle.data.EntityDataFactory.buildNewAppointmentEntity;
import static uk.com.poodle.domain.DomainDataFactory.buildNewAppointment;
import static uk.com.poodle.domain.DomainDataFactory.buildNewCreateAppointmentParams;
import static uk.com.poodle.domain.DomainDataFactory.buildNewPatient;

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
        var expected = buildNewAppointment();
        var entity = buildNewAppointmentEntity();
        var params = buildNewCreateAppointmentParams();
        when(mockPatientService.getPatient(PATIENT_ID)).thenReturn(Optional.of(buildNewPatient()));
        when(mockRepository.save(entity)).thenReturn(entity.withId(APPOINTMENT_ID));

        var actual = service.createAppointment(params);

        assertEquals(expected, actual);
    }

    @Test
    void shouldRetrieveAllAppointments() {
        when(mockRepository.findAllByPatientId(PATIENT_ID)).thenReturn(List.of(buildNewAppointmentEntity(APPOINTMENT_ID)));

        var patients = service.getAppointments(PATIENT_ID, true);

        assertEquals(1, patients.size());
        assertEquals(buildNewAppointment(), patients.get(0));
    }

    @Test
    void shouldRetrieveAllUpcomingAppointments() {
        when(mockDateTimeProvider.getNow()).thenReturn(Optional.of(APPOINTMENT_DATE_TIME));
        when(mockRepository.findAllByPatientIdAndDateTimeGreaterThanEqual(PATIENT_ID, APPOINTMENT_DATE_TIME)).thenReturn(List.of(buildNewAppointmentEntity(APPOINTMENT_ID)));

        var patients = service.getAppointments(PATIENT_ID, false);

        assertEquals(1, patients.size());
        assertEquals(buildNewAppointment(), patients.get(0));
    }
}
