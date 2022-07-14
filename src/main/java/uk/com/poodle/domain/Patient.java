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

    Address address;

    List<Appointment> appointments;

    @JsonFormat(shape = STRING)
    LocalDate dob;

    EducationEstablishment educationEstablishment;

    String firstname;

    List<Guardian> guardians;

    String lastname;

    Sex sex;
}
