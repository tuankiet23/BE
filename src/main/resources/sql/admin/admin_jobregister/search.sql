select tabwithrownum.id, tabwithrownum.cv_file, tabwithrownum.date_interview, tabwithrownum.date_register, tabwithrownum.is_delete, tabwithrownum.method_interview, tabwithrownum.job_id, tabwithrownum.profile_status_id, tabwithrownum.user_id
from
(select job_register.*, rownum ROWNR
from job_register, jobs, users, profile_status
where 1=1
and job_register.job_id=jobs.id
and job_register.user_id=users.id
and job_register.profile_status_id = profile_status.id
and job_register.is_delete =0
