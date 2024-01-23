--liquibase formatted sql
--changeset <maxlutovinov>:<create-company-table>
CREATE TABLE IF NOT EXISTS public.company(
    id bigint NOT NULL,
    title character varying(256) NOT NULL,
    logo TEXT DEFAULT 'NOT_FOUND',
    PRIMARY KEY (id)
);

--rollback DROP TABLE company;
