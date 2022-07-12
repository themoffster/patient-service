package uk.com.poodle;

import uk.com.poodle.domain.Relation;
import uk.com.poodle.domain.Sex;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static uk.com.poodle.domain.Relation.PARENT;
import static uk.com.poodle.domain.Sex.FEMALE;
import static uk.com.poodle.domain.Sex.MALE;

public class Constants {

    public static final String APPOINTMENT_ID = "77e37b83-0483-4ba0-9ff1-acc070313e41";
    public static final LocalDateTime APPOINTMENT_DATE_TIME = LocalDateTime.of(2030, 1, 1, 9, 0);

    public static final String GUARDIAN_ID = "72181737-7c07-4183-8f1c-9a181eec3006";
    public static final LocalDate GUARDIAN_DOB = LocalDate.of(1980, 6, 1);
    public static final String GUARDIAN_FIRSTNAME = "Jane";
    public static final String GUARDIAN_LASTNAME = "Bloggs";
    public static final Relation GUARDIAN_RELATION = PARENT;
    public static final Sex GUARDIAN_SEX = FEMALE;

    public static final String PATIENT_ID = "67150f64-b235-4e07-af6b-4018aabc1e3e";
    public static final LocalDate PATIENT_DOB = LocalDate.of(2010, 1, 1);
    public static final String PATIENT_FIRSTNAME = "Joe";
    public static final String PATIENT_LASTNAME = "Bloggs";
    public static final Sex PATIENT_SEX = MALE;
}
