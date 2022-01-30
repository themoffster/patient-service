package uk.com.poodle.service;

import org.junit.jupiter.api.Test;
import uk.com.poodle.domain.Patient;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static uk.com.poodle.Constants.PATIENT_ID;
import static uk.com.poodle.data.EntityDataFactory.buildNewPatientEntity;
import static uk.com.poodle.domain.DomainDataFactory.buildNewPatient;

class PatientMapperTest {

    @Test
    void classCanNotBeInstantiated() throws NoSuchMethodException {
        Constructor<PatientMapper> constructor = PatientMapper.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        assertThrows(InvocationTargetException.class, constructor::newInstance);
    }

    @Test
    void shouldMapPatientEntity() {
        Patient patient = PatientMapper.map(buildNewPatientEntity(PATIENT_ID));
        assertEquals(buildNewPatient(), patient);
    }
}
