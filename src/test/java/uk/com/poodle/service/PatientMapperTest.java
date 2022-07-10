package uk.com.poodle.service;

import org.junit.jupiter.api.Test;
import uk.com.poodle.domain.CreatePatientParams;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static uk.com.poodle.Constants.PATIENT_FIRSTNAME;
import static uk.com.poodle.Constants.PATIENT_ID;
import static uk.com.poodle.Constants.PATIENT_LASTNAME;
import static uk.com.poodle.data.EntityDataFactory.buildNewPatientEntity;
import static uk.com.poodle.domain.DomainDataFactory.buildNewPatient;
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
        var patient = map(buildNewPatientEntity(PATIENT_ID));
        assertEquals(buildNewPatient(), patient);
    }

    @Test
    void shouldMapCreatePatientParams() {
        var params = CreatePatientParams.builder()
            .firstname(PATIENT_FIRSTNAME)
            .lastname(PATIENT_LASTNAME)
            .build();
        var patient = map(params);
        assertEquals(buildNewPatientEntity(), patient);
    }
}
