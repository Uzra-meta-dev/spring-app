--liquibase formatted sql

--changeset javalin:1
ALTER TABLE users_aud
drop constraint users_aud_username_key;

--changeset javalin:2
ALTER TABLE users_aud
ALTER COLUMN username DROP NOT NULL ;
