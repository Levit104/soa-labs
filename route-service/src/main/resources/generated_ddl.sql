create table location
(
    id   bigint generated by default as identity,
    x    bigint check (x <= 251)               not null,
    y    integer                               not null,
    z    bigint                                not null,
    name varchar(255) check (trim(name) <> '') not null,
    primary key (id)
);

create table route
(
    id            integer generated by default as identity,
    creation_date timestamp(6)                          not null,
    distance      integer check (distance > 1)          not null,
    name          varchar(255) check (trim(name) <> '') not null,
    from_id       bigint                                not null,
    to_id         bigint                                not null,
    primary key (id)
);

alter table if exists location
    drop constraint if exists UKsahixf1v7f7xns19cbg12d946;

alter table if exists location
    add constraint UKsahixf1v7f7xns19cbg12d946 unique (name);

alter table if exists route
    add constraint FK4ym959gsev0tmcltrwp5ybhp4 foreign key (from_id) references location;

alter table if exists route
    add constraint FKl6v6y4x58d5f2veew53bccdjv foreign key (to_id) references location;
