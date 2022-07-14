package uk.com.poodle.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.Email;

@With
@Value
@Builder
@Jacksonized
public class ContactDetails {

    String id;

    String homePhone;

    String workPhone;

    String mobilePhone;

    @Email(regexp = ".+[@].+[\\.].+")
    String email;
}
