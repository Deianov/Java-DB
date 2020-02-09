-- 01. Find Book Titles
SELECT title FROM books
WHERE SUBSTRING(title, 1, 3) = 'The'
ORDER BY id;

-- 02. Replace Titles 
SELECT  REPLACE(title, 'The', '***')
FROM books
WHERE SUBSTRING(title, 1, 3) = 'The'
ORDER BY id;

-- 03. Sum Cost of All Books
SELECT FORMAT(SUM(`cost`), 2) AS Sum
FROM books;

SELECT ROUND(SUM(`cost`), 2) AS Sum
FROM books;

-- 04. Days Lived
SELECT 
	CONCAT(first_name, ' ',  last_name) as 'Full Name',
	IF(died IS NULL, '(NULL)', timestampdiff(day, born, died))  as 'Days Lived'
FROM authors;

-- 05. Harry Potter Books
SELECT title FROM books
WHERE LOWER(title) LIKE '%harry potter%'
ORDER BY id ASC;


-- Other
-- CASE
SELECT *, 
	CASE author_id
		WHEN 1 THEN 'Recommended'
        WHEN 2 THEN 'Not recommended'
        ELSE '-'
	END
AS Recommended
FROM books;

-- INSERT, LOCATE
SELECT *, INSERT(title, LOCATE('Big', title), 3, 'Small') AS Test
FROM books
WHERE title LIKE 'The Big%';

-- 
USE INFORMATION_SCHEMA;
SELECT * FROM collations;
SELECT * FROM statistics;

-- REGEX	
SELECT * FROM books
WHERE year_of_release REGEXP '[0-9]{4}-[a-zA-Z]{3}-[0-9]{2}';

-- Convert Binary to Decimal
SELECT CONV(10101001, 2, 10) as 'Decimal';
