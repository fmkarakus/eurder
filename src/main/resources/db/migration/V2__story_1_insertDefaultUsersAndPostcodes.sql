-- INSERT VALUES INTO POSTAL_CODE
insert into postal_code
values ('1111', 'city');

insert into postal_code
values ('3020', 'Herent');

insert into postal_code
values ('3018', 'Wijgmaal');

insert into postal_code
values ('3000', 'Leuven');

insert into postal_code
values ('3010', 'Kessel-Lo');

insert into postal_code
values ('9000', 'Ghent');

insert into postal_code
values ('1000', 'Bruxelles');

insert into postal_code
values ('2000', 'Antwerp');

insert into postal_code
values ('3500', 'Hasselt');

-- INSERT ADMIN

insert into person
values (nextval('person_seq'),'admin','surname','Street','45','3500', 'admin@eurder.com','047896521','ADMIN','password')