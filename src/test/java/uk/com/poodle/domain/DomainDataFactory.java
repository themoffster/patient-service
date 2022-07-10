package uk.com.poodle.domain;

import static uk.com.poodle.Constants.APPOINTMENT_DATE_TIME;
import static uk.com.poodle.Constants.APPOINTMENT_ID;
import static uk.com.poodle.Constants.PATIENT_FIRSTNAME;
import static uk.com.poodle.Constants.PATIENT_ID;
import static uk.com.poodle.Constants.PATIENT_LASTNAME;

public class DomainDataFactory {

    public static Patient buildNewPatient() {
        return Patient.builder()
            .id(PATIENT_ID)
            .firstname(PATIENT_FIRSTNAME)
            .lastname(PATIENT_LASTNAME)
            .build();
    }

    public static CreatePatientParams buildNewCreatePatientParams() {
        return CreatePatientParams.builder()
            .firstname(PATIENT_FIRSTNAME)
            .lastname(PATIENT_LASTNAME)
            .build();
    }

    public static Appointment buildNewAppointment() {
        return Appointment.builder()
            .id(APPOINTMENT_ID)
            .dateTime(APPOINTMENT_DATE_TIME)
            .patientId(PATIENT_ID)
            .build();
    }

    public static CreateAppointmentParams buildNewCreateAppointmentParams() {
        return CreateAppointmentParams.builder()
            .dateTime(APPOINTMENT_DATE_TIME)
            .patientId(PATIENT_ID)
            .build();
    }
}
