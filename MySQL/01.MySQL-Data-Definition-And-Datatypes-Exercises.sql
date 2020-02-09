CREATE DATABASE minions;
USE minions;

-- ----------------------------------------------------------------
-- 01. Create Tables
-- ----------------------------------------------------------------

CREATE TABLE minions (
    id INT NOT NULL PRIMARY KEY,
    name VARCHAR(50),
    age INT
);
  
CREATE TABLE towns (
    id INT NOT NULL PRIMARY KEY,
    name VARCHAR(50)
);

-- ----------------------------------------------------------------
-- 02. Alter Minions Table
-- ----------------------------------------------------------------

ALTER TABLE minions
ADD COLUMN town_id INT;

ALTER TABLE minions
ADD CONSTRAINT fk_minions_towns
  FOREIGN KEY (town_id)
  REFERENCES towns(id);
  
-- ----------------------------------------------------------------
-- 03. Insert Records in Both Tables
-- ----------------------------------------------------------------

INSERT INTO towns(id, name)
VALUES
	(1, 'Sofia'),
    (2, 'Plovdiv'),
    (3, 'Varna');

INSERT INTO minions(id, name, age, town_id)
VALUES
	(1, 'Kevin', 22, 1),
    (2, 'Bob', 15, 3),
    (3, 'Steward', NULL, 2);
    
-- ----------------------------------------------------------------
-- 04. Truncate Table Minions
-- ----------------------------------------------------------------

TRUNCATE TABLE minions;

-- ----------------------------------------------------------------
-- 05. Drop All Tables
-- ----------------------------------------------------------------

DROP TABLE minions;
DROP TABLE towns;

-- ----------------------------------------------------------------
-- 06. Create Table People
-- ----------------------------------------------------------------

CREATE TABLE people(
	id 	INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(200) NOT NULL,
    picture BLOB,
    height DECIMAL(6, 2),
    weight DECIMAL(6, 2),
    gender enum('m', 'f') NOT NULL,
    birthdate DATE NOT NULL,
    biography text
);

INSERT INTO people(name, gender, birthdate, biography)
VALUES
	('A', 'm', '2020-01-14', 'aaaaaaaaaaaa'),
    ('B', 'm', '2020-01-14', 'aaaaaaaaaaaa'),
    ('C', 'm', '2020-01-14', 'aaaaaaaaaaaa'),
    ('D', 'm', '2020-01-14', 'aaaaaaaaaaaa'),
    ('E', 'm', '2020-01-14', 'aaaaaaaaaaaa');
    
-- ----------------------------------------------------------------
-- 07. Create Table Users
-- ----------------------------------------------------------------

CREATE TABLE users(
	id BIGINT UNIQUE AUTO_INCREMENT,
    username VARCHAR(30) UNIQUE NOT NULL,
    password VARCHAR(26) NOT NULL,
    profile_picture BLOB,
    last_login_time TIME,
    is_deleted bool
);

ALTER TABLE users
ADD PRIMARY KEY (id);

INSERT INTO users
(username, password, is_deleted)
VALUES
('A', '12345', 0),
('B', '12345', 0),
('C', '12345', 0),
('D', '12345', 0),
('E', '12345', 1);

-- ----------------------------------------------------------------
-- 08. Change Primary Key
-- ----------------------------------------------------------------

ALTER TABLE users
DROP PRIMARY KEY,
ADD CONSTRAINT pk_users PRIMARY KEY (id, username);

-- ----------------------------------------------------------------
-- 09. Set Default Value of a Field
-- ----------------------------------------------------------------

ALTER TABLE users
MODIFY last_login_time TIMESTAMP NOT NULL DEFAULT current_timestamp;

-- ----------------------------------------------------------------
-- 10. Set Unique Field
-- ----------------------------------------------------------------

ALTER TABLE users MODIFY id BIGINT NOT NULL;
ALTER TABLE users DROP PRIMARY KEY;
ALTER TABLE users ADD CONSTRAINT pk_users PRIMARY KEY (id);
ALTER TABLE users ADD CONSTRAINT uq_username UNIQUE (username);

-- ----------------------------------------------------------------
-- 11. Movies Database
-- ----------------------------------------------------------------

CREATE DATABASE movies;
USE movies;

CREATE TABLE directors(
	id INT NOT NULL AUTO_INCREMENT,
    director_name VARCHAR(30) NOT NULL,
    notes TEXT,
    PRIMARY KEY (id)
);
INSERT INTO directors(director_name)
VALUES
	('A'),
    ('B'),
    ('C'),
    ('D'),
    ('E');

CREATE TABLE genres(
	id INT NOT NULL AUTO_INCREMENT,
    genre_name VARCHAR(30) NOT NULL,
    notes TEXT,
    PRIMARY KEY (id)
);
INSERT INTO genres(genre_name)
VALUES
	('AAA'),
    ('BBB'),
    ('CCC'),
    ('DDD'),
    ('EEE');

CREATE TABLE categories(
	id INT NOT NULL AUTO_INCREMENT,
    category_name VARCHAR(30) NOT NULL,
    notes TEXT,
    PRIMARY KEY (id)
);
INSERT INTO categories(category_name)
VALUES
	('AAAAA'),
    ('BBBBB'),
    ('CCCCC'),
    ('DDDDD'),
    ('EEEEE');

CREATE TABLE movies(
	id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(30) NOT NULL, 
    director_id INT,
    copyright_year YEAR,
    length INT, 
    genre_id INT, 
    category_id INT, 
    rating INT, 
    notes TEXT,
    PRIMARY KEY (id)
);
INSERT INTO movies(title, copyright_year)
VALUES
	('AAA AAA', NULL),
    ('BBB BBB', 2018),
    ('CCC CCC', 2019),
    ('DDD DDD', current_timestamp()),
    ('EEE EEE', current_timestamp());
    
-- ----------------------------------------------------------------
-- 12. Car Rental Database
-- ----------------------------------------------------------------

CREATE DATABASE car_rental;
USE car_rental;

CREATE TABLE categories (
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	category VARCHAR(30) NOT NULL, 
	daily_rate TINYINT DEFAULT 0, 
	weekly_rate TINYINT DEFAULT 0, 
	monthly_rate TINYINT DEFAULT 0, 
	weekend_rate TINYINT DEFAULT 0
);
INSERT INTO categories(category)
VALUES 
	('A'), 
    ('B'), 
    ('C');
    
CREATE TABLE cars(
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    plate_number INT NOT NULL, 
    make VARCHAR(30) NOT NULL, 
    model VARCHAR(30) NOT NULL, 
    car_year YEAR, 
    category_id INT, 
    doors TINYINT, 
    picture BLOB, 
    car_condition VARCHAR(30), 
    available BIT DEFAULT 0
);
INSERT INTO cars(plate_number, make, model)
VALUES 
	(1, 'a', 'A'), 
    (2, 'b', 'B'), 
    (3, 'c', 'C');
    
CREATE TABLE employees(
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(30) NOT NULL, 
    last_name VARCHAR(30) NOT NULL, 
    title VARCHAR(30), 
    notes TEXT
);
INSERT INTO employees(first_name, last_name)
VALUES 
	('Pesho', 'A'), 
    ('Petko', 'B'), 
    ('Pavel', 'C');
    
CREATE TABLE customers(
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    driver_licence_number INT NOT NULL, 
    full_name VARCHAR(30) NOT NULL, 
    address VARCHAR(50), 
    city VARCHAR(30), 
    zip_code VARCHAR(10), 
    notes TEXT
);
INSERT INTO customers(driver_licence_number, full_name)
VALUES 
	(11111, 'A.A.'), 
    (22222, 'B.B.'), 
    (33333, 'C.C.');

CREATE TABLE rental_orders(
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
    employee_id INT NOT NULL, 
    customer_id INT NOT NULL, 
    car_id INT NOT NULL, 
    car_condition VARCHAR(30), 
    tank_level VARCHAR(30), 
    kilometrage_start INT, 
    kilometrage_end INT, 
    total_kilometrage INT, 
    start_date DATE, 
    end_date DATE, 
    total_days INT, 
    rate_applied VARCHAR(30), 
    tax_rate INT, 
    order_status VARCHAR(30), 
    notes TEXT
);
INSERT INTO rental_orders(employee_id, customer_id, car_id)
VALUES 
	(1, 1, 1), 
    (2, 2, 2), 
    (3, 2, 3);

-- ----------------------------------------------------------------
-- 13. Hotel Database
-- ----------------------------------------------------------------

CREATE DATABASE hotel;
USE hotel;

CREATE TABLE employees(
	id int not null primary key auto_increment, 
	first_name varchar(30) not null, 
	last_name varchar(30), 
	title varchar(30), 
	notes text
);
INSERT INTO employees (first_name)
VALUES
	('A.'),
    ('B.'),
    ('C.');
    
CREATE TABLE customers(
	account_number int not null primary key auto_increment, 
    first_name varchar(30) not null, 
    last_name varchar(30) not null, 
    phone_number int, 
    emergency_name varchar(30), 
    emergency_number varchar(30), 
    notes text
);
INSERT INTO customers(first_name, last_name)
VALUES
	('A.', 'A.'),
    ('B.', 'B.'),
    ('C.', 'C.');
    
CREATE TABLE room_status(
	room_status varchar(30) not null primary key, 
	notes text
);
INSERT INTO room_status(room_status)
VALUES
('Available'), 
('Reservation'), 
('Occupied');
    
CREATE TABLE room_types(
	room_type varchar(30) not null primary key, 
	notes text
);
INSERT INTO room_types(room_type)
VALUES 
	('Single'), 
	('Double'), 
	('Apartment');
    
CREATE TABLE bed_types(
	bed_type varchar(30) not null primary key, 
	notes text
);
INSERT INTO bed_types(bed_type)
VALUES 
('S'), 
('M'), 
('L');
    
CREATE TABLE rooms(
	room_number int not null primary key, 
	room_type varchar(30), 
	bed_type varchar(30), 
	rate varchar(30), 
	room_status varchar(30), 
	notes text
);
INSERT INTO rooms(room_number)
VALUES
	(101),
    (102),
    (103);
    
CREATE TABLE payments(
	id int not null primary key auto_increment,
    employee_id int,
    payment_date datetime,
    account_number varchar(30), 
    first_date_occupied date,
    last_date_occupied date,
    total_days int,
    amount_charged decimal(10, 2),
    tax_rate decimal(10, 2),
    tax_amount decimal(10, 2),
    payment_total decimal(10, 2),
    notes text
);
INSERT INTO payments(employee_id)
VALUES
	(1),
    (2),
    (3);
    
CREATE TABLE occupancies(
	id int not null primary key auto_increment, 
    employee_id int, 
    date_occupied datetime, 
    account_number varchar(30), 
    room_number int, 
    rate_applied varchar(30), 
    phone_charge varchar(30), 
    notes text
);
INSERT INTO occupancies (room_number)
VALUES
	(101),
    (102),
    (103);
    
-- ----------------------------------------------------------------
-- 14. Create SoftUni Database
-- ----------------------------------------------------------------

CREATE DATABASE soft_uni;
USE soft_uni;

CREATE TABLE towns(
	id int not null primary key auto_increment,
    `name` varchar(30) not null
);

CREATE TABLE addresses (
	id int not null primary key auto_increment,
    address_text varchar(255), 
    town_id int,
    constraint fk_addresses_towns foreign key addresses(town_id)
	references towns(id)
);

CREATE TABLE departments(
	id int not null primary key auto_increment,
    `name` varchar(50) not null
);

CREATE TABLE employees(
	id int not null primary key auto_increment,
    first_name varchar(45), 
    middle_name varchar(45), 
    last_name varchar(45), 
    job_title varchar(45), 
    department_id int, 
    hire_date date, 
    salary decimal(10, 2), 
    address_id int,
    
    constraint fk_employees_departments foreign key employees(department_id)
    references departments(id),
    
    constraint fk_employees_addresses foreign key employees(address_id)
    references addresses(id)
);

-- ----------------------------------------------------------------
-- 15. Basic Insert
-- ----------------------------------------------------------------
INSERT INTO towns(`name`)
VALUES
	('Sofia'),
    ('Plovdiv'),
    ('Varna'),
    ('Burgas');

INSERT INTO departments(`name`)
VALUES
	('Engineering'),
    ('Sales'),
    ('Marketing'),
    ('Software Development'),
    ('Quality Assurance');

INSERT INTO employees
	(first_name,middle_name,last_name,job_title,department_id,hire_date,salary)
VALUES
	('Ivan','Ivanov','Ivanov','.NET Developer',4,'2013-02-01','3500.00'),
	('Petar','Petrov','Petrov','Senior Engineer',1,'2004-03-02','4000.00'),
	('Maria','Petrova','Ivanova','Intern',5,'2016-08-28','525.25'),
	('Georgi','Terziev','Ivanov','CEO',2,'2007-12-09','3000.00'),
	('Peter','Pan','Pan','Intern',3,'2016-08-28','599.88');
    
-- ----------------------------------------------------------------
-- 16. Basic Select All Fields
-- ----------------------------------------------------------------

SELECT * from towns;
SELECT * from departments;
SELECT * from employees;

-- ----------------------------------------------------------------
-- 17. Basic Select All Fields and Order Them
-- ----------------------------------------------------------------

SELECT * from towns ORDER BY `name` ASC;
SELECT * from departments ORDER BY `name` ASC;
SELECT * from employees ORDER BY salary DESC;

-- ----------------------------------------------------------------
-- 18. Basic Select Some Fields
-- ----------------------------------------------------------------

SELECT `name` from towns ORDER BY `name` ASC;
SELECT `name` from departments ORDER BY `name` ASC;
SELECT first_name, last_name, job_title, salary from employees ORDER BY salary DESC;

-- ----------------------------------------------------------------
-- 19. Increase Employees Salary
-- ----------------------------------------------------------------

UPDATE employees SET salary = salary * 1.1;
SELECT salary FROM employees;

-- ----------------------------------------------------------------
-- 20. Decrease Tax Rate
-- ----------------------------------------------------------------

USE hotel;
UPDATE payments SET tax_rate = tax_rate * 0.97;
SELECT tax_rate FROM payments;

-- ----------------------------------------------------------------
-- 21. Delete All Records
-- ----------------------------------------------------------------

Truncate Table occupancies;
