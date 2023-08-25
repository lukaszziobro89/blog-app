-- liquibase formatted sql
-- changeset liquibase:4
create table myblog.verification_token
(
    id              bigint auto_increment primary key,
    expiration_time datetime(6)  null,
    token           varchar(255) null,
    user_id         bigint       null unique,
    foreign key (user_id) references myblog.users (id)
);
