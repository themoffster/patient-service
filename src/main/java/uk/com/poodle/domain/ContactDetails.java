package uk.com.poodle.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ContactDetails {

    String email;
}
