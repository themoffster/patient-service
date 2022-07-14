create table education_establishment
(
    id                 varchar(36) primary key,
    type               varchar(20)  not null,
    name               varchar(200) not null,
    address_id         varchar(36)  not null,
    contact_details_id varchar(36)  not null
);

alter table education_establishment add constraint fk_address_id foreign key (address_id) references address;
alter table patient add column education_establishment_id varchar(36) not null;
alter table patient add constraint fk_education_establishment_id foreign key (education_establishment_id) references education_establishment;
