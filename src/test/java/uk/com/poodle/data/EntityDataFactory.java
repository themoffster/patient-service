package uk.com.poodle.data;

import static uk.com.poodle.Constants.APPOINTMENT_DATE_TIME;
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
            .dob(PATIENT_DOB)
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
            .dob(GUARDIAN_DOB)
            .firstname(GUARDIAN_FIRSTNAME)
            .lastname(GUARDIAN_LASTNAME)
            .relation(buildRelationEntity())
            .patientId(PATIENT_ID)
            .sex(GUARDIAN_SEX.getCode())
            .build();
    }

    public static RelationEntity buildRelationEntity() {
        return RelationEntity.builder()
            .id(1L)
            .description(GUARDIAN_RELATION.name())
            .build();
    }
}
