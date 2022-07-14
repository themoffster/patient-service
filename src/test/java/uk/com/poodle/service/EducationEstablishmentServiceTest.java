package uk.com.poodle.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.com.poodle.data.EducationEstablishmentRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static uk.com.poodle.Constants.EDUCATION_ESTABLISHMENT_ID;
import static uk.com.poodle.data.EntityDataFactory.buildEducationEstablishmentEntity;
import static uk.com.poodle.domain.DomainDataFactory.buildAddEducationEstablishmentParams;
import static uk.com.poodle.domain.DomainDataFactory.buildEducationEstablishment;

@ExtendWith(MockitoExtension.class)
class EducationEstablishmentServiceTest {

    @Mock
    private EducationEstablishmentRepository mockRepository;

    @InjectMocks
    private EducationEstablishmentService service;

    @Test
    void shouldAddEducationEstablishment() {
        when(mockRepository.save(buildEducationEstablishmentEntity())).thenReturn(buildEducationEstablishmentEntity(EDUCATION_ESTABLISHMENT_ID));

        var educationEstablishment = service.addEducationEstablishment(buildAddEducationEstablishmentParams());

        assertEquals(buildEducationEstablishment(), educationEstablishment);
    }

    @Test
    void shouldRetrieveEducationEstablishment() {
        when(mockRepository.findById(EDUCATION_ESTABLISHMENT_ID)).thenReturn(Optional.of(buildEducationEstablishmentEntity(EDUCATION_ESTABLISHMENT_ID)));

        var optional = service.getEducationEstablishment(EDUCATION_ESTABLISHMENT_ID);

        optional.ifPresentOrElse(educationEstablishment -> assertEquals(buildEducationEstablishment(), educationEstablishment), Assertions::fail);
    }

    @Test
    void shouldReturnEmptyOptionalWhenUnableToRetrieveEducationEstablishment() {
        when(mockRepository.findById(EDUCATION_ESTABLISHMENT_ID)).thenReturn(Optional.empty());

        var educationEstablishmentOptional = service.getEducationEstablishment(EDUCATION_ESTABLISHMENT_ID);

        assertTrue(educationEstablishmentOptional.isEmpty());
    }
}
