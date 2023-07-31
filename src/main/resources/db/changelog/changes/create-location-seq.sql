--liquibase formatted sql
--changeset <maxlutovinov>:<create-location-seq>
CREATE SEQUENCE IF NOT EXISTS public.location_id_seq INCREMENT 1 START 1 MINVALUE 1;

--rollback DROP SEQUENCE public.location_id_seq;
