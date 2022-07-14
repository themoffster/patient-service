package uk.com.poodle.service;

import lombok.experimental.UtilityClass;
import uk.com.poodle.data.EducationEstablishmentEntity;
import uk.com.poodle.data.PatientEntity;
import uk.com.poodle.domain.AddPatientParams;
import uk.com.poodle.domain.Patient;
import uk.com.poodle.domain.Sex;

@UtilityClass
class PatientMapper {

    public static Patient map(PatientEntity entity) {
        return Patient.builder()
            .id(entity.getId())
            .address(AddressMapper.map(entity.getAddress()))
            .dob(entity.getDob())
            .educationEstablishment(EducationEstablishmentMapper.map(entity.getEducationEstablishment()))
            .firstname(entity.getFirstname())
            .lastname(entity.getLastname())
            .sex(Sex.fromCode(entity.getSex()))
            .build();
    }

    public static PatientEntity map(AddPatientParams params, EducationEstablishmentEntity educationEstablishment) {
        return PatientEntity.builder()
            .address(AddressMapper.map(params.getAddress()))
            .dob(params.getDob())
            .educationEstablishment(educationEstablishment)
            .firstname(params.getFirstname())
            .lastname(params.getLastname())
            .sex(params.getSex().getCode())
            .build();
    }
}
