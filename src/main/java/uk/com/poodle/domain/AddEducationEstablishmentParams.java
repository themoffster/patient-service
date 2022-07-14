package uk.com.poodle.domain;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
@Builder
@Jacksonized
public class AddEducationEstablishmentParams {

    @Valid
    @NotNull
    Address address;

    @Valid
    @NotNull
    ContactDetails contactDetails;

    @NotBlank
    String name;

    @NotNull
    EducationEstablishmentType type;
}
