package uk.com.poodle.domain;

import static uk.com.poodle.Constants.PATIENT_FIRSTNAME;
import static uk.com.poodle.Constants.PATIENT_ID;
import static uk.com.poodle.Constants.PATIENT_LASTNAME;

public class DomainDataFactory {

    public static Patient buildNewPatient() {
        return Patient.builder()
            .id(PATIENT_ID)
            .firstname(PATIENT_FIRSTNAME)
            .lastname(PATIENT_LASTNAME)
            .build();
    }
}
