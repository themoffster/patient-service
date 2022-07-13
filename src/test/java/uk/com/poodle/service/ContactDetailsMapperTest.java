package uk.com.poodle.service;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static uk.com.poodle.Constants.CONTACT_DETAILS_ID;
import static uk.com.poodle.data.EntityDataFactory.buildContactDetailsEntity;
import static uk.com.poodle.domain.DomainDataFactory.buildContactDetails;
import static uk.com.poodle.service.ContactDetailsMapper.map;

class ContactDetailsMapperTest {

    @Test
    void classCanNotBeInstantiated() throws NoSuchMethodException {
        var constructor = ContactDetailsMapper.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        assertThrows(InvocationTargetException.class, constructor::newInstance);
    }

    @Test
    void shouldMapContactDetailsEntity() {
        var contactDetails = map(buildContactDetailsEntity(CONTACT_DETAILS_ID));
        assertEquals(buildContactDetails(CONTACT_DETAILS_ID), contactDetails);
    }

    @Test
    void shouldMapCreateContactDetails() {
        var contactDetails = map(buildContactDetails(CONTACT_DETAILS_ID));
        assertEquals(buildContactDetailsEntity(CONTACT_DETAILS_ID), contactDetails);
    }
}
