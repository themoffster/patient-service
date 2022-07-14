package uk.com.poodle.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotBlank;

@With
@Value
@Builder
@Jacksonized
public class Address {

    String id;

    @NotBlank
    String number;

    @NotBlank
    String street;

    @NotBlank
    String town;

    @NotBlank
    String postcode;
}
