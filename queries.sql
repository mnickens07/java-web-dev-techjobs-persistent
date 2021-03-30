## Part 1: Test it with SQL
id int primary key auto_increment,
employer varchar(100),
name varchar(100),
skills varchar(100);

## Part 2: Test it with SQL
select * from employer where location="St Louis, MO"

## Part 3: Test it with SQL
drop table job

## Part 4: Test it with SQL
select name, description from skill
 inner join job_skills on job_skills.skills_id=skill.id
 where jobs_id is not null;