package uk.com.poodle.rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import uk.com.poodle.service.PatientService;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.com.poodle.Constants.PATIENT_ID;
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
}
