-- noinspection SqlNoDataSourceInspectionForFile

insert into relation (id, description)
values (1, 'PARENT');

insert into patient (id, firstname, lastname, dob, sex)
values ('67150f64-b235-4e07-af6b-4018aabc1e3e', 'Joe', 'Bloggs', '2010-01-01', 'M');

insert into guardian (id, firstname, lastname, dob, sex, patient_id, relation_id, contact_details_id)
values ('72181737-7c07-4183-8f1c-9a181eec3006', 'Jane', 'Bloggs', '1980-06-01', 'F',
        '67150f64-b235-4e07-af6b-4018aabc1e3e', 1, '841646d2-e6a6-4be2-a014-674bb65aefcd');

insert into contact_details (id, mobile_phone, email)
values ('841646d2-e6a6-4be2-a014-674bb65aefcd', '07123456789', 'foo@bar.com');

insert into appointment (id, patient_id, datetime)
values ('77e37b83-0483-4ba0-9ff1-acc070313e41', '67150f64-b235-4e07-af6b-4018aabc1e3e', '2030-01-01T09:00');
insert into appointment (id, patient_id, datetime)
values ('cf6c5228-31b4-411d-9cb7-f6ec73b7fee8', '67150f64-b235-4e07-af6b-4018aabc1e3e', '2022-01-01T09:00');
