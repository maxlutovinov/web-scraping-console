--liquibase formatted sql
--changeset <maxlutovinov>:<create-job_function-table>
CREATE TABLE IF NOT EXISTS public.job_function(
    job_id bigint NOT NULL,
    function_id bigint NOT NULL,
    PRIMARY KEY (job_id, function_id),
    CONSTRAINT fk_job
      FOREIGN KEY(job_id)
	  REFERENCES public.job(id),
	CONSTRAINT fk_function
      FOREIGN KEY(function_id)
	  REFERENCES public.location(id)
);

--rollback DROP TABLE job_function;
