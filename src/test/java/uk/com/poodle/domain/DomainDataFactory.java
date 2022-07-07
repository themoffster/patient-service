package uk.com.poodle.domain;

import uk.com.poodle.rest.domain.CreatePatientParams;

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

    public static CreatePatientParams buildNewCreatePatientParams() {
        return CreatePatientParams.builder()
            .firstname(PATIENT_FIRSTNAME)
            .lastname(PATIENT_LASTNAME)
            .build();
    }
}
