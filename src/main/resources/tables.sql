create table if not exists devices
(
	model varchar(800),
	version varchar(800),
	mac varchar(800),
	ssid varchar(800),
	bssid varchar(800),
	rssi varchar(800),
	password varchar(800),
	hash varchar(800),
	id uuid not null,
	ip_address varchar(255)
);

alter table devices owner to postgres;

create table if not exists users
(
	id serial not null,
	name text,
	email varchar(200),
	hash varchar(500)
);

alter table users owner to postgres;

create table if not exists sessions
(
    id serial not null,
    login text,
    session_id text,
    date timestamp
);

alter table sessions owner to postgres;

drop table sessions;

select * from sessions;

select * from users;