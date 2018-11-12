drop all objects;
commit;

create table product(
    id varchar(36) not null,
    name varchar(255) not null,
    price integer not null,
    primary key (id));
