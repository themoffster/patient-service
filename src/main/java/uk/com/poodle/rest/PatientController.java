package uk.com.poodle.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.com.poodle.domain.Patient;
import uk.com.poodle.service.PatientService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/patients", produces = APPLICATION_JSON_VALUE)
public class PatientController {

    private final PatientService service;

    @GetMapping("{patientId}")
    public ResponseEntity<Patient> getPatient(@PathVariable("patientId") String id) {
        return service.getPatient(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
