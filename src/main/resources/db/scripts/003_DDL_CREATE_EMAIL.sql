create table if not exists email (
    id serial primary key,
    client_id int references client(id),
    email varchar(255)
);