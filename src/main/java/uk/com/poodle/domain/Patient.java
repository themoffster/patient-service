package uk.com.poodle.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class Patient {

    String id;
    String firstname;
    String lastname;
}
