create table contact_details
(
    id           varchar(36) primary key,
    home_phone   varchar(20),
    work_phone   varchar(20),
    mobile_phone varchar(20),
    email        varchar(200)
);

alter table guardian add column contact_details_id varchar(36) not null;
