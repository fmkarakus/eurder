create table orders
(
    id          integer primary key,
    customer_id integer references person,
    total_price float
);
create sequence orders_seq start with 1 increment by 1;

create table item_group
(
    id            integer primary key,
    item_id       integer references item,
    amount        integer not null,
    shipping_time date    not null,
    total_price   float,
    orders_id      integer references orders
);

create sequence item_group_seq start with 1 increment by 1;