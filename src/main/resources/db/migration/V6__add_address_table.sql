create table address
(
    id                varchar(36) primary key,
    number            varchar(100) not null,
    street            varchar(100) not null,
    town              varchar(100) not null,
    outbound_postcode varchar(4)   not null,
    inbound_postcode  varchar(3)   not null
);

alter table patient add column address_id varchar(36);
alter table patient add constraint fk_address_id foreign key (address_id) references address;
alter table guardian add column address_id varchar(36);
alter table guardian add constraint fk_address_id foreign key (address_id) references address;
