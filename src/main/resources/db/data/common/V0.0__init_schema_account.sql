create table admin_user
(
    id       varchar(255) not null,
    password varchar(255),
    role     integer,
    username varchar(255),
    primary key (id)
);

insert into `admin_user` (`id`,`password`,`role`,`username`)
values (
        '00000001-a000-b000-c000-d00000000000',
        '$2a$10$NtJP.RGNuWGk3wCUAdTmJOMqBm/CxjiHikJGPqDSlMQ7lRJ9K93ce',
        1,
        'admin'
       );

create table notice
(
    id         varchar(255) not null,
    content    varchar(3000) not null,
    created_at timestamp,
    title      varchar(255),
    updated_at timestamp,
    primary key (id)
);