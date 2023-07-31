--liquibase formatted sql
--changeset <maxlutovinov>:<create-function-seq>
CREATE SEQUENCE IF NOT EXISTS public.function_id_seq INCREMENT 1 START 1 MINVALUE 1;

--rollback DROP SEQUENCE public.function_id_seq;
