--liquibase formatted sql
--changeset <maxlutovinov>:<create-tag-table>
CREATE TABLE IF NOT EXISTS public.tag(
    id bigint NOT NULL,
    title character varying(256) NOT NULL,
    PRIMARY KEY (id)
);

--rollback DROP TABLE tag;
