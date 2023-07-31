--liquibase formatted sql
--changeset <maxlutovinov>:<create-company-table>
CREATE TABLE IF NOT EXISTS public.company(
    id bigint NOT NULL,
    title character varying(256) NOT NULL,
    url character varying(1024),
    logo character varying(1024) NOT NULL,
    PRIMARY KEY (id)
);

--rollback DROP TABLE company;
