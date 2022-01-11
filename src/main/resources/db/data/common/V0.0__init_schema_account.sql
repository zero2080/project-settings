create table admin_user
(
    id       varchar(36) not null,
    role     integer,
    username varchar(255),
    password varchar(255),
    primary key (id)
);

insert into `admin_user` (`id`, `role`, `username`, `password`)
values ('00000001-a000-b000-c000-d00000000000',
        1,
        'admin',
        '$2a$10$NtJP.RGNuWGk3wCUAdTmJOMqBm/CxjiHikJGPqDSlMQ7lRJ9K93ce');

create table collection
(
    id         varchar(36)  not null,
    thumb      varchar(255) not null,
    title      varchar(100),
    type       varchar(20)  not null,
    created_at timestamp,
    updated_at timestamp,
    primary key (id)
);