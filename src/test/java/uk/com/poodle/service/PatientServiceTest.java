package uk.com.poodle.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.com.poodle.data.EntityDataFactory;
import uk.com.poodle.data.PatientRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static uk.com.poodle.Constants.PATIENT_ID;
import static uk.com.poodle.data.EntityDataFactory.buildPatientEntity;
import static uk.com.poodle.domain.DomainDataFactory.buildCreatePatientParams;
import static uk.com.poodle.domain.DomainDataFactory.buildPatient;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository mockRepository;

    @InjectMocks
    private PatientService service;

    @Test
    void shouldCreatePatient() {
        var expected = buildPatient();
        var entity = EntityDataFactory.buildPatientEntity();
        var params = buildCreatePatientParams();
        when(mockRepository.save(entity)).thenReturn(entity.withId(PATIENT_ID));

        var actual = service.createPatient(params);

        assertEquals(expected, actual);
    }

    @Test
    void shouldRetrieveAllPatients() {
        when(mockRepository.findAll()).thenReturn(List.of(buildPatientEntity(PATIENT_ID)));

        var patients = service.getAllPatients();

        assertEquals(1, patients.size());
        assertEquals(buildPatient(), patients.get(0));
    }

    @Test
    void shouldRetrievePatient() {
        when(mockRepository.findById(PATIENT_ID)).thenReturn(Optional.of(buildPatientEntity(PATIENT_ID)));

        var optional = service.getPatient(PATIENT_ID);

        optional.ifPresentOrElse(patient -> assertEquals(buildPatient(), patient), Assertions::fail);
    }

    @Test
    void shouldReturnEmptyOptionalWhenUnableToRetrievePatient() {
        when(mockRepository.findById(PATIENT_ID)).thenReturn(Optional.empty());

        var patientOptional = service.getPatient(PATIENT_ID);

        assertTrue(patientOptional.isEmpty());
    }
}
