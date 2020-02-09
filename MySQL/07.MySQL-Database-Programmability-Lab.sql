SET GLOBAL log_bin_trust_function_creators = 1;
use `soft_uni`;

-- 1. Count Employees by Town
DELIMITER $$
create function ufn_count_employees_by_town(town_name varchar(50))
returns int
begin
	declare e_count int;
    set e_count := (
        select count(e.employee_id) from employees as e
        inner join addresses as a
        on e.address_id = a.address_id
        inner join towns as t
        on a.town_id = t.town_id
        where t.`name` = town_name
	);
    return e_count;
end;
$$
DELIMITER ;
-- test
select ufn_count_employees_by_town('Sofia') as 'count';

-- 02. Employees Promotion 
DELIMITER $$
create procedure usp_raise_salaries (department_name varchar(50))
begin
	update employees as e
	join departments as d
	on e.department_id = d.department_id
	set salary = salary * 1.05
	where d.`name` = department_name;
end
$$
DELIMITER ;

-- test
SET SQL_SAFE_UPDATES=0;
call usp_raise_salaries('Finance');
SET SQL_SAFE_UPDATES=1;

-- 3. Employees Promotion By ID
DELIMITER $$
create procedure  usp_raise_salary_by_id(id int)
begin
	update employees as e
    set salary = salary * 1.05
    where e.employee_id = id;
end
$$
DELIMITER ;

-- test
SET SQL_SAFE_UPDATES=0;
call usp_raise_salary_by_id(178);
SET SQL_SAFE_UPDATES=1;

-- 4. Triggered
create table deleted_employees(
	employee_id int primary key auto_increment,
    first_name varchar(50),
    last_name varchar(50),
    middle_name varchar(50),
    job_title varchar(50),
    department_id int,
    salary decimal(19,4)
);

DELIMITER $$
create trigger tr_deleted_employees
after delete on employees
for each row
begin
	insert into deleted_employees 
    (`first_name`, `last_name`, `middle_name`, `job_title`, `department_id`, `salary`)
    values
	(old.first_name, old.last_name, old.middle_name, old.job_title, old.department_id, old.salary);
end;
$$
DELIMITER ;

-- test
-- MySQL Disable Foreign Key Checks
SET FOREIGN_KEY_CHECKS=0;
delete from employees where employee_id = 5;
SET FOREIGN_KEY_CHECKS=1;


-- Examples from presentation slides
DELIMITER $$
CREATE PROCEDURE usp_add_numbers(
	first_number INT, second_number INT, OUT result INT)
BEGIN
	SET result := first_number + second_number;
END 
$$
DELIMITER ;

SET @result=0;
CALL usp_add_numbers(5, 6, @result);
SELECT @result as 'result';






