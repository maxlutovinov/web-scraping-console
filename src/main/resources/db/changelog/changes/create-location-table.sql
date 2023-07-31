--liquibase formatted sql
--changeset <maxlutovinov>:<create-location-table>
CREATE TABLE IF NOT EXISTS public.location(
    id bigint NOT NULL,
    title character varying(256) NOT NULL,
    PRIMARY KEY (id)
);

--rollback DROP TABLE location;
