with count_job_success as (
    select jobs.* from jobs inner join
                       (select jobs.id, count(jobs.id) as countJob from jobs
                                                                            inner join job_register on jobs.id  = job_register.job_id
                                                                            inner join profile_status on profile_status.id  = job_register.profile_status_id
                                                                            INNER JOIN Status_Job on Status_Job.id = Jobs.status_job_id
                        where
                            profile_status.name like 'SUCCESS' and job_register.is_delete =0 and jobs.is_delete = 0
                            and Status_Job.status_name IN('APPROVED' ,'RECRUITING')
                            and (jobs.due_date >= current_date) and (jobs.due_date - current_date) <= :p_numberDay
                        group by jobs.id ) temp on temp.id = jobs.id where temp.countJob < jobs.qty_person)
SELECT *  FROM
    (SELECT  ROW_NUMBER() OVER (ORDER BY count_job_success.id asc) as MyRowNumber, count_job_success.*
     FROM count_job_success) tblJobs