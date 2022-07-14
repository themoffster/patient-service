package uk.com.poodle;

import uk.com.poodle.domain.EducationEstablishmentType;
import uk.com.poodle.domain.Relation;
import uk.com.poodle.domain.Sex;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static uk.com.poodle.domain.EducationEstablishmentType.SCHOOL;
import static uk.com.poodle.domain.Relation.PARENT;
import static uk.com.poodle.domain.Sex.FEMALE;
import static uk.com.poodle.domain.Sex.MALE;

public class Constants {

    public static final String ADDRESS_ID = "33dc83d7-54cb-4abd-8faa-2bb8259d7a67";
    public static final String ADDRESS_NUMBER = "1";
    public static final String ADDRESS_STREET = "Road Street";
    public static final String ADDRESS_TOWN = "Edinburgh";
    public static final String ADDRESS_OUTBOUND_POSTCODE = "EH12";
    public static final String ADDRESS_INBOUND_POSTCODE = "1FG";

    public static final String APPOINTMENT_ID = "77e37b83-0483-4ba0-9ff1-acc070313e41";
    public static final LocalDateTime APPOINTMENT_DATE_TIME = LocalDateTime.of(2030, 1, 1, 9, 0);
    public static final String APPOINTMENT_NOTES = "This is a dummy note.";

    public static final String CONTACT_DETAILS_ID = "841646d2-e6a6-4be2-a014-674bb65aefcd";
    public static final String CONTACT_DETAILS_EMAIL = "foo@bar.com";
    public static final String CONTACT_DETAILS_MOBILE_PHONE = "07123456789";

    public static final String EDUCATION_ESTABLISHMENT_ID = "1468967e-5ee7-4cfe-81df-794cfd65f8a2";
    public static final String EDUCATION_ESTABLISHMENT_NAME = "Glasgow Primary";
    public static final EducationEstablishmentType EDUCATION_ESTABLISHMENT_TYPE = SCHOOL;

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
