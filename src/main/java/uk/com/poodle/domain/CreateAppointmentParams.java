package uk.com.poodle.domain;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Value
@Builder
public class CreateAppointmentParams {

    @NotEmpty
    String patientId;

    @NotNull
    LocalDateTime dateTime;
}
