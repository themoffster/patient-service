package uk.com.poodle.rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import uk.com.poodle.service.AppointmentService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.com.poodle.Constants.PATIENT_ID;
import static uk.com.poodle.domain.DomainDataFactory.buildNewAppointment;
import static uk.com.poodle.utils.FileUtils.fileToString;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = AppointmentController.class)
class AppointmentControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AppointmentService mockService;

    @Test
    void shouldRetrieveAllAppointmentsForPatients() throws Exception {
        when(mockService.getAppointments(PATIENT_ID, true)).thenReturn(List.of(buildNewAppointment()));

        mvc.perform(get("/appointments/" + PATIENT_ID)
                .param("includeHistoric", "true"))
            .andExpect(status().isOk())
            .andExpect(content().json(fileToString("appointments.json", getClass())));
    }

    @Test
    void shouldRetrieveAllUpcomingAppointmentsForPatients() throws Exception {
        when(mockService.getAppointments(PATIENT_ID, false)).thenReturn(List.of(buildNewAppointment()));

        mvc.perform(get("/appointments/" + PATIENT_ID))
            .andExpect(status().isOk())
            .andExpect(content().json(fileToString("appointments.json", getClass())));
    }
}
