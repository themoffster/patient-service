package uk.com.poodle.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.com.poodle.data.PatientRepository;
import uk.com.poodle.domain.Patient;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static uk.com.poodle.Constants.PATIENT_ID;
import static uk.com.poodle.data.EntityDataFactory.buildNewPatientEntity;
import static uk.com.poodle.domain.DomainDataFactory.buildNewPatient;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository mockRepository;

    @InjectMocks
    private PatientService service;

    @Test
    void shouldRetrievePatient() {
        when(mockRepository.findById(PATIENT_ID)).thenReturn(Optional.of(buildNewPatientEntity(PATIENT_ID)));

        Optional<Patient> optional = service.getPatient(PATIENT_ID);

        optional.ifPresentOrElse(patient -> assertEquals(buildNewPatient(), patient), Assertions::fail);;
    }

    @Test
    void shouldReturnEmptyOptionalWhenUnableToRetrievePatient() {
        when(mockRepository.findById(PATIENT_ID)).thenReturn(Optional.empty());

        Optional<Patient> patientOptional = service.getPatient(PATIENT_ID);

        assertTrue(patientOptional.isEmpty());
    }
}
