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
import uk.com.poodle.domain.AddGuardianDetailsParams;
import uk.com.poodle.domain.Guardian;
import uk.com.poodle.service.GuardianService;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/patients/{patientId}/guardians", produces = APPLICATION_JSON_VALUE)
public class GuardianController {

    private final GuardianService service;

    @PostMapping("/add")
    public ResponseEntity<Guardian> createGuardian(@PathVariable("patientId") String id, @RequestBody @Valid AddGuardianDetailsParams params) {
        return ResponseEntity.status(CREATED).body(service.addGuardian(id, params));
    }

    @GetMapping
    public ResponseEntity<List<Guardian>> getGuardians(@PathVariable("patientId") String patientId) {
        return ok(service.getGuardians(patientId));
    }
}
