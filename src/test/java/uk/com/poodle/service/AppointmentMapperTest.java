package uk.com.poodle.service;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static uk.com.poodle.Constants.APPOINTMENT_ID;
import static uk.com.poodle.data.EntityDataFactory.buildNewAppointmentEntity;
import static uk.com.poodle.domain.DomainDataFactory.buildNewAppointment;

class AppointmentMapperTest {

    @Test
    void classCanNotBeInstantiated() throws NoSuchMethodException {
        var constructor = AppointmentMapper.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        assertThrows(InvocationTargetException.class, constructor::newInstance);
    }

    @Test
    void shouldMapAppointmentEntity() {
        var appointment = AppointmentMapper.map(buildNewAppointmentEntity(APPOINTMENT_ID));
        assertEquals(buildNewAppointment(), appointment);
    }
}
