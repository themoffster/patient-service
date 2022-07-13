package uk.com.poodle.service;

import lombok.experimental.UtilityClass;
import uk.com.poodle.data.ContactDetailsEntity;
import uk.com.poodle.domain.ContactDetails;

@UtilityClass
class ContactDetailsMapper {

    public static ContactDetails map(ContactDetailsEntity entity) {
        return ContactDetails.builder()
            .id(entity.getId())
            .email(entity.getEmail())
            .homePhone(entity.getHomePhone())
            .mobilePhone(entity.getMobilePhone())
            .workPhone(entity.getWorkPhone())
            .build();
    }

    public static ContactDetailsEntity map(ContactDetails details) {
        return ContactDetailsEntity.builder()
            .id(details.getId())
            .email(details.getEmail())
            .homePhone(details.getHomePhone())
            .mobilePhone(details.getMobilePhone())
            .workPhone(details.getWorkPhone())
            .build();
    }
}
