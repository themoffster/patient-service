package uk.com.poodle.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SexTest {

    @ParameterizedTest
    @EnumSource(value = Sex.class)
    void shouldMapCode(Sex sex) {
        assertEquals(sex, Sex.fromCode(sex.getCode()));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionMappingUnknownCode() {
        var thrown = assertThrows(IllegalArgumentException.class, () -> Sex.fromCode("x"));
        assertEquals("Sex 'x' not found.", thrown.getMessage());
    }
}
