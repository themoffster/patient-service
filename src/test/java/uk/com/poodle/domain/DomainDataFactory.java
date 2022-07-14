package uk.com.poodle.domain;

import static java.lang.String.join;
import static uk.com.poodle.Constants.ADDRESS_ID;
import static uk.com.poodle.Constants.ADDRESS_INBOUND_POSTCODE;
import static uk.com.poodle.Constants.ADDRESS_NUMBER;
import static uk.com.poodle.Constants.ADDRESS_OUTBOUND_POSTCODE;
import static uk.com.poodle.Constants.ADDRESS_STREET;
import static uk.com.poodle.Constants.ADDRESS_TOWN;
import static uk.com.poodle.Constants.APPOINTMENT_DATE_TIME;
import static uk.com.poodle.Constants.APPOINTMENT_ID;
import static uk.com.poodle.Constants.APPOINTMENT_NOTES;
import static uk.com.poodle.Constants.CONTACT_DETAILS_EMAIL;
import static uk.com.poodle.Constants.CONTACT_DETAILS_ID;
import static uk.com.poodle.Constants.CONTACT_DETAILS_MOBILE_PHONE;
import static uk.com.poodle.Constants.EDUCATION_ESTABLISHMENT_ID;
import static uk.com.poodle.Constants.EDUCATION_ESTABLISHMENT_NAME;
import static uk.com.poodle.Constants.EDUCATION_ESTABLISHMENT_TYPE;
import static uk.com.poodle.Constants.GUARDIAN_DOB;
import static uk.com.poodle.Constants.GUARDIAN_FIRSTNAME;
import static uk.com.poodle.Constants.GUARDIAN_ID;
import static uk.com.poodle.Constants.GUARDIAN_LASTNAME;
import static uk.com.poodle.Constants.GUARDIAN_RELATION;
import static uk.com.poodle.Constants.GUARDIAN_SEX;
import static uk.com.poodle.Constants.PATIENT_DOB;
import static uk.com.poodle.Constants.PATIENT_FIRSTNAME;
import static uk.com.poodle.Constants.PATIENT_ID;
import static uk.com.poodle.Constants.PATIENT_LASTNAME;
import static uk.com.poodle.Constants.PATIENT_SEX;

public class DomainDataFactory {

    public static Patient buildPatient() {
        return Patient.builder()
            .id(PATIENT_ID)
            .address(buildAddress(ADDRESS_ID))
            .dob(PATIENT_DOB)
            .educationEstablishment(buildEducationEstablishment())
            .firstname(PATIENT_FIRSTNAME)
            .lastname(PATIENT_LASTNAME)
            .sex(PATIENT_SEX)
            .build();
    }

    public static AddPatientParams buildAddPatientParams() {
        return AddPatientParams.builder()
            .address(buildAddress())
            .dob(PATIENT_DOB)
            .educationEstablishmentId(EDUCATION_ESTABLISHMENT_ID)
            .firstname(PATIENT_FIRSTNAME)
            .lastname(PATIENT_LASTNAME)
            .sex(PATIENT_SEX)
            .build();
    }

    public static Appointment buildAppointment() {
        return Appointment.builder()
            .id(APPOINTMENT_ID)
            .dateTime(APPOINTMENT_DATE_TIME)
            .patientId(PATIENT_ID)
            .build();
    }

    public static EducationEstablishment buildEducationEstablishment() {
        return EducationEstablishment.builder()
            .id(EDUCATION_ESTABLISHMENT_ID)
            .address(buildAddress(ADDRESS_ID))
            .contactDetails(buildContactDetails(CONTACT_DETAILS_ID))
            .name(EDUCATION_ESTABLISHMENT_NAME)
            .type(EDUCATION_ESTABLISHMENT_TYPE)
            .build();
    }

    public static AddAppointmentParams buildAddAppointmentParams() {
        return AddAppointmentParams.builder()
            .dateTime(APPOINTMENT_DATE_TIME)
            .build();
    }

    public static AddAppointmentNotesParams buildAddAppointmentNotesParams() {
        return AddAppointmentNotesParams.builder()
            .notes(APPOINTMENT_NOTES)
            .build();
    }

    public static Guardian buildGuardian() {
        return Guardian.builder()
            .id(GUARDIAN_ID)
            .address(buildAddress(ADDRESS_ID))
            .contactDetails(buildContactDetails(CONTACT_DETAILS_ID))
            .dob(GUARDIAN_DOB)
            .firstname(GUARDIAN_FIRSTNAME)
            .lastname(GUARDIAN_LASTNAME)
            .relation(GUARDIAN_RELATION)
            .sex(GUARDIAN_SEX)
            .build();
    }

    public static AddGuardianDetailsParams buildAddGuardianDetailsParams() {
        return AddGuardianDetailsParams.builder()
            .address(buildAddress())
            .contactDetails(buildContactDetails())
            .dob(GUARDIAN_DOB)
            .firstname(GUARDIAN_FIRSTNAME)
            .lastname(GUARDIAN_LASTNAME)
            .relation(GUARDIAN_RELATION)
            .sex(GUARDIAN_SEX)
            .build();
    }

    public static AddEducationEstablishmentParams buildAddEducationEstablishmentParams() {
        return AddEducationEstablishmentParams.builder()
            .address(buildAddress())
            .contactDetails(buildContactDetails())
            .name(EDUCATION_ESTABLISHMENT_NAME)
            .type(EDUCATION_ESTABLISHMENT_TYPE)
            .build();
    }

    public static ContactDetails buildContactDetails() {
        return buildContactDetails(null);
    }

    public static ContactDetails buildContactDetails(String id) {
        return ContactDetails.builder()
            .id(id)
            .email(CONTACT_DETAILS_EMAIL)
            .mobilePhone(CONTACT_DETAILS_MOBILE_PHONE)
            .build();
    }

    public static Address buildAddress() {
        return buildAddress(null);
    }

    public static Address buildAddress(String id) {
        return Address.builder()
            .id(id)
            .number(ADDRESS_NUMBER)
            .street(ADDRESS_STREET)
            .town(ADDRESS_TOWN)
            .postcode(join(" ", ADDRESS_OUTBOUND_POSTCODE, ADDRESS_INBOUND_POSTCODE))
            .build();
    }
}
