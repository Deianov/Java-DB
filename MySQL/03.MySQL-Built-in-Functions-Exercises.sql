USE soft_uni;

-- 1.	Find Names of All Employees by First Name
SELECT first_name, last_name 
FROM employees
WHERE LOWER(LEFT(`first_name`, 2)) LIKE 'sa%'
ORDER BY employee_id ASC;

-- 02. Find Names of All Employees by Last Name
SELECT first_name, last_name 
FROM employees
WHERE LOWER(`last_name`) LIKE '%ei%'
ORDER BY employee_id ASC;

-- 03. Find First Names of All Employess
SELECT first_name FROM employees
WHERE 
	(department_id = 3 or department_id = 10) and
    year(hire_date) BETWEEN 1995 AND 2005
ORDER BY employee_id ASC;

-- 04. Find All Employees Except Engineers
SELECT first_name, last_name FROM employees
WHERE job_title NOT REGEXP 'engineer'
ORDER BY employee_id ASC;

-- 05. Find Towns with Name Length
SELECT `name` FROM towns
WHERE char_length(`name`) BETWEEN 5 AND 6
ORDER BY `name` ASC;

-- 06. Find Towns Starting With 
SELECT * FROM towns
WHERE LOWER(LEFT(`name`, 1)) IN('m', 'k', 'b', 'e')
ORDER BY `name` ASC;

-- 07. Find Towns Not Starting With
SELECT * FROM towns
WHERE LOWER(LEFT(`name`, 1)) NOT IN('r', 'b', 'd')
ORDER BY `name` ASC;

-- 08. Create View Employees Hired After
CREATE VIEW v_employees_hired_after_2000 AS
SELECT first_name, last_name FROM employees
WHERE YEAR(hire_date) > 2000;

-- 09. Length of Last Name
SELECT first_name, last_name FROM employees
WHERE char_length(last_name) = 5;

-- 10. Countries Holding 'A' 
SELECT country_name, iso_code FROM countries
WHERE country_name REGEXP '[aA].*[aA].*[aA]'
ORDER BY iso_code;

-- 11. Mix of Peak and River Names 
SELECT peak_name, river_name, LOWER(CONCAT(peak_name, SUBSTRING(river_name, 2))) AS 'mix'
FROM peaks, rivers
WHERE LOWER(RIGHT(peak_name, 1)) = LOWER(LEFT(river_name, 1))
ORDER BY mix ASC;

-- 12. Games From 2011 and 2012 Year
SELECT `name`, DATE_FORMAT(`start`, '%Y-%m-%d') AS `start`
FROM games
WHERE YEAR(`start`) BETWEEN 2011 AND 2012
ORDER BY `start`, `name` LIMIT 50;

-- 13. User Email Providers 
SELECT user_name, SUBSTRING(email, POSITION('@' IN email) + 1) AS 'Email Provider' 
FROM users
ORDER BY `Email Provider`, user_name;

-- 14. Get Users with IP Address Like Pattern
SELECT user_name, ip_address FROM users
WHERE ip_address LIKE '___.1%.%.___'
ORDER BY user_name;

-- 15. Show All Games with Duration
SELECT
	`name` AS 'game',
    CASE
		WHEN hour(`start`) >= 0 AND hour(`start`) < 12 THEN 'Morning'
        WHEN hour(`start`) >= 12 AND hour(`start`) < 18 THEN 'Afternoon'
        WHEN hour(`start`) >= 18 AND hour(`start`) < 24 THEN 'Evening'
	END AS 'Part of the Day',
    CASE
		WHEN `duration` <= 3 THEN 'Extra Short'
        WHEN `duration` > 3 AND `duration` <= 6 THEN 'Short'
        WHEN `duration` > 6 AND `duration` <= 10 THEN 'Long'
        ELSE 'Extra Long'
	END AS 'Duration'
FROM games;

-- 16. Orders Table 
SELECT
	product_name,
    order_date,
    DATE_ADD(order_date, INTERVAL 3 DAY) AS 'pay_due',
    DATE_ADD(order_date, INTERVAL 1 MONTH) AS 'deliver_due'
FROM orders;




	