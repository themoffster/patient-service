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
import uk.com.poodle.domain.CreatePatientParams;
import uk.com.poodle.service.PatientService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.com.poodle.Constants.PATIENT_FIRSTNAME;
import static uk.com.poodle.Constants.PATIENT_ID;
import static uk.com.poodle.Constants.PATIENT_LASTNAME;
import static uk.com.poodle.domain.DomainDataFactory.buildNewCreatePatientParams;
import static uk.com.poodle.domain.DomainDataFactory.buildNewPatient;
import static uk.com.poodle.utils.FileUtils.fileToString;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = PatientController.class)
class PatientControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PatientService mockService;

    @Test
    void shouldRetrieveAllPatients() throws Exception {
        when(mockService.getAllPatients()).thenReturn(List.of(buildNewPatient()));

        mvc.perform(get("/patients/"))
            .andExpect(status().isOk())
            .andExpect(content().json(fileToString("patients.json", getClass())));
    }

    @Test
    void shouldRetrievePatient() throws Exception {
        when(mockService.getPatient(PATIENT_ID)).thenReturn(Optional.of(buildNewPatient()));

        mvc.perform(get("/patients/" + PATIENT_ID))
            .andExpect(status().isOk())
            .andExpect(content().json(fileToString("patient.json", getClass())));
    }

    @Test
    void shouldReturnNotFoundWhenUnableToRetrievePatient() throws Exception {
        when(mockService.getPatient(PATIENT_ID)).thenReturn(Optional.empty());

        mvc.perform(get("/patients/" + PATIENT_ID))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreatePatient() throws Exception {
        when(mockService.createPatient(buildNewCreatePatientParams())).thenReturn(buildNewPatient());

        mvc.perform(post("/patients/create")
                .contentType(APPLICATION_JSON)
                .content(fileToString("create-patient-params.json", getClass())))
            .andExpect(status().isCreated())
            .andExpect(content().json(fileToString("patient.json", getClass())));
    }

    @NullSource
    @ParameterizedTest
    @MethodSource("invalidCreatePatientParams")
    void shouldReturnBadRequestWhenCreatePatientParamsAreInvalid(CreatePatientParams params) throws Exception {
        var json = new ObjectMapper().writeValueAsString(params);

        mvc.perform(post("/patients/create")
                .contentType(APPLICATION_JSON)
                .content(json))
            .andExpect(status().isBadRequest());
    }

    private static Stream<Arguments> invalidCreatePatientParams() {
        return Stream.of(
            Arguments.of(CreatePatientParams.builder().build()),
            Arguments.of(CreatePatientParams.builder()
                .firstname(PATIENT_FIRSTNAME)
                .build()),
            Arguments.of(CreatePatientParams.builder()
                .lastname(PATIENT_LASTNAME)
                .build()),
            Arguments.of(CreatePatientParams.builder()
                .firstname(PATIENT_FIRSTNAME)
                .lastname("")
                .build()),
            Arguments.of(CreatePatientParams.builder()
                .firstname("")
                .lastname(PATIENT_LASTNAME)
                .build()),
            Arguments.of(CreatePatientParams.builder()
                .firstname(PATIENT_FIRSTNAME)
                .lastname("  ")
                .build()),
            Arguments.of(CreatePatientParams.builder()
                .firstname("  ")
                .lastname(PATIENT_LASTNAME)
                .build())
        );
    }
}
