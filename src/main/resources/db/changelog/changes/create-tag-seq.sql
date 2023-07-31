--liquibase formatted sql
--changeset <maxlutovinov>:<create-tag-seq>
CREATE SEQUENCE IF NOT EXISTS public.tag_id_seq INCREMENT 1 START 1 MINVALUE 1;

--rollback DROP SEQUENCE public.tag_id_seq;
