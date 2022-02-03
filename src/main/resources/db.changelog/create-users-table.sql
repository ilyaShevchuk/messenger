create table users
(
    id           varchar(255) not null,
    name         varchar(255),
    login        varchar(255),
    password     varchar(255),
    time_zone_id varchar(10) default '+3',
    primary key (id)
);
