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
import uk.com.poodle.domain.AddPatientParams;
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
import static uk.com.poodle.Constants.PATIENT_ID;
import static uk.com.poodle.domain.DomainDataFactory.buildAddPatientParams;
import static uk.com.poodle.domain.DomainDataFactory.buildAddress;
import static uk.com.poodle.domain.DomainDataFactory.buildPatient;
import static uk.com.poodle.utils.FileUtils.fileToString;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = PatientController.class)
class PatientControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private PatientService mockService;

    @Test
    void shouldRetrieveAllPatients() throws Exception {
        when(mockService.getAllPatients()).thenReturn(List.of(buildPatient()));

        mvc.perform(get("/patients/"))
            .andExpect(status().isOk())
            .andExpect(content().json(fileToString("patients.json", getClass())));
    }

    @Test
    void shouldRetrievePatient() throws Exception {
        when(mockService.getPatient(PATIENT_ID)).thenReturn(Optional.of(buildPatient()));

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
    void shouldAddPatient() throws Exception {
        when(mockService.addPatient(buildAddPatientParams())).thenReturn(buildPatient());

        mvc.perform(post("/patients/add")
                .contentType(APPLICATION_JSON)
                .content(fileToString("add-patient-params.json", getClass())))
            .andExpect(status().isCreated())
            .andExpect(content().json(fileToString("patient.json", getClass())));
    }

    @NullSource
    @ParameterizedTest
    @MethodSource("invalidAddPatientParams")
    void shouldReturnBadRequestWhenAddPatientParamsAreInvalid(AddPatientParams params) throws Exception {
        var json = mapper.writeValueAsString(params);

        mvc.perform(post("/patients/add")
                .contentType(APPLICATION_JSON)
                .content(json))
            .andExpect(status().isBadRequest());
    }

    private static Stream<Arguments> invalidAddPatientParams() {
        return Stream.of(
            Arguments.of(AddPatientParams.builder().build()),
            Arguments.of(buildAddPatientParams().withAddress(null)),
            Arguments.of(buildAddPatientParams().withAddress(buildAddress().withNumber(null))),
            Arguments.of(buildAddPatientParams().withAddress(buildAddress().withNumber(""))),
            Arguments.of(buildAddPatientParams().withAddress(buildAddress().withNumber(" "))),
            Arguments.of(buildAddPatientParams().withAddress(buildAddress().withStreet(null))),
            Arguments.of(buildAddPatientParams().withAddress(buildAddress().withStreet(""))),
            Arguments.of(buildAddPatientParams().withAddress(buildAddress().withStreet(" "))),
            Arguments.of(buildAddPatientParams().withAddress(buildAddress().withTown(null))),
            Arguments.of(buildAddPatientParams().withAddress(buildAddress().withTown(""))),
            Arguments.of(buildAddPatientParams().withAddress(buildAddress().withTown(" "))),
            Arguments.of(buildAddPatientParams().withAddress(buildAddress().withPostcode(null))),
            Arguments.of(buildAddPatientParams().withAddress(buildAddress().withPostcode(""))),
            Arguments.of(buildAddPatientParams().withAddress(buildAddress().withPostcode(" "))),
            Arguments.of(buildAddPatientParams().withDob(null)),
            Arguments.of(buildAddPatientParams().withEducationEstablishmentId(null)),
            Arguments.of(buildAddPatientParams().withEducationEstablishmentId("")),
            Arguments.of(buildAddPatientParams().withEducationEstablishmentId(" ")),
            Arguments.of(buildAddPatientParams().withFirstname(null)),
            Arguments.of(buildAddPatientParams().withFirstname("")),
            Arguments.of(buildAddPatientParams().withFirstname(" ")),
            Arguments.of(buildAddPatientParams().withLastname(null)),
            Arguments.of(buildAddPatientParams().withLastname("")),
            Arguments.of(buildAddPatientParams().withLastname(" ")),
            Arguments.of(buildAddPatientParams().withSex(null))
        );
    }
}
