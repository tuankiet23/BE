select tabwithrownum.id, tabwithrownum.address_work, tabwithrownum.create_date, tabwithrownum.description, tabwithrownum.due_date, tabwithrownum.interrest, tabwithrownum.is_delete, tabwithrownum.job_name, tabwithrownum.number_experience, tabwithrownum.qty_person, tabwithrownum.salary_max, tabwithrownum.salary_min, tabwithrownum.skills, tabwithrownum.start_recruitment_date, tabwithrownum.views, tabwithrownum.academic_level_id, tabwithrownum.contact_id, tabwithrownum.create_id, tabwithrownum.job_position_id, tabwithrownum.level_id, tabwithrownum.method_work_id, tabwithrownum.status_job_id
from(
select jobs.*, rownum ROWNR
from jobs
left join academic_level on jobs.academic_level_id=academic_level.id
left join users on jobs.academic_level_id=users.id
left join job_position on jobs.academic_level_id=job_position.id
left join level_rank on jobs.academic_level_id=level_rank.id
left join method_work on jobs.academic_level_id=method_work.id
left join status_job on jobs.academic_level_id=status_job.id
where jobs.is_delete=0