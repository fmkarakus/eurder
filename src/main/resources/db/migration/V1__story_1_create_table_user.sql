create table postal_code
(
    postcode varchar(16) primary key,
    city     varchar(32) not null unique
);

create table person
(
    id           integer,
    first_name   varchar(64) not null,
    last_name    varchar(64) not null,
    street_name  varchar(32) not null,
    house_number varchar(8) not null,
    postcode     varchar(16) references postal_code,
    email        varchar(64) not null,
    phone_number varchar(16) not null,
    role         varchar(16) not null,
    password     varchar(64) not null,
    constraint pk_car primary key (id)
);

create sequence person_seq start with 1 increment by 1;