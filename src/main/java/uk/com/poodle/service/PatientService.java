package uk.com.poodle.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uk.com.poodle.data.PatientRepository;
import uk.com.poodle.domain.AddPatientParams;
import uk.com.poodle.domain.Patient;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;
import static uk.com.poodle.service.PatientMapper.map;

@Slf4j
@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository repository;
    private final EducationEstablishmentService educationEstablishmentService;

    public Patient addPatient(AddPatientParams params) {
        var educationEstablishment = Optional.of(params.getEducationEstablishmentId())
            .flatMap(educationEstablishmentService::getEducationEstablishment)
            .map(EducationEstablishmentMapper::map)
            .orElseThrow(() -> new IllegalArgumentException("Education establishment not found."));
        log.info("Adding patient {} {}.", params.getFirstname(), params.getLastname());
        var entity = map(params, educationEstablishment);
        var savedEntity = repository.save(entity);
        log.info("Added patient record {} for {} {}.", savedEntity.getId(), savedEntity.getFirstname(), savedEntity.getLastname());
        return map(savedEntity);
    }

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

    public List<Patient> getAllPatientsByEducationEstablishment(String educationEstablishmentId) {
        log.info("Retrieving all patients.");
        return repository.findAllByEducationEstablishmentId(educationEstablishmentId).stream()
            .map(PatientMapper::map)
            .collect(toList());
    }
}
