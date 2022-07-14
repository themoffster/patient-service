package uk.com.poodle.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;
import lombok.With;
import lombok.extern.jackson.Jacksonized;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@With
@Value
@Builder
@Jacksonized
public class AddPatientParams {

    @Valid
    @NotNull
    Address address;

    @NotNull
    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd")
    LocalDate dob;

    @NotBlank
    String educationEstablishmentId;

    @NotBlank
    String firstname;

    @NotBlank
    String lastname;

    @NotNull
    Sex sex;
}
