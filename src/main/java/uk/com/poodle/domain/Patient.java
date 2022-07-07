package uk.com.poodle.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Patient {

    String id;
    String firstname;
    String lastname;
}
