package uk.com.poodle.data;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static uk.com.poodle.Constants.APPOINTMENT_DATE_TIME;
import static uk.com.poodle.data.EntityDataFactory.buildNewAppointmentEntity;
import static uk.com.poodle.data.EntityDataFactory.buildNewPatientEntity;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@AutoConfigureEmbeddedDatabase(provider = ZONKY)
class AppointmentRepositoryTest {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Mock
    private DateTimeProvider mockDateTimeProvider;

    private String patientId;

    @BeforeEach
    void insertPatient() {
        var savedPatient = patientRepository.save(buildNewPatientEntity());
        patientId = savedPatient.getId();
    }

    @Test
    void shouldFindAllAppointmentsByPatientId() {
        var entity = buildNewAppointmentEntity().withPatientId(patientId);
        appointmentRepository.save(entity);

        var appointments = appointmentRepository.findAllByPatientId(patientId);

        assertEquals(1, appointments.size());
        assertTrue(appointments.contains(entity));
    }

    @Test
    void shouldFindAllUpcomingAppointmentsByPatientId() {
        var historicAppointmentEntity = buildNewAppointmentEntity().withPatientId(patientId).toBuilder()
            .dateTime(LocalDateTime.of(2022, 1, 1, 9, 0))
            .build();
        var upcomingAppointmentEntity = buildNewAppointmentEntity().withPatientId(patientId);
        appointmentRepository.saveAll(List.of(historicAppointmentEntity, upcomingAppointmentEntity));
        when(mockDateTimeProvider.getNow()).thenReturn(Optional.of(APPOINTMENT_DATE_TIME));

        var appointments = appointmentRepository.findAllByPatientIdAndDateTimeGreaterThanEqual(patientId, APPOINTMENT_DATE_TIME);

        assertEquals(1, appointments.size());
        assertEquals(upcomingAppointmentEntity, appointments.get(0));
    }
}
