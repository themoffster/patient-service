package uk.com.poodle.service;

import lombok.experimental.UtilityClass;
import uk.com.poodle.data.GuardianEntity;
import uk.com.poodle.data.RelationEntity;
import uk.com.poodle.domain.AddGuardianDetailsParams;
import uk.com.poodle.domain.Guardian;
import uk.com.poodle.domain.Relation;
import uk.com.poodle.domain.Sex;

@UtilityClass
class GuardianMapper {

    public static Guardian map(GuardianEntity entity) {
        return Guardian.builder()
            .id(entity.getId())
            .address(AddressMapper.map(entity.getAddress()))
            .contactDetails(ContactDetailsMapper.map(entity.getContactDetails()))
            .dob(entity.getDob())
            .firstname(entity.getFirstname())
            .lastname(entity.getLastname())
            .relation(Relation.valueOf(entity.getRelation().getDescription()))
            .sex(Sex.fromCode(entity.getSex()))
            .build();
    }

    public static GuardianEntity map(String patientId, AddGuardianDetailsParams params) {
        var relation = Relation.valueOf(params.getRelation().name());
        return GuardianEntity.builder()
            .address(AddressMapper.map(params.getAddress()))
            .contactDetails(ContactDetailsMapper.map(params.getContactDetails()))
            .dob(params.getDob())
            .firstname(params.getFirstname())
            .lastname(params.getLastname())
            .patientId(patientId)
            .relation(RelationEntity.builder()
                .id(relation.getId())
                .description(relation.name())
                .build())
            .sex(params.getSex().getCode())
            .build();
    }
}
