package uk.com.poodle.service;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static uk.com.poodle.Constants.EDUCATION_ESTABLISHMENT_ID;
import static uk.com.poodle.data.EntityDataFactory.buildEducationEstablishmentEntity;
import static uk.com.poodle.domain.DomainDataFactory.buildAddEducationEstablishmentParams;
import static uk.com.poodle.domain.DomainDataFactory.buildEducationEstablishment;
import static uk.com.poodle.service.EducationEstablishmentMapper.map;

class EducationEstablishmentMapperTest {

    @Test
    void classCanNotBeInstantiated() throws NoSuchMethodException {
        var constructor = EducationEstablishmentMapper.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        assertThrows(InvocationTargetException.class, constructor::newInstance);
    }

    @Test
    void shouldMapEducationEstablishmentEntity() {
        var educationEstablishment = map(buildEducationEstablishmentEntity(EDUCATION_ESTABLISHMENT_ID));
        assertEquals(buildEducationEstablishment(), educationEstablishment);
    }

    @Test
    void shouldMapAddEducationEstablishmentParams() {
        var educationEstablishment = map(buildAddEducationEstablishmentParams());
        assertEquals(buildEducationEstablishmentEntity(), educationEstablishment);
    }
}
