package uk.com.poodle.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import uk.com.poodle.domain.AddEducationEstablishmentParams;
import uk.com.poodle.service.EducationEstablishmentService;

import java.util.stream.Stream;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.com.poodle.domain.DomainDataFactory.buildAddEducationEstablishmentParams;
import static uk.com.poodle.domain.DomainDataFactory.buildEducationEstablishment;
import static uk.com.poodle.utils.FileUtils.fileToString;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = EducationEstablishmentController.class)
class EducationEstablishmentControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private EducationEstablishmentService mockService;

    @Test
    void shouldAddEducationEstablishment() throws Exception {
        when(mockService.addEducationEstablishment(buildAddEducationEstablishmentParams())).thenReturn(buildEducationEstablishment());

        mvc.perform(post("/education-establishments/add")
                .contentType(APPLICATION_JSON)
                .content(fileToString("add-education-establishment-params.json", getClass())))
            .andExpect(status().isCreated())
            .andExpect(content().json(fileToString("education-establishment.json", getClass())));
    }

    @NullSource
    @ParameterizedTest
    @MethodSource("invalidAddEducationEstablishmentParams")
    void shouldReturnBadRequestWhenAddEducationEstablishmentParamsAreInvalid(AddEducationEstablishmentParams params) throws Exception {
        var json = mapper.writeValueAsString(params);

        mvc.perform(post("/education-establishments/add")
                .contentType(APPLICATION_JSON)
                .content(json))
            .andExpect(status().isBadRequest());
    }

    private static Stream<Arguments> invalidAddEducationEstablishmentParams() {
        return Stream.of(
            Arguments.of(AddEducationEstablishmentParams.builder().build()),
            Arguments.of(AddEducationEstablishmentParams.builder().name(null).build()),
            Arguments.of(AddEducationEstablishmentParams.builder().name("").build()),
            Arguments.of(AddEducationEstablishmentParams.builder().name(" ").build())
        );
    }
}
