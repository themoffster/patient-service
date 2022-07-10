package uk.com.poodle.data;

import static uk.com.poodle.Constants.APPOINTMENT_DATE_TIME;
import static uk.com.poodle.Constants.PATIENT_FIRSTNAME;
import static uk.com.poodle.Constants.PATIENT_ID;
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

    public static AppointmentEntity buildNewAppointmentEntity() {
        return buildNewAppointmentEntity(null);
    }

    public static AppointmentEntity buildNewAppointmentEntity(String id) {
        return AppointmentEntity.builder()
            .id(id)
            .dateTime(APPOINTMENT_DATE_TIME)
            .patientId(PATIENT_ID)
            .build();
    }
}
