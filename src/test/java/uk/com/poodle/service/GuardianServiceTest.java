package uk.com.poodle.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.com.poodle.data.GuardianRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static uk.com.poodle.Constants.GUARDIAN_ID;
import static uk.com.poodle.Constants.PATIENT_ID;
import static uk.com.poodle.data.EntityDataFactory.buildGuardianEntity;
import static uk.com.poodle.domain.DomainDataFactory.buildAddGuardianDetailsParams;
import static uk.com.poodle.domain.DomainDataFactory.buildGuardian;
import static uk.com.poodle.domain.DomainDataFactory.buildPatient;
import static uk.com.poodle.service.GuardianMapper.map;

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

    @Test
    void shouldGetGuardiansForPatient() {
        var entity = buildGuardianEntity(GUARDIAN_ID);
        when(mockRepository.findAllByPatientId(PATIENT_ID)).thenReturn(List.of(entity));

        var guardians = service.getGuardians(PATIENT_ID);

        assertEquals(1, guardians.size());
        assertTrue(guardians.contains(map(entity)));
    }
}
