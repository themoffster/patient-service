package uk.com.poodle.service;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static uk.com.poodle.Constants.PATIENT_ID;
import static uk.com.poodle.data.EntityDataFactory.buildPatientEntity;
import static uk.com.poodle.domain.DomainDataFactory.buildAddPatientParams;
import static uk.com.poodle.domain.DomainDataFactory.buildPatient;
import static uk.com.poodle.service.PatientMapper.map;

class PatientMapperTest {

    @Test
    void classCanNotBeInstantiated() throws NoSuchMethodException {
        var constructor = PatientMapper.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        assertThrows(InvocationTargetException.class, constructor::newInstance);
    }

    @Test
    void shouldMapPatientEntity() {
        var patient = map(buildPatientEntity(PATIENT_ID));
        assertEquals(buildPatient(), patient);
    }

    @Test
    void shouldMapAddPatientParams() {
        var patient = map(buildAddPatientParams());
        assertEquals(buildPatientEntity(), patient);
    }
}
