package uk.com.poodle.data;

import static uk.com.poodle.Constants.PATIENT_FIRSTNAME;
import static uk.com.poodle.Constants.PATIENT_LASTNAME;

public class EntityDataFactory {

    public static PatientEntity buildNewPatientEntity() {
        return buildNewPatientEntity(null);
    }

    public static PatientEntity buildNewPatientEntity(String id) {
        return PatientEntity.builder()
            .id(id)
            .firstname(PATIENT_FIRSTNAME)
            .lastname(PATIENT_LASTNAME)
            .build();
    }
}
