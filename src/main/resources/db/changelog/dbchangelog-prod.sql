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
