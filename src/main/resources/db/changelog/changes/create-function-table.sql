--liquibase formatted sql
--changeset <maxlutovinov>:<create-function-table>
CREATE TABLE IF NOT EXISTS public.function(
    id bigint NOT NULL,
    title character varying(256) NOT NULL,
    PRIMARY KEY (id)
);

--rollback DROP TABLE function;
