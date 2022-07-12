package uk.com.poodle.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.com.poodle.data.GuardianRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static uk.com.poodle.Constants.GUARDIAN_ID;
import static uk.com.poodle.Constants.PATIENT_ID;
import static uk.com.poodle.data.EntityDataFactory.buildGuardianEntity;
import static uk.com.poodle.domain.DomainDataFactory.buildAddGuardianDetailsParams;
import static uk.com.poodle.domain.DomainDataFactory.buildGuardian;
import static uk.com.poodle.domain.DomainDataFactory.buildPatient;

@ExtendWith(MockitoExtension.class)
class GuardianServiceTest {

    @Mock
    private GuardianRepository mockRepository;

    @Mock
    private PatientService mockPatientService;

    @InjectMocks
    private GuardianService service;

    @Test
    void shouldAddGuardianToPatient() {
        when(mockPatientService.getPatient(PATIENT_ID)).thenReturn(Optional.of(buildPatient()));
        when(mockRepository.save(buildGuardianEntity(null))).thenReturn(buildGuardianEntity(GUARDIAN_ID));

        var guardian = service.addGuardian(PATIENT_ID, buildAddGuardianDetailsParams());

        assertEquals(buildGuardian(), guardian);
    }

    @Test
    void shouldThrowNoSuchElementExceptionAddingGuardianToPatientIfPatientDoesNotExist() {
        when(mockPatientService.getPatient(PATIENT_ID)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> service.addGuardian(PATIENT_ID, buildAddGuardianDetailsParams()));

        verifyNoInteractions(mockRepository);
    }
}
