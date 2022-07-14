package uk.com.poodle.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Value
@Builder
@Jacksonized
public class Guardian {

    String id;

    Address address;

    ContactDetails contactDetails;

    @JsonFormat(shape = STRING)
    LocalDate dob;

    String firstname;

    String lastname;

    Sex sex;

    Relation relation;
}
