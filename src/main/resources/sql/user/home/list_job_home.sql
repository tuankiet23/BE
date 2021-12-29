SELECT *  FROM
    (SELECT  ROW_NUMBER() OVER (ORDER BY jobs.id asc) as MyRowNumber, jobs.*
     FROM jobs INNER  JOIN Status_Job on Status_Job.id = Jobs.status_job_id
     WHERE Status_Job.status_name IN('APPROVED' ,'RECRUITING')  AND Jobs.is_delete = 0 and (current_date <= JOBs.due_date)
    ) tblJobs
WHERE MyRowNumber BETWEEN ( ((:pageNumber - 1) * :pageSize )+ 1) AND :pageNumber*:pageSize

