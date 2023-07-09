-- liquibase formatted sql
-- changeset liquibase:2
create table myblog.categories
(
    id          bigint auto_increment primary key,
    name        varchar(255) not null,
    description varchar(255) not null
);

ALTER TABLE myblog.posts
    ADD category_id bigint;

ALTER TABLE myblog.posts
    ADD CONSTRAINT fk_category_id
        FOREIGN KEY (category_id) REFERENCES myblog.posts (id);
