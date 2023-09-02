create table if not exists phone_number (
    id serial primary key,
    client_id int references client(id),
    phone_num varchar(255)
);