--liquibase formatted sql
--changeset <maxlutovinov>:<create-job-table>
create TABLE IF NOT EXISTS public.job(
    id bigint NOT NULL,
    title character varying(256) NOT NULL,
    posted_date bigint NOT NULL,
    company_id bigint NOT NULL,
    job_url TEXT NOT NULL,
    job_application_url TEXT DEFAULT 'NOT_FOUND',
    description TEXT DEFAULT 'NOT_FOUND',
    PRIMARY KEY(id),
    CONSTRAINT fk_company
      FOREIGN KEY(company_id)
	  REFERENCES public.company(id)
);

--rollback DROP TABLE job;
