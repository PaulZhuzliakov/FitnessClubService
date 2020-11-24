create table clients
(
    id serial not null,
    club_card_number serial not null,
    last_name varchar(32) not null,
    first_name varchar(32) not null,
    middle_name varchar(32) not null
);

create unique index clients_club_card_number_uindex
    on clients (club_card_number);

create unique index clients_id_uindex
    on clients (id);

alter table clients
    add constraint clients_pk
        primary key (id);