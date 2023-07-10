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
insert into myblog.posts (content, description, title) values ( 'content 1', 'description 1', 'title 1' );
insert into myblog.posts (content, description, title) values ( 'content 2', 'description 2', 'title 2' );
insert into myblog.posts (content, description, title) values ( 'content 3', 'description 3', 'title 3' );
insert into myblog.posts (content, description, title) values ( 'content 4', 'description 4', 'title 4' );
insert into myblog.posts (content, description, title) values ( 'content 5', 'description 5', 'title 5' );
insert into myblog.posts (content, description, title) values ( 'content 6', 'description 6', 'title 6' );
insert into myblog.posts (content, description, title) values ( 'content 7', 'description 7', 'title 7' );
insert into myblog.posts (content, description, title) values ( 'content 8', 'description 8', 'title 8' );
insert into myblog.posts (content, description, title) values ( 'content 9', 'description 9', 'title 9' );
insert into myblog.posts (content, description, title) values ( 'content 10', 'description 10', 'title 10' );
insert into myblog.posts (content, description, title) values ( 'content 11', 'description 11', 'title 11' );

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

insert into myblog.categories (name, description) values ( 'category name 1', 'category description 1' );
insert into myblog.categories (name, description) values ( 'category name 2', 'category description 2' );
insert into myblog.categories (name, description) values ( 'category name 3', 'category description 3' );
