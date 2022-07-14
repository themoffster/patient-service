-- noinspection SqlNoDataSourceInspectionForFile

insert into relation (id, description) values (1, 'PARENT');

insert into address (id, number, street, town, outbound_postcode, inbound_postcode) values ('33dc83d7-54cb-4abd-8faa-2bb8259d7a67', '1', 'Road Street', 'Edinburgh', 'EH12', '1FG');

insert into contact_details (id, mobile_phone, email) values ('841646d2-e6a6-4be2-a014-674bb65aefcd', '07123456789', 'foo@bar.com');

insert into education_establishment (id, name, type, address_id, contact_details_id) values ('1468967e-5ee7-4cfe-81df-794cfd65f8a2', 'Glasgow Primary', 'SCHOOL', '33dc83d7-54cb-4abd-8faa-2bb8259d7a67', '841646d2-e6a6-4be2-a014-674bb65aefcd');

insert into patient (id, firstname, lastname, dob, sex, address_id, education_establishment_id) values ('67150f64-b235-4e07-af6b-4018aabc1e3e', 'Joe', 'Bloggs', '2010-01-01', 'M', '33dc83d7-54cb-4abd-8faa-2bb8259d7a67', '1468967e-5ee7-4cfe-81df-794cfd65f8a2');

insert into guardian (id, firstname, lastname, dob, sex, patient_id, relation_id, contact_details_id, address_id) values ('72181737-7c07-4183-8f1c-9a181eec3006', 'Jane', 'Bloggs', '1980-06-01', 'F', '67150f64-b235-4e07-af6b-4018aabc1e3e', 1, '841646d2-e6a6-4be2-a014-674bb65aefcd', '33dc83d7-54cb-4abd-8faa-2bb8259d7a67');

insert into appointment (id, patient_id, datetime) values ('77e37b83-0483-4ba0-9ff1-acc070313e41', '67150f64-b235-4e07-af6b-4018aabc1e3e', '2030-01-01T09:00');
insert into appointment (id, patient_id, datetime) values ('cf6c5228-31b4-411d-9cb7-f6ec73b7fee8', '67150f64-b235-4e07-af6b-4018aabc1e3e', '2022-01-01T09:00');
