create table driver
(
    id            int8 not null,
    city          varchar(255),
    code          varchar(255),
    country       varchar(255),
    phone         varchar(255),
    street        varchar(255),
    company_id    int8,
    contract_type varchar(255),
    firstname     varchar(255),
    hired_date    timestamp,
    journey_id    int8,
    lastname      varchar(255),
    is_valid      boolean,
    user_id       int8,
    primary key (id)
);
create table t_user
(
    id        int8         not null,
    email     varchar(255),
    password  varchar(255) not null,
    username  varchar(255) not null,
    driver_id int8,
    primary key (id)
);
alter table driver
    add constraint FKiomqu4nh213untsk1xlfmlo8p foreign key (user_id) references t_user;
alter table t_user
    add constraint FKjvqo5k9a4dn5cgpwx3vlossam foreign key (driver_id) references driver;
alter table driver
    drop constraint FKiomqu4nh213untsk1xlfmlo8p;
alter table t_user
    drop constraint FKjvqo5k9a4dn5cgpwx3vlossam;
drop table if exists driver cascade;
drop table if exists t_user cascade;
drop sequence if exists hibernate_sequence;
create sequence hibernate_sequence start 1 increment 1
