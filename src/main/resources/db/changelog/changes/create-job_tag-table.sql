--liquibase formatted sql
--changeset <maxlutovinov>:<create-job_tag-table>
CREATE TABLE IF NOT EXISTS public.job_tag(
    job_id bigint NOT NULL,
    tag_id bigint NOT NULL,
    PRIMARY KEY (job_id, tag_id),
    CONSTRAINT fk_job
      FOREIGN KEY(job_id)
	  REFERENCES public.job(id),
	CONSTRAINT fk_tag
      FOREIGN KEY(tag_id)
	  REFERENCES public.tag(id)
);

--rollback DROP TABLE job_tag;
