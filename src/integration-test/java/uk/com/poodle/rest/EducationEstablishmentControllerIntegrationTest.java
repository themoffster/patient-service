package uk.com.poodle.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import uk.com.poodle.domain.EducationEstablishment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpStatus.CREATED;
import static uk.com.poodle.domain.DomainDataFactory.buildAddEducationEstablishmentParams;
import static uk.com.poodle.domain.DomainDataFactory.buildEducationEstablishment;

class EducationEstablishmentControllerIntegrationTest extends AbstractControllerIntegrationTest {

    @Test
    void shouldAddEducationEstablishment() throws JsonProcessingException, JSONException {
        var payload = buildAddEducationEstablishmentParams();
        var responseEntity = restTemplate.postForEntity("/education-establishments/add", payload, EducationEstablishment.class);

        assertEquals(CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getId());
        JSONAssert.assertEquals(
            mapper.writeValueAsString(buildEducationEstablishment()),
            mapper.writeValueAsString(responseEntity.getBody()),
            new CustomComparator(
                JSONCompareMode.STRICT,
                new Customization("id", (o1, o2) -> true),
                new Customization("address.id", (o1, o2) -> true),
                new Customization("contactDetails.id", (o1, o2) -> true)));
    }
}
