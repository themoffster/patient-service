package uk.com.poodle.domain;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class ContactDetails {

    String id;
    String homePhone;
    String workPhone;
    String mobilePhone;
    String email;
}
