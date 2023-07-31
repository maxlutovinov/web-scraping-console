--liquibase formatted sql
--changeset <maxlutovinov>:<create-job-seq>
CREATE SEQUENCE IF NOT EXISTS public.job_id_seq INCREMENT 1 START 1 MINVALUE 1;

--rollback DROP SEQUENCE public.job_id_seq;
