package uk.com.poodle.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uk.com.poodle.data.GuardianRepository;
import uk.com.poodle.domain.AddGuardianDetailsParams;
import uk.com.poodle.domain.Guardian;

import static uk.com.poodle.service.GuardianMapper.map;

@Slf4j
@Service
@RequiredArgsConstructor
public class GuardianService {

    private final GuardianRepository repository;
    private final PatientService patientService;

    public Guardian addGuardian(String id, AddGuardianDetailsParams params) {
        var patient = patientService.getPatient(id).orElseThrow();
        log.info("Adding guardian {} {} to patient {}.", params.getFirstname(), params.getLastname(), patient.getId());
        var entity = map(id, params);
        var savedEntity = repository.save(entity);
        log.info("Added guardian record {} for {} {}.", savedEntity.getId(), savedEntity.getFirstname(), savedEntity.getLastname());
        return map(savedEntity);
    }
}
