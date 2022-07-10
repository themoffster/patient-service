package uk.com.poodle.domain;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class Patient {

    String id;
    String firstname;
    String lastname;
    List<Appointment> appointments;
}
