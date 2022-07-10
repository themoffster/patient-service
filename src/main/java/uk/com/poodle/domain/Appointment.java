package uk.com.poodle.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Value
@EqualsAndHashCode
@Builder(toBuilder = true)
public class Appointment {

    String id;

    String patientId;

    @JsonFormat(shape = STRING)
    LocalDateTime dateTime;
}
