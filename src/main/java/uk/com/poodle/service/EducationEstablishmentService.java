package uk.com.poodle.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uk.com.poodle.data.EducationEstablishmentRepository;
import uk.com.poodle.domain.AddEducationEstablishmentParams;
import uk.com.poodle.domain.EducationEstablishment;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;
import static uk.com.poodle.service.EducationEstablishmentMapper.map;

@Slf4j
@Service
@RequiredArgsConstructor
public class EducationEstablishmentService {

    private final EducationEstablishmentRepository repository;

    public EducationEstablishment addEducationEstablishment(AddEducationEstablishmentParams params) {
        log.info("Adding {} {}.", params.getType(), params.getName());
        var entity = map(params);
        var savedEntity = repository.save(entity);
        log.info("Added {} {}.", savedEntity.getType(), savedEntity.getName());
        return map(savedEntity);
    }

    public Optional<EducationEstablishment> getEducationEstablishment(String id) {
        log.info("Retrieving education establishment {}.", id);
        return repository.findById(id)
            .map(EducationEstablishmentMapper::map)
            .or(() -> {
                log.warn("Education establishment {}, not found.", id);
                return Optional.empty();
            });
    }

    public List<EducationEstablishment> getEducationEstablishments() {
        log.info("Retrieving all education establishments.");
        return stream(repository.findAll().spliterator(), false)
            .map(EducationEstablishmentMapper::map)
            .collect(toList());
    }
}
