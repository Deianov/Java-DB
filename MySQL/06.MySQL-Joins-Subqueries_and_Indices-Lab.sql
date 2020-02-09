-- 1. Managers 
select 
	e.employee_id,
    concat(e.first_name, ' ', e.last_name) as full_name,
    d.department_id,
    d.`name` as 'department_name'
from employees as e
right join departments as d
on e.employee_id = d.manager_id
order by e.employee_id limit 5;

-- 2. Towns and Addresses
select
	t.town_id,
    t.`name` as 'town_name',
    a.address_text
from towns as t
join addresses as a
on t.town_id = a.town_id
where t.town_id in(9, 15, 32)
order by t.town_id, a.address_id;

-- 3. Employees Without Managers
select
	e.employee_id,
    e.first_name,
    e.last_name,
    e.department_id,
    e.salary
from employees as e
where e.manager_id is null;

-- 4. High Salary
select 
	count(e.employee_id) as 'count'
from employees as e
where e.salary > 
(
	select avg(salary) as 'average_salary'
    from employees
);