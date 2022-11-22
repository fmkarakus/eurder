create table item
(
    id          integer,
    name        varchar(64) not null,
    description varchar(64) not null,
    price       float,
    amount      integer  not null,
    constraint pk_item primary key (id)
);

create sequence item_seq start with 1 increment by 1;