package uk.com.poodle.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.com.poodle.domain.Appointment;
import uk.com.poodle.service.AppointmentService;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/appointments", produces = APPLICATION_JSON_VALUE)
public class AppointmentController {

    private final AppointmentService service;

    @GetMapping("{patientId}")
    public ResponseEntity<List<Appointment>> getAppointments(@PathVariable("patientId") String id, @RequestParam(defaultValue = "false") boolean includeHistoric) {
        return ok(service.getAppointments(id, includeHistoric));
    }
}