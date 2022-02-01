package uk.com.poodle.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uk.com.poodle.data.PatientRepository;
import uk.com.poodle.domain.Patient;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository repository;

    public List<Patient> getAllPatients() {
        log.info("Retrieving all patients.");
        return stream(repository.findAll().spliterator(), false)
            .map(PatientMapper::map)
            .collect(toList());
    }

    public Optional<Patient> getPatient(String id) {
        log.info("Retrieving patient {}.", id);
        return repository.findById(id)
            .map(PatientMapper::map)
            .or(() -> {
                log.warn("Patient {}, not found.", id);
                return Optional.empty();
            });
    }
}
