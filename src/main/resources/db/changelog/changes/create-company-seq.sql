--liquibase formatted sql
--changeset <maxlutovinov>:<create-company-seq>
CREATE SEQUENCE IF NOT EXISTS public.company_id_seq INCREMENT 1 START 1 MINVALUE 1;

--rollback DROP SEQUENCE public.company_id_seq;
