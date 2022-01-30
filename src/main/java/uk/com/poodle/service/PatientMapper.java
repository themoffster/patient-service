package uk.com.poodle.service;

import lombok.experimental.UtilityClass;
import uk.com.poodle.data.PatientEntity;
import uk.com.poodle.domain.Patient;

@UtilityClass
class PatientMapper {

    public static Patient map(PatientEntity entity) {
        return Patient.builder()
            .id(entity.getId())
            .firstname(entity.getFirstname())
            .lastname(entity.getLastname())
            .build();
    }
}
