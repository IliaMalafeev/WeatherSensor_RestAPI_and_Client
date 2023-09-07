create table sensor (
    id integer generated by default as identity primary key,
    name varchar(100) not null unique
);

create table measurement (
    id integer generated by default as identity primary key,
    value double precision not null,
    raining boolean not null,
    measured_at timestamp not null,
    sensor varchar(100) references sensor (name)
);