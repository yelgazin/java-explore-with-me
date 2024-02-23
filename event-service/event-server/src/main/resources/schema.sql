drop table if exists participation_request;
drop table if exists event_compilation;
drop table if exists compilation;
drop table if exists event;
drop table if exists category;
drop table if exists users;

create table users
(
    id    bigint generated by default as identity primary key,
    email varchar(254) not null,
    name  varchar(250) not null,

    constraint users_email_unique unique (email)
);

create table category
(
    id   bigint generated by default as identity primary key,
    name varchar(50) not null,

    constraint category_name_unique unique (name)
);

create table event
(
    id                 bigint generated by default as identity primary key,
    title              varchar(120)                 not null,
    annotation         varchar(2000)                not null,
    description        varchar(7000)               not null,
    category_id        bigint                      not null
        constraint event_category_foreign_key
            references category (id) on delete cascade,
    initiator_id       bigint                      not null
        constraint event_users_foreign_key
            references users (id) on delete cascade,
    date               timestamp without time zone not null,
    location_lat       float                       not null,
    location_lon       float                       not null,
    paid               boolean                     not null,
    participant_limit  int                         not null,
    views              int                         not null,
    request_moderation boolean                     not null,
    state              varchar(50)                 not null,
    created            timestamp without time zone not null,
    published          timestamp without time zone
);

create table compilation
(
    id     bigint generated by default as identity primary key,
    title  varchar(50) not null,
    pinned boolean     not null
);

create table event_compilation
(
    event_id       bigint not null
        constraint event_compilation_event_foreign_key
            references event (id) on delete cascade,
    compilation_id bigint not null
        constraint event_compilation_compilation_foreign_key
            references compilation (id) on delete cascade,

    constraint event_compilation_unique unique (event_id, compilation_id)
);

create table participation_request
(
    id           bigint generated by default as identity primary key,
    event_id     bigint      not null
        constraint participation_request_event_foreign_key
            references event (id) on delete cascade,
    requester_id bigint      not null
        constraint participation_request_users_foreign_key
            references users (id) on delete cascade,
    state        varchar(50) not null
);