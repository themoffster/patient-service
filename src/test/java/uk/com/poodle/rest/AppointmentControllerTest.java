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
import uk.com.poodle.domain.AddAppointmentParams;
import uk.com.poodle.service.AppointmentService;

import java.util.List;
import java.util.stream.Stream;

import static java.time.LocalDateTime.now;
import static java.time.temporal.ChronoUnit.DAYS;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.com.poodle.Constants.PATIENT_ID;
import static uk.com.poodle.domain.DomainDataFactory.buildAddAppointmentParams;
import static uk.com.poodle.domain.DomainDataFactory.buildAppointment;
import static uk.com.poodle.utils.FileUtils.fileToString;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = AppointmentController.class)
class AppointmentControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AppointmentService mockService;

    @Test
    void shouldRetrieveAllAppointmentsForPatients() throws Exception {
        when(mockService.getAppointments(PATIENT_ID, true)).thenReturn(List.of(buildAppointment()));

        mvc.perform(get("/patients/" + PATIENT_ID + "/appointments")
                .param("includeHistoric", "true"))
            .andExpect(status().isOk())
            .andExpect(content().json(fileToString("appointments.json", getClass())));
    }

    @Test
    void shouldRetrieveAllUpcomingAppointmentsForPatients() throws Exception {
        when(mockService.getAppointments(PATIENT_ID, false)).thenReturn(List.of(buildAppointment()));

        mvc.perform(get("/patients/" + PATIENT_ID + "/appointments"))
            .andExpect(status().isOk())
            .andExpect(content().json(fileToString("appointments.json", getClass())));
    }

    @Test
    void shouldAddAppointment() throws Exception {
        when(mockService.addAppointment(PATIENT_ID, buildAddAppointmentParams())).thenReturn(buildAppointment());

        mvc.perform(post("/patients/" + PATIENT_ID + "/appointments/add")
                .contentType(APPLICATION_JSON)
                .content(fileToString("add-appointment-params.json", getClass())))
            .andExpect(status().isCreated())
            .andExpect(content().json(fileToString("appointment.json", getClass())));
    }

    @NullSource
    @ParameterizedTest
    @MethodSource("invalidAddAppointmentParams")
    void shouldReturnBadRequestWhenAddAppointmentParamsAreInvalid(AddAppointmentParams params) throws Exception {
        var json = mapper.writeValueAsString(params);

        mvc.perform(post("/patients/" + PATIENT_ID + "/appointments/add")
                .contentType(APPLICATION_JSON)
                .content(json))
            .andExpect(status().isBadRequest());
    }

    private static Stream<Arguments> invalidAddAppointmentParams() {
        return Stream.of(
            Arguments.of(AddAppointmentParams.builder().build()),
            Arguments.of(AddAppointmentParams.builder()
                .dateTime(now().minus(1, DAYS))
                .build())
        );
    }
}
