package uk.com.poodle.domain;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Value
@Builder
@Jacksonized
public class CreateAppointmentParams {

    @NotNull
    LocalDateTime dateTime;
}
