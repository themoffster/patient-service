package uk.com.poodle.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.com.poodle.domain.AddEducationEstablishmentParams;
import uk.com.poodle.domain.EducationEstablishment;
import uk.com.poodle.service.EducationEstablishmentService;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/education-establishments", produces = APPLICATION_JSON_VALUE)
public class EducationEstablishmentController {

    private final EducationEstablishmentService service;

    @PostMapping("/add")
    public ResponseEntity<EducationEstablishment> addEducationEstablishment(@RequestBody @Valid AddEducationEstablishmentParams params) {
        return ResponseEntity.status(CREATED).body(service.addEducationEstablishment(params));
    }
}
