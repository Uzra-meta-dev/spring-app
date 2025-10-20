--liquibase formatted sql

--changeset javalin:1
ALTER TABLE users
add column image varchar(64);

--changeset javalin:2
ALTER TABLE users_aud
add COLUMN image varchar(64);
