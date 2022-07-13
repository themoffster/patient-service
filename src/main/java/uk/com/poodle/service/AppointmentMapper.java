package uk.com.poodle.service;

import lombok.experimental.UtilityClass;
import uk.com.poodle.data.AppointmentEntity;
import uk.com.poodle.domain.Appointment;
import uk.com.poodle.domain.CreateAppointmentParams;

@UtilityClass
class AppointmentMapper {

    public static Appointment map(AppointmentEntity entity) {
        return Appointment.builder()
            .id(entity.getId())
            .dateTime(entity.getDateTime())
            .patientId(entity.getPatientId())
            .build();
    }

    public static AppointmentEntity map(String patientId, CreateAppointmentParams params) {
        return AppointmentEntity.builder()
            .dateTime(params.getDateTime())
            .patientId(patientId)
            .build();
    }
}
