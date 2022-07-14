package uk.com.poodle.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.com.poodle.domain.AddPatientParams;
import uk.com.poodle.domain.Patient;
import uk.com.poodle.service.PatientService;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/patients", produces = APPLICATION_JSON_VALUE)
public class PatientController {

    private final PatientService service;

    @PostMapping("/add")
    public ResponseEntity<Patient> addPatient(@RequestBody @Valid AddPatientParams params) {
        return ResponseEntity.status(CREATED).body(service.addPatient(params));
    }

    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        return ok(service.getAllPatients());
    }

    @GetMapping("{patientId}")
    public ResponseEntity<Patient> getPatient(@PathVariable("patientId") String id) {
        return service.getPatient(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/education-establishment/{educationEstablishmentId}")
    public ResponseEntity<List<Patient>> getAllPatientsByEducationEstablishment(@PathVariable("educationEstablishmentId") String id) {
        return ok(service.getAllPatientsByEducationEstablishment(id));
    }
}
