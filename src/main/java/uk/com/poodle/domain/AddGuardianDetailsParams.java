package uk.com.poodle.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;
import lombok.With;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@With
@Value
@Builder
@Jacksonized
public class AddGuardianDetailsParams {

    @NotNull
    ContactDetails contactDetails;

    @NotNull
    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd")
    LocalDate dob;

    @NotBlank
    String firstname;

    @NotBlank
    String lastname;

    @NotNull
    Sex sex;

    @NotNull
    Relation relation;
}
