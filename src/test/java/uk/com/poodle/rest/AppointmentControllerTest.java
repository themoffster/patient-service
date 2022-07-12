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
import uk.com.poodle.domain.CreateAppointmentParams;
import uk.com.poodle.service.AppointmentService;

import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.com.poodle.Constants.PATIENT_ID;
import static uk.com.poodle.domain.DomainDataFactory.buildAppointment;
import static uk.com.poodle.domain.DomainDataFactory.buildCreateAppointmentParams;
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
        when(mockService.getAppointments(PATIENT_ID, true)).thenReturn(List.of(buildAppointment()));

        mvc.perform(get("/appointments/" + PATIENT_ID)
                .param("includeHistoric", "true"))
            .andExpect(status().isOk())
            .andExpect(content().json(fileToString("appointments.json", getClass())));
    }

    @Test
    void shouldRetrieveAllUpcomingAppointmentsForPatients() throws Exception {
        when(mockService.getAppointments(PATIENT_ID, false)).thenReturn(List.of(buildAppointment()));

        mvc.perform(get("/appointments/" + PATIENT_ID))
            .andExpect(status().isOk())
            .andExpect(content().json(fileToString("appointments.json", getClass())));
    }

    @Test
    void shouldCreateAppointment() throws Exception {
        when(mockService.createAppointment(buildCreateAppointmentParams())).thenReturn(buildAppointment());

        mvc.perform(post("/appointments/create")
                .contentType(APPLICATION_JSON)
                .content(fileToString("create-appointment-params.json", getClass())))
            .andExpect(status().isCreated())
            .andExpect(content().json(fileToString("appointment.json", getClass())));
    }

    @NullSource
    @ParameterizedTest
    @MethodSource("invalidCreateAppointmentParams")
    void shouldReturnBadRequestWhenCreateAppointmentParamsAreInvalid(CreateAppointmentParams params) throws Exception {
        var json = new ObjectMapper().writeValueAsString(params);

        mvc.perform(post("/appointments/create")
                .contentType(APPLICATION_JSON)
                .content(json))
            .andExpect(status().isBadRequest());
    }

    private static Stream<Arguments> invalidCreateAppointmentParams() {
        return Stream.of(
            Arguments.of(CreateAppointmentParams.builder().build())
        );
    }
}
