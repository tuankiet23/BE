select DISTINCT *
from job_register, jobs, users, profile_status
where 1=1 and job_register.job_id=jobs.id
and job_register.user_id=users.id
and job_register.profile_status_id = profile_status.id
and job_register.is_delete =0
