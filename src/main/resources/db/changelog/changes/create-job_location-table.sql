--liquibase formatted sql
--changeset <maxlutovinov>:<create-job_location-table>
CREATE TABLE IF NOT EXISTS public.job_location(
    job_id bigint NOT NULL,
    location_id bigint NOT NULL,
    PRIMARY KEY (job_id, location_id),
    CONSTRAINT fk_job
      FOREIGN KEY(job_id)
	  REFERENCES public.job(id),
	CONSTRAINT fk_location
      FOREIGN KEY(location_id)
	  REFERENCES public.location(id)
);

--rollback DROP TABLE job_location;
