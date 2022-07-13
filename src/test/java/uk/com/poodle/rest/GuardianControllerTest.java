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
import static uk.com.poodle.Constants.PATIENT_DOB;
import static uk.com.poodle.Constants.PATIENT_FIRSTNAME;
import static uk.com.poodle.Constants.PATIENT_ID;
import static uk.com.poodle.Constants.PATIENT_LASTNAME;
import static uk.com.poodle.Constants.PATIENT_SEX;
import static uk.com.poodle.domain.DomainDataFactory.buildAddGuardianDetailsParams;
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
            Arguments.of(buildValidAddGuardianDetailsParams().withFirstname(null)),
            Arguments.of(buildValidAddGuardianDetailsParams().withFirstname("")),
            Arguments.of(buildValidAddGuardianDetailsParams().withFirstname(" ")),
            Arguments.of(buildValidAddGuardianDetailsParams().withLastname(null)),
            Arguments.of(buildValidAddGuardianDetailsParams().withLastname("")),
            Arguments.of(buildValidAddGuardianDetailsParams().withLastname(" ")),
            Arguments.of(buildValidAddGuardianDetailsParams().withDob(null)),
            Arguments.of(buildValidAddGuardianDetailsParams().withRelation(null)),
            Arguments.of(buildValidAddGuardianDetailsParams().withSex(null))
        );
    }

    private static AddGuardianDetailsParams buildValidAddGuardianDetailsParams() {
        return AddGuardianDetailsParams.builder()
            .dob(PATIENT_DOB)
            .firstname(PATIENT_FIRSTNAME)
            .lastname(PATIENT_LASTNAME)
            .sex(PATIENT_SEX)
            .build();
    }
}
