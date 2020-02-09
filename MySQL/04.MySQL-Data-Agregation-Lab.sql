-- 1. Departments Info
SELECT
	e.department_id,
    COUNT(e.department_id) AS 'Number of employees'
FROM employees AS e
GROUP BY e.department_id
ORDER BY e.department_id, `Number of employees`;

-- 2. Average Salary
SELECT
	e.department_id,
    ROUND(AVG(e.salary), 2) AS 'Average Salary'
FROM employees AS e
GROUP BY e.department_id;

-- 3. Minimum Salary
SELECT
	e.department_id,
    ROUND(MIN(e.salary), 2) AS 'Min Salary'
FROM employees AS e
GROUP BY e.department_id
HAVING `Min Salary` > 800;

-- 4. Appetizers Count 
SELECT COUNT(e.id) AS 'Count' FROM products AS e
WHERE e.category_id = 2 AND e.price > 8;

-- 5. Menu Prices
SELECT 
	e.category_id AS 'Category_id',
    ROUND(AVG(e.price), 2) AS 'Average Price',
    ROUND(MIN(e.price), 2) AS 'Cheapest Product',
    ROUND(MAX(e.price), 2) AS 'Most Expensive Product'
FROM products AS e
GROUP BY e.category_id;




