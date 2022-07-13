package uk.com.poodle.domain;

import static uk.com.poodle.Constants.APPOINTMENT_DATE_TIME;
import static uk.com.poodle.Constants.APPOINTMENT_ID;
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
            .dob(PATIENT_DOB)
            .firstname(PATIENT_FIRSTNAME)
            .lastname(PATIENT_LASTNAME)
            .sex(PATIENT_SEX)
            .build();
    }

    public static CreatePatientParams buildCreatePatientParams() {
        return CreatePatientParams.builder()
            .dob(PATIENT_DOB)
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

    public static CreateAppointmentParams buildCreateAppointmentParams() {
        return CreateAppointmentParams.builder()
            .dateTime(APPOINTMENT_DATE_TIME)
            .build();
    }

    public static Guardian buildGuardian() {
        return Guardian.builder()
            .id(GUARDIAN_ID)
            .dob(GUARDIAN_DOB)
            .firstname(GUARDIAN_FIRSTNAME)
            .lastname(GUARDIAN_LASTNAME)
            .relation(GUARDIAN_RELATION)
            .sex(GUARDIAN_SEX)
            .build();
    }

    public static AddGuardianDetailsParams buildAddGuardianDetailsParams() {
        return AddGuardianDetailsParams.builder()
            .dob(GUARDIAN_DOB)
            .firstname(GUARDIAN_FIRSTNAME)
            .lastname(GUARDIAN_LASTNAME)
            .relation(GUARDIAN_RELATION)
            .sex(GUARDIAN_SEX)
            .build();
    }
}
