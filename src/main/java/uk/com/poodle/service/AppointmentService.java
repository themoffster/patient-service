package uk.com.poodle.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.stereotype.Service;
import uk.com.poodle.data.AppointmentRepository;
import uk.com.poodle.domain.Appointment;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository repository;
    private final DateTimeProvider dateTimeProvider;

    public List<Appointment> getAppointments(String id, boolean includeHistoric) {
        log.info("Retrieving appointments for patient {}.", id);
        var appointments = includeHistoric
            ? repository.findAllByPatientId(id)
            : repository.findAllByPatientIdAndDateTimeGreaterThanEqual(id, now());
        return appointments.stream()
            .map(AppointmentMapper::map)
            .collect(toList());
    }

    private LocalDateTime now() {
        return dateTimeProvider.getNow()
            .map(LocalDateTime.class::cast)
            .orElseThrow();
    }
}
