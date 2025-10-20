--liquibase formatted sql

--changeset javalin:1
ALTER TABLE users
    add column password varchar(128) default '{noop}123';

--changeset javalin:2
ALTER TABLE users_aud
    add column password varchar(128);