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
import uk.com.poodle.domain.AddGuardianDetailsParams;
import uk.com.poodle.service.GuardianService;

import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.com.poodle.Constants.PATIENT_ID;
import static uk.com.poodle.domain.DomainDataFactory.buildAddGuardianDetailsParams;
import static uk.com.poodle.domain.DomainDataFactory.buildAddress;
import static uk.com.poodle.domain.DomainDataFactory.buildContactDetails;
import static uk.com.poodle.domain.DomainDataFactory.buildGuardian;
import static uk.com.poodle.utils.FileUtils.fileToString;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = GuardianController.class)
class GuardianControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private GuardianService mockService;

    @Test
    void shouldAddGuardianToPatient() throws Exception {
        when(mockService.addGuardian(PATIENT_ID, buildAddGuardianDetailsParams())).thenReturn(buildGuardian());

        mvc.perform(post("/patients/" + PATIENT_ID + "/guardians/add")
                .contentType(APPLICATION_JSON)
                .content(fileToString("add-guardian-params.json", getClass())))
            .andExpect(status().isCreated())
            .andExpect(content().json(fileToString("guardian.json", getClass())));
    }

    @NullSource
    @ParameterizedTest
    @MethodSource("invalidAddGuardianDetailsParams")
    void shouldReturnBadRequestWhenAddGuardianDetailsParamsAreInvalid(AddGuardianDetailsParams params) throws Exception {
        var json = mapper.writeValueAsString(params);

        mvc.perform(post("/patients/" + PATIENT_ID + "/guardians/add")
                .contentType(APPLICATION_JSON)
                .content(json))
            .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetGuardiansForPatient() throws Exception {
        when(mockService.getGuardians(PATIENT_ID)).thenReturn(List.of(buildGuardian()));

        mvc.perform(get("/patients/" + PATIENT_ID + "/guardians")
                .contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(fileToString("guardians.json", getClass())));
    }

    private static Stream<Arguments> invalidAddGuardianDetailsParams() {
        return Stream.of(
            Arguments.of(AddGuardianDetailsParams.builder().build()),
            Arguments.of(buildAddGuardianDetailsParams().withAddress(null)),
            Arguments.of(buildAddGuardianDetailsParams().withAddress(buildAddress().withNumber(""))),
            Arguments.of(buildAddGuardianDetailsParams().withAddress(buildAddress().withNumber(" "))),
            Arguments.of(buildAddGuardianDetailsParams().withAddress(buildAddress().withStreet(null))),
            Arguments.of(buildAddGuardianDetailsParams().withAddress(buildAddress().withStreet(""))),
            Arguments.of(buildAddGuardianDetailsParams().withAddress(buildAddress().withStreet(" "))),
            Arguments.of(buildAddGuardianDetailsParams().withAddress(buildAddress().withTown(null))),
            Arguments.of(buildAddGuardianDetailsParams().withAddress(buildAddress().withTown(""))),
            Arguments.of(buildAddGuardianDetailsParams().withAddress(buildAddress().withTown(" "))),
            Arguments.of(buildAddGuardianDetailsParams().withAddress(buildAddress().withPostcode(null))),
            Arguments.of(buildAddGuardianDetailsParams().withAddress(buildAddress().withPostcode(""))),
            Arguments.of(buildAddGuardianDetailsParams().withAddress(buildAddress().withPostcode(" "))),
            Arguments.of(buildAddGuardianDetailsParams().withContactDetails(null)),
            Arguments.of(buildAddGuardianDetailsParams().withFirstname(null)),
            Arguments.of(buildAddGuardianDetailsParams().withFirstname("")),
            Arguments.of(buildAddGuardianDetailsParams().withFirstname(" ")),
            Arguments.of(buildAddGuardianDetailsParams().withLastname(null)),
            Arguments.of(buildAddGuardianDetailsParams().withLastname("")),
            Arguments.of(buildAddGuardianDetailsParams().withLastname(" ")),
            Arguments.of(buildAddGuardianDetailsParams().withDob(null)),
            Arguments.of(buildAddGuardianDetailsParams().withRelation(null)),
            Arguments.of(buildAddGuardianDetailsParams().withSex(null)),
            Arguments.of(buildAddGuardianDetailsParams().withContactDetails(buildContactDetails().withEmail("not@email")))
        );
    }
}
