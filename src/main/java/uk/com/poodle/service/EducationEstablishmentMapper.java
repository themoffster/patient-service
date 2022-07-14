package uk.com.poodle.service;

import lombok.experimental.UtilityClass;
import uk.com.poodle.data.EducationEstablishmentEntity;
import uk.com.poodle.domain.AddEducationEstablishmentParams;
import uk.com.poodle.domain.EducationEstablishment;

@UtilityClass
class EducationEstablishmentMapper {

    public static EducationEstablishment map(EducationEstablishmentEntity entity) {
        return EducationEstablishment.builder()
            .id(entity.getId())
            .address(AddressMapper.map(entity.getAddress()))
            .contactDetails(ContactDetailsMapper.map(entity.getContactDetails()))
            .name(entity.getName())
            .type(entity.getType())
            .build();
    }

    public static EducationEstablishmentEntity map(EducationEstablishment educationEstablishment) {
        return EducationEstablishmentEntity.builder()
            .id(educationEstablishment.getId())
            .address(AddressMapper.map(educationEstablishment.getAddress()))
            .contactDetails(ContactDetailsMapper.map(educationEstablishment.getContactDetails()))
            .name(educationEstablishment.getName())
            .type(educationEstablishment.getType())
            .build();
    }

    public static EducationEstablishmentEntity map(AddEducationEstablishmentParams params) {
        return EducationEstablishmentEntity.builder()
            .address(AddressMapper.map(params.getAddress()))
            .contactDetails(ContactDetailsMapper.map(params.getContactDetails()))
            .name(params.getName())
            .type(params.getType())
            .build();
    }
}
