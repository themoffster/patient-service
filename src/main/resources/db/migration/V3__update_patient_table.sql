alter table patient add column dob timestamptz not null;
alter table patient add column sex varchar(1) not null;

create table guardian
(
    id          varchar(36) primary key,
    dob         timestamptz  not null,
    firstname   varchar(100) not null,
    lastname    varchar(100) not null,
    patient_id  varchar(36)  not null,
    sex         varchar(1)   not null,
    relation_id numeric(100) not null
);

create table relation
(
    id          numeric(100) primary key,
    description varchar(100) not null
);

insert into relation (id, description) values (1, 'PARENT');
insert into relation (id, description) values (2, 'GRANDPARENT');
insert into relation (id, description) values (3, 'OTHER');

alter table guardian add constraint fk_patient_id foreign key (patient_id) references patient;
alter table guardian add constraint fk_relation_id foreign key (relation_id) references relation;
