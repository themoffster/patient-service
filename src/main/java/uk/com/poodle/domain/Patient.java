package uk.com.poodle.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Value
@Builder
@Jacksonized
public class Patient {

    String id;

    List<Appointment> appointments;

    ContactDetails contactDetails;

    @JsonFormat(shape = STRING)
    LocalDate dob;

    String firstname;

    List<Guardian> guardians;

    String lastname;

    Sex sex;
}
