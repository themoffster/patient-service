package uk.com.poodle.domain;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class EducationEstablishment {

    String id;
    Address address;
    ContactDetails contactDetails;
    String name;
    EducationEstablishmentType type;
}
