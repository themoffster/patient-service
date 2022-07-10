create table appointment
(
    id         varchar(36) primary key,
    patient_id varchar(100) not null,
    datetime   timestamptz  not null
);

alter table appointment
    add constraint fk_patient_id foreign key (patient_id) references patient;
