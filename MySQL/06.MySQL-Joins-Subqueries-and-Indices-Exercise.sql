-- 01. Employee Address
select
	e.employee_id,
    e.job_title,
    e.address_id,
    a.address_text
from employees as e
join addresses as a
on e.address_id = a.address_id
order by a.address_id limit 5;

-- 02. Addresses with Towns
select
	e.first_name,
    e.last_name,
    t.`name` as 'town',
    a.address_text
from employees as e
join addresses as a
on e.address_id = a.address_id
join towns as t
on a.town_id = t.town_id
order by e.first_name, e.last_name limit 5;

-- 03. Sales Employee 
select
	e.employee_id,
    e.first_name,
    e.last_name,
    d.`name` as 'department_name'
from employees as e
join departments as d
on e.department_id = d.department_id
where d.`name` = 'Sales'
order by e.employee_id desc;

-- 04. Employee Departments
select
	e.employee_id,
    e.first_name,
    e.salary,
    d.`name` as 'department_name'
from employees as e
join departments as d
on e.department_id = d.department_id
where e.salary > 15000
order by d.department_id desc limit 5;

-- 05.Employees Without Project
select
	e.employee_id,
    e.first_name
from employees as e
left join employees_projects as p
on e.employee_id = p.employee_id
where project_id is null
order by e.employee_id desc limit 3;

-- 06. Employees Hired After
select
	e.first_name,
    e.last_name,
    date_format(e.hire_date, '%Y-%m-%d %H:%i:%s') as 'hire_date',
    d.`name` as 'dept_name'
from employees as e
join departments as d
on e.department_id = d.department_id
where date(e.hire_date) > '1999-1-1' and d.`name` in('Sales', 'Finance')
order by e.hire_date asc;

-- 07. Employees with Project 
select
	e.employee_id,
    e.first_name,
    p.`name` as 'project_name' 
from employees as e
join employees_projects as ep
on e.employee_id = ep.employee_id
join projects as p
on ep.project_id = p.project_id
where p.end_date is null and date(p.start_date) > '2002-08-13'
order by e.first_name, `project_name` limit 5;

-- 08. Employee 24 
select
	e.employee_id,
    e.first_name,
    case
		when year(p.start_date) >= 2005 then null
        else p.`name`
	end as 'project_name'
from employees as e
join employees_projects as ep
on e.employee_id = ep.employee_id
join projects as p
on ep.project_id = p.project_id
where e.employee_id = 24
order by `project_name`;

-- 09. Employee Manager
select
	e.employee_id,
    e.first_name,
    e.manager_id,
    (select first_name from employees as i where i.employee_id = e.manager_id) as 'manager_name'
from employees as e
where e.manager_id in(3, 7)
order by e.first_name asc;

-- 10. Employee Summary 
select
	e.employee_id,
    concat(e.first_name, ' ', e.last_name) as 'employee_name',
    (select concat(i.first_name, ' ', i.last_name) from employees as i where i.employee_id = e.manager_id) as 'manager_name',
    d.`name` as department_name
from employees as e
join departments as d
on e.department_id = d.department_id
where e.manager_id is not null
order by e.employee_id limit 5;

-- 11. Min Average Salary
select 
	avg(salary) as 'min_average_salary'
from employees as e 
group by e.department_id
order by `min_average_salary` asc limit 1;

-- 12. Highest Peaks in Bulgaria
select 
	mc.country_code,
    m.mountain_range,
    p.peak_name,
    p.elevation
from mountains_countries as mc
join peaks as p
on mc.mountain_id = p.mountain_id
join mountains as m
on mc.mountain_id = m.id
where mc.country_code = 'BG' and p.elevation > 2835
order by p.elevation desc;

-- 13. Count Mountain Ranges
select
	mc.country_code,
    count(mc.mountain_id) as 'mountain_range'
from mountains_countries as mc
where mc.country_code in ('US', 'RU', 'BG')
group by `country_code`
order by `mountain_range` desc;

-- 14. Countries with Rivers
select
	c.country_name,
    r.river_name
from countries as c
left join countries_rivers as cr
on c.country_code = cr.country_code
left join rivers as r
on r.id = cr.river_id
where c.continent_code = 'AF'
order by `country_name` limit 5;

-- 15. *Continents and Currencies
select
	c.continent_code,
    c.currency_code,
    count(currency_code) as 'currency_usage'
from countries as c
group by continent_code, currency_code
having `currency_usage` > 1 and `currency_usage` =
	(select count(`currency_code`) as 'count' from countries as i 
		where c.continent_code = i.continent_code
        group by i.currency_code
        order by `count` desc limit 1
    )
order by c.continent_code, c.currency_code;

-- 16. Countries without any Mountains
select
	count(c.continent_code) as `country_count`
from countries as c
left join mountains_countries as mc
on c.country_code = mc.country_code
where mountain_id is null;

-- 17. Highest Peak and Longest River by Country
select 
	c.country_name,
    max(elevation) as 'highest_peak_elevation',
    max(length) as 'longest_river_length'
from countries as c
left join mountains_countries as mc
on c.country_code = mc.country_code
join peaks as p
on mc.mountain_id = p.mountain_id
left join countries_rivers as cr
on cr.country_code = c.country_code
join rivers as r
on cr.river_id = r.id
group by c.country_code
order by 
	`highest_peak_elevation` desc,
    `longest_river_length` desc,
    `country_name` limit 5;