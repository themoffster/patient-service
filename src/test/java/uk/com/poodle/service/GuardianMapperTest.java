package uk.com.poodle.service;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static uk.com.poodle.Constants.GUARDIAN_ID;
import static uk.com.poodle.Constants.PATIENT_ID;
import static uk.com.poodle.data.EntityDataFactory.buildGuardianEntity;
import static uk.com.poodle.domain.DomainDataFactory.buildAddGuardianDetailsParams;
import static uk.com.poodle.domain.DomainDataFactory.buildGuardian;
import static uk.com.poodle.service.GuardianMapper.map;

class GuardianMapperTest {

    @Test
    void classCanNotBeInstantiated() throws NoSuchMethodException {
        var constructor = GuardianMapper.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        assertThrows(InvocationTargetException.class, constructor::newInstance);
    }

    @Test
    void shouldMapGuardianEntity() {
        var guardian = map(buildGuardianEntity(GUARDIAN_ID));
        assertEquals(buildGuardian(), guardian);
    }

    @Test
    void shouldMapAddGuardianParams() {
        var guardian = map(PATIENT_ID, buildAddGuardianDetailsParams());
        assertEquals(buildGuardianEntity(), guardian);
    }
}
