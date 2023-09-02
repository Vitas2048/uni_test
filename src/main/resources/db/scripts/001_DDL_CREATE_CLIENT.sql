create table if not exists client (
    id serial primary key not null,
    name varchar(255) unique not null
);