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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static uk.com.poodle.Constants.APPOINTMENT_DATE_TIME;
import static uk.com.poodle.data.EntityDataFactory.buildAppointmentEntity;
import static uk.com.poodle.data.EntityDataFactory.buildEducationEstablishmentEntity;
import static uk.com.poodle.data.EntityDataFactory.buildPatientEntity;
import static uk.com.poodle.data.EntityDataFactory.buildRelationEntity;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@AutoConfigureEmbeddedDatabase(provider = ZONKY)
class AppointmentRepositoryTest {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private RelationRepository relationRepository;

    @Autowired
    private EducationEstablishmentRepository educationEstablishmentRepository;

    @Mock
    private DateTimeProvider mockDateTimeProvider;

    private EducationEstablishmentEntity educationEstablishmentEntity;
    private String patientId;

    @BeforeEach
    void setup() {
        insertEducationEstablishment();
        insertRelation();
        insertPatient();
    }

    void insertPatient() {
        var savedPatient = patientRepository.save(buildPatientEntity().withEducationEstablishment(educationEstablishmentEntity));
        patientId = savedPatient.getId();
    }

    void insertRelation() {
        relationRepository.save(buildRelationEntity());
    }

    void insertEducationEstablishment() {
        educationEstablishmentEntity = educationEstablishmentRepository.save(buildEducationEstablishmentEntity());
    }

    @Test
    void shouldFindAllAppointmentsByPatientId() {
        var entity = buildAppointmentEntity().withPatientId(patientId);
        appointmentRepository.save(entity);

        var appointments = appointmentRepository.findAllByPatientId(patientId);

        assertEquals(1, appointments.size());
        assertTrue(appointments.contains(entity));
    }

    @Test
    void shouldFindAppointmentByIdAndPatientId() {
        var entity = buildAppointmentEntity().withPatientId(patientId);
        appointmentRepository.save(entity);

        var appointment = appointmentRepository.findByIdAndPatientId(entity.getId(), patientId).orElseThrow();

        assertNotNull(appointment);
        assertEquals(entity, appointment);
    }

    @Test
    void shouldFindAllUpcomingAppointmentsByPatientId() {
        var historicAppointmentEntity = buildAppointmentEntity().withPatientId(patientId).toBuilder()
            .dateTime(LocalDateTime.of(2022, 1, 1, 9, 0))
            .build();
        var upcomingAppointmentEntity = buildAppointmentEntity().withPatientId(patientId);
        appointmentRepository.saveAll(List.of(historicAppointmentEntity, upcomingAppointmentEntity));
        when(mockDateTimeProvider.getNow()).thenReturn(Optional.of(APPOINTMENT_DATE_TIME));

        var appointments = appointmentRepository.findAllByPatientIdAndDateTimeGreaterThanEqual(patientId, APPOINTMENT_DATE_TIME);

        assertEquals(1, appointments.size());
        assertEquals(upcomingAppointmentEntity, appointments.get(0));
    }

    @Test
    void shouldFindAllByDateTimeAfter() {
        var appointment = buildAppointmentEntity().withPatientId(patientId);
        appointmentRepository.save(appointment);

        var appointments = appointmentRepository.findAllByDateTimeAfter(APPOINTMENT_DATE_TIME.minusDays(1L));

        assertEquals(1, appointments.size());
        assertEquals(appointment, appointments.get(0));
    }
}
