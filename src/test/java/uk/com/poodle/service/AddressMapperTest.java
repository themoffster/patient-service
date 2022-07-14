package uk.com.poodle.service;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static uk.com.poodle.Constants.ADDRESS_ID;
import static uk.com.poodle.data.EntityDataFactory.buildAddressEntity;
import static uk.com.poodle.domain.DomainDataFactory.buildAddress;
import static uk.com.poodle.service.AddressMapper.map;

class AddressMapperTest {

    @Test
    void classCanNotBeInstantiated() throws NoSuchMethodException {
        var constructor = AddressMapper.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        assertThrows(InvocationTargetException.class, constructor::newInstance);
    }

    @Test
    void shouldMapAddressEntity() {
        var address = map(buildAddressEntity(ADDRESS_ID));
        assertEquals(buildAddress(ADDRESS_ID), address);
    }

    @Test
    void shouldMapAddress() {
        var entity = map(buildAddress(ADDRESS_ID));
        assertEquals(buildAddressEntity(ADDRESS_ID), entity);
    }
}
