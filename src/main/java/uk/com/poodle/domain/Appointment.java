package uk.com.poodle.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Value
@Builder
@Jacksonized
public class Appointment {

    String id;

    String patientId;

    @JsonFormat(shape = STRING)
    LocalDateTime dateTime;
}
