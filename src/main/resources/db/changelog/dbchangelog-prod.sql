-- liquibase formatted sql
-- changeset liquibase:1
create table myblog.posts
(
    id          bigint auto_increment primary key,
    content     varchar(255) not null,
    description varchar(255) not null,
    title       varchar(255) not null
);

create table myblog.comments
(
    id      bigint auto_increment primary key,
    body    varchar(255) null,
    email   varchar(255) null,
    name    varchar(255) null,
    post_id bigint       null,
    foreign key (post_id) references myblog.posts (id)
);

create table myblog.users
(
    id       bigint auto_increment primary key,
    name     varchar(255),
    username varchar(255) not null unique,
    email    varchar(255) not null unique,
    password varchar(255) not null
);

create table myblog.roles
(
    id   bigint auto_increment primary key,
    name varchar(255)
);

create table myblog.users_roles
(
    user_id bigint not null,
    role_id bigint not null,
    FOREIGN KEY (user_id) REFERENCES myblog.users (id),
    FOREIGN KEY (role_id) REFERENCES myblog.roles (id)
);

INSERT INTO myblog.users(name, username, email, password)
VALUES ('admin', 'admin', 'admin@admin.com', '$2a$10$7.MLEFh1cDpBl4609G3vb.PFoHTjvSkiSNqpBya2FupFTL0V34H2e');

INSERT INTO myblog.roles(name) VALUES ('ROLE_ADMIN');
INSERT INTO myblog.roles(name) VALUES ('ROLE_USER');

INSERT INTO myblog.users_roles VALUES (1, 1);
