-- liquibase formatted sql
-- changeset liquibase:3
alter table myblog.users
add column is_enabled bit;
