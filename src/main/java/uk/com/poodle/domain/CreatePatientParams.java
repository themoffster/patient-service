package uk.com.poodle.domain;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
@Builder
public class CreatePatientParams {

    @NotBlank
    String firstname;

    @NotBlank
    String lastname;
}
