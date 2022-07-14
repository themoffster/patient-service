package uk.com.poodle.data;

import static uk.com.poodle.Constants.ADDRESS_ID;
import static uk.com.poodle.Constants.ADDRESS_INBOUND_POSTCODE;
import static uk.com.poodle.Constants.ADDRESS_NUMBER;
import static uk.com.poodle.Constants.ADDRESS_OUTBOUND_POSTCODE;
import static uk.com.poodle.Constants.ADDRESS_STREET;
import static uk.com.poodle.Constants.ADDRESS_TOWN;
import static uk.com.poodle.Constants.APPOINTMENT_DATE_TIME;
import static uk.com.poodle.Constants.CONTACT_DETAILS_EMAIL;
import static uk.com.poodle.Constants.CONTACT_DETAILS_ID;
import static uk.com.poodle.Constants.CONTACT_DETAILS_MOBILE_PHONE;
import static uk.com.poodle.Constants.EDUCATION_ESTABLISHMENT_ID;
import static uk.com.poodle.Constants.EDUCATION_ESTABLISHMENT_NAME;
import static uk.com.poodle.Constants.EDUCATION_ESTABLISHMENT_TYPE;
import static uk.com.poodle.Constants.GUARDIAN_DOB;
import static uk.com.poodle.Constants.GUARDIAN_FIRSTNAME;
import static uk.com.poodle.Constants.GUARDIAN_LASTNAME;
import static uk.com.poodle.Constants.GUARDIAN_RELATION;
import static uk.com.poodle.Constants.GUARDIAN_SEX;
import static uk.com.poodle.Constants.PATIENT_DOB;
import static uk.com.poodle.Constants.PATIENT_FIRSTNAME;
import static uk.com.poodle.Constants.PATIENT_ID;
import static uk.com.poodle.Constants.PATIENT_LASTNAME;
import static uk.com.poodle.Constants.PATIENT_SEX;

public class EntityDataFactory {

    public static PatientEntity buildPatientEntity() {
        return buildPatientEntity(null);
    }

    public static PatientEntity buildPatientEntity(String id) {
        return PatientEntity.builder()
            .id(id)
            .address(buildAddressEntity(id == null ? null : ADDRESS_ID))
            .dob(PATIENT_DOB)
            .educationEstablishment(buildEducationEstablishmentEntity(EDUCATION_ESTABLISHMENT_ID))
            .firstname(PATIENT_FIRSTNAME)
            .lastname(PATIENT_LASTNAME)
            .sex(PATIENT_SEX.getCode())
            .build();
    }

    public static AppointmentEntity buildAppointmentEntity() {
        return buildAppointmentEntity(null);
    }

    public static AppointmentEntity buildAppointmentEntity(String id) {
        return AppointmentEntity.builder()
            .id(id)
            .dateTime(APPOINTMENT_DATE_TIME)
            .patientId(PATIENT_ID)
            .build();
    }

    public static GuardianEntity buildGuardianEntity() {
        return buildGuardianEntity(null);
    }

    public static GuardianEntity buildGuardianEntity(String id) {
        return GuardianEntity.builder()
            .id(id)
            .address(buildAddressEntity(id == null ? null : ADDRESS_ID))
            .contactDetails(buildContactDetailsEntity(id == null ? null : CONTACT_DETAILS_ID))
            .dob(GUARDIAN_DOB)
            .firstname(GUARDIAN_FIRSTNAME)
            .lastname(GUARDIAN_LASTNAME)
            .relation(buildRelationEntity())
            .patientId(PATIENT_ID)
            .sex(GUARDIAN_SEX.getCode())
            .build();
    }

    public static EducationEstablishmentEntity buildEducationEstablishmentEntity() {
        return buildEducationEstablishmentEntity(null);
    }

    public static EducationEstablishmentEntity buildEducationEstablishmentEntity(String id) {
        return EducationEstablishmentEntity.builder()
            .id(id)
            .address(buildAddressEntity(id == null ? null : ADDRESS_ID))
            .contactDetails(buildContactDetailsEntity(id == null ? null : CONTACT_DETAILS_ID))
            .name(EDUCATION_ESTABLISHMENT_NAME)
            .type(EDUCATION_ESTABLISHMENT_TYPE)
            .build();
    }

    public static RelationEntity buildRelationEntity() {
        return RelationEntity.builder()
            .id(1L)
            .description(GUARDIAN_RELATION.name())
            .build();
    }

    public static ContactDetailsEntity buildContactDetailsEntity() {
        return buildContactDetailsEntity(null);
    }

    public static ContactDetailsEntity buildContactDetailsEntity(String id) {
        return ContactDetailsEntity.builder()
            .id(id)
            .email(CONTACT_DETAILS_EMAIL)
            .mobilePhone(CONTACT_DETAILS_MOBILE_PHONE)
            .build();
    }

    public static AddressEntity buildAddressEntity() {
        return buildAddressEntity(null);
    }

    public static AddressEntity buildAddressEntity(String id) {
        return AddressEntity.builder()
            .id(id)
            .inboundPostcode(ADDRESS_INBOUND_POSTCODE)
            .number(ADDRESS_NUMBER)
            .outboundPostcode(ADDRESS_OUTBOUND_POSTCODE)
            .street(ADDRESS_STREET)
            .town(ADDRESS_TOWN)
            .build();
    }
}
