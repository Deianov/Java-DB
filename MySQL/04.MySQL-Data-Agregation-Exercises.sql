-- 01. Records’ Count
SELECT COUNT(e.id) AS 'count' FROM wizzard_deposits AS e;

-- 02. Longest Magic Wand
SELECT MAX(e.magic_wand_size) AS 'longest_magic_wand' FROM wizzard_deposits AS e;

-- 03. Longest Magic Wand per Deposit Groups
SELECT
	e.deposit_group,
    MAX(e.magic_wand_size) AS longest_magic_wand
FROM wizzard_deposits AS e
GROUP BY e.deposit_group
ORDER BY longest_magic_wand;

-- 04. Smallest Deposit Group per Magic Wand Size
SELECT e.deposit_group
FROM wizzard_deposits AS e
GROUP BY e.deposit_group
ORDER BY AVG(e.magic_wand_size)
LIMIT 1;

-- 05. Deposits Sum
SELECT
	e.deposit_group,
    SUM(e.deposit_amount) AS total_sum
FROM wizzard_deposits AS e
GROUP BY e.deposit_group
ORDER BY total_sum;

-- 06. Deposits Sum for Ollivander Family
SELECT
	e.deposit_group,
    SUM(e.deposit_amount) AS total_sum
FROM wizzard_deposits AS e
WHERE e.magic_wand_creator = 'Ollivander family'
GROUP BY e.deposit_group
ORDER BY deposit_group;

-- 7.Deposits Filter
SELECT
	e.deposit_group,
    SUM(e.deposit_amount) AS total_sum
FROM wizzard_deposits AS e
WHERE e.magic_wand_creator = 'Ollivander family'
GROUP BY e.deposit_group
HAVING `total_sum` < 150000
ORDER BY `total_sum` DESC;

-- 08. Deposit Charge
SELECT
	e.deposit_group,
    e.magic_wand_creator,
    MIN(e.deposit_charge) AS 'min_deposit_charge'
FROM wizzard_deposits AS e
GROUP BY e.deposit_group, e.magic_wand_creator
ORDER BY magic_wand_creator, deposit_group;

-- 09. Age Groups
SELECT 
	CASE 
		WHEN e.age <= 10 THEN '[0-10]'
        WHEN e.age <= 20 THEN '[11-20]'
        WHEN e.age <= 30 THEN '[21-30]'
        WHEN e.age <= 40 THEN '[31-40]'
        WHEN e.age <= 50 THEN '[41-50]'
        WHEN e.age <= 60 THEN '[51-60]'
        WHEN e.age > 60 THEN '[61+]'
	END AS 'age_group',
    COUNT(`age`) AS 'wizard_count'
FROM wizzard_deposits AS e
GROUP BY `age_group`
ORDER BY `age_group`;

-- 10. First Letter
SELECT 
	LEFT(e.first_name, 1) AS 'first_letter'
FROM wizzard_deposits AS e
WHERE e.deposit_group = 'Troll Chest'
GROUP BY `first_letter`
ORDER BY `first_letter`;

-- 11. Average Interest
SELECT
	e.deposit_group,
    e.is_deposit_expired,
    AVG(e.deposit_interest) AS 'average_interest'
FROM wizzard_deposits AS e
WHERE e.deposit_start_date > '1985-01-01'
GROUP BY `deposit_group`, `is_deposit_expired`
ORDER BY `deposit_group` DESC, `is_deposit_expired` ASC;

-- 12. Rich Wizard, Poor Wizard
-- variant 1
CREATE VIEW wd_compare AS
SELECT 
	e1.first_name AS 'host_wizard',
    e1.deposit_amount AS 'host_wizard_deposit',
    e2.first_name AS 'guest_wizard',
    e2.deposit_amount AS 'guest_wizard_deposit',
    e1.deposit_amount - e2.deposit_amount AS 'difference'
FROM wizzard_deposits AS e1, wizzard_deposits AS e2
WHERE e1.id + 1 = e2.id;
SELECT SUM(`difference`) AS 'sum_difference' FROM wd_compare;

-- variant 2
SELECT SUM(e1.deposit_amount - e2.deposit_amount) AS 'difference'
FROM wizzard_deposits AS e1, wizzard_deposits AS e2
WHERE e1.id + 1 = e2.id;

-- variant 3
SELECT SUM(diff.NEXT) AS 'sum_difference' 
FROM 
	(SELECT deposit_amount - (
		SELECT deposit_amount FROM wizzard_deposits WHERE id = e.id +1)
		AS NEXT FROM wizzard_deposits AS e
	) 	
AS diff;

-- 13. Employees Minimum Salaries
SELECT
	e.department_id,
    MIN(salary) AS 'minimum_salary'
FROM employees AS e
WHERE e.department_id IN(2, 5, 7) AND e.hire_date > '2000-01-01'
GROUP BY e.department_id
ORDER BY e.department_id;

-- 14. Employees Average Salaries
CREATE TABLE `employees_paid` AS
SELECT * FROM employees WHERE `salary` > 30000;

DELETE FROM `employees_paid`
WHERE `manager_id` = 42;

UPDATE `employees_paid`
SET salary = salary + 5000
WHERE department_id = 1;

SELECT
	e.department_id,
    AVG(e.salary) AS 'avg_salary'
FROM `employees_paid` AS e
GROUP BY e.department_id
ORDER BY e.department_id;

-- 15. Employees Maximum Salaries
SELECT 
	e.department_id,
    MAX(e.salary) AS 'max_salary'
FROM employees AS e
GROUP BY e.department_id
HAVING `max_salary` NOT BETWEEN 30000 AND 70000
ORDER BY e.department_id;

-- 16. Employees Count Salaries
SELECT
	COUNT(e.employee_id) AS ''
FROM employees AS e
WHERE e.manager_id IS NULL;

-- 17. 3rd Highest Salary
SELECT
	e.department_id,
    e.salary AS 'third_highest_salary'
FROM employees AS e
WHERE
	(SELECT employee_id FROM employees AS ine
		WHERE ine.department_id = e.department_id
        GROUP BY salary
		ORDER BY salary DESC LIMIT 1 OFFSET 2
	) = employee_id
GROUP BY e.department_id
ORDER BY e.department_id ASC;

-- variant 2
SELECT
	e.department_id,
    (SELECT DISTINCT salary FROM employees
		WHERE department_id = e.department_id
        ORDER BY salary DESC LIMIT 2,1
	) AS 'third_highest_salary'

FROM employees AS e
GROUP BY e.department_id
HAVING `third_highest_salary` IS NOT NULL
ORDER BY e.department_id ASC;

-- 18. Salary Challenge
SELECT e.first_name, e.last_name, e.department_id FROM employees AS e
WHERE e.salary > (select AVG(salary) from employees where department_id = e.department_id)
ORDER BY e.department_id, e.employee_id LIMIT 10;

-- 19. Departments Total Salaries
SELECT
	e.department_id,
    sum(e.salary) as 'total_salary'
FROM employees AS e
GROUP BY e.department_id
ORDER BY e.department_id;

