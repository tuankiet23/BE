select * from Jobs
inner  join Status_Job on Status_Job.id = Jobs.status_job_id
where 1 = 1 and Status_Job.status_name  like 'APPROVED' OR Status_Job.status_name  like 'RECRUITING'  and Jobs.is_delete = 0