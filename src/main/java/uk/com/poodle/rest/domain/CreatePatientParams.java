package uk.com.poodle.rest.domain;

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
