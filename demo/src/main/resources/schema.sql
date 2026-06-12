drop table if exists group_users;
drop table if exists groups;
drop table if exists users;
drop table if exists roles;

create table roles (
    id bigint not null auto_increment primary key,
    name varchar(255) not null unique
);

create table users (
    id bigint not null auto_increment primary key,
    username varchar(255),
    email varchar(255) unique,
    password varchar(255),
    role varchar(255),
    telephone varchar(20),
    status varchar(20) default 'ACTIVE'
);

create table groups (
    id bigint not null auto_increment primary key,
    name varchar(255) unique,
    description varchar(255)
);

create table group_users (
    group_id bigint not null,
    user_id bigint not null,
    primary key (group_id, user_id),
    foreign key (group_id) references groups(id) on delete cascade,
    foreign key (user_id) references users(id) on delete cascade
);
