CREATE DATABASE `persons`;
USE `persons`;

-- 01. One-To-One Relationship
create table `passports`(
	passport_id int primary key,
    passport_number varchar(10) unique
);
insert into `passports` 
values
	(101, 'N34FG21B'),
	(102, 'K65LO4R7'),
    (103, 'ZE657QP2');
    
create table `persons`(
	person_id int primary key auto_increment,
    first_name varchar(20),
    salary decimal(10, 2),
    passport_id int unique
);

alter table `persons`
add foreign key persons(passport_id) 
references passports(passport_id);

insert into `persons`
values 
	(1, 'Roberto', 43300.00, 102),
	(2, 'Tom', 56100.00, 103),
    (3, 'Yana', 60200.00, 101);

-- 02. One-To-Many Relationship
create table manufacturers(
	manufacturer_id int primary key auto_increment,
    `name` varchar(30),
    established_on date
);
insert into manufacturers(`name`, `established_on`)
values
	('BMW', '1916-03-01'),
    ('Tesla', '2003-01-01'),
    ('Lada', '1966-05-01');
    
create table models(
	model_id int primary key,
    `name` varchar(30),
    manufacturer_id int,
    
    foreign key models(manufacturer_id)
    references manufacturers(manufacturer_id)
);
insert into models
values
	(101, 'X1', 1),
    (102, 'i6', 1),
    (103, 'Model S', 2),
    (104, 'Model X', 2),
    (105, 'Model 3', 2),
    (106, 'Nova', 3);

-- 03. Many-To-Many Relationship
create table students(
	student_id int primary key auto_increment,
    `name` varchar(45)
);
insert into students(`name`)
values
	('Mila'),
    ('Toni'),
    ('Ron');
    
create table exams(
	exam_id int primary key,
    `name` varchar(45)
);
insert into exams
values
	(101, 'Spring MVC'),
    (102, 'Neo4j'),
    (103, 'Oracle 11g');
    
create table students_exams(
	student_id int not null,
    exam_id int not null,
    primary key(student_id, exam_id),
    
    foreign key students_exams(student_id)
    references students(student_id),
    
    foreign key students_exams(exam_id)
    references exams(exam_id)
);
insert into students_exams
values
	(1, 101),
    (1, 102),
    (2, 101),
    (3, 103),
    (2, 102),
    (2, 103);
    
-- 04. Self-Referencing
create table teachers(
	teacher_id int primary key,
    `name` varchar(45),
    manager_id int NULL
);

insert into teachers
values
	(101, 'John', NULL),
    (102, 'Maya', 106),
    (103, 'Silvia', 106),
    (104, 'Ted', 105),
    (105, 'Mark', 101),
    (106, 'Greta', 101);

alter table teachers
add constraint fk_teachers_teachers
foreign key (manager_id)
references teachers(teacher_id);

-- 05. Online Store Database
create database store;
use store;

create table cities(
	city_id int(11) primary key,
    `name` varchar(50)
);

create table item_types(
	item_type_id int(11) primary key,
    `name` varchar(50)
);

create table items(
	item_id int(11) primary key auto_increment,
    `name` varchar(50),
    item_type_id int(11),
    
    foreign key (item_type_id)
    references item_types(item_type_id)
);

create table customers(
	customer_id int(11) primary key auto_increment,
    `name` varchar(50),
    birthday date,
    city_id int(11),
    
    foreign key (city_id)
    references cities(city_id)
);

create table orders(
	order_id int(11) primary key auto_increment,
    customer_id int(11),
    
    foreign key (customer_id)
    references customers(customer_id)
);

create table order_items(
	order_id int(11) not null,
    item_id int(11) not null,
    primary key(order_id, item_id),
    
    foreign key (order_id)
    references orders(order_id),
    
    foreign key (item_id)
    references items(item_id)
);

-- 06. University Database 
create database university;
use university;

create table subjects(
	subject_id int(11) primary key auto_increment,
    subject_name varchar(50)
);

create table majors(
	major_id int(11) primary key auto_increment,
    `name` varchar(50)
);

create table students(
	student_id int(11) primary key auto_increment,
    student_number varchar(12),
    student_name varchar(50),
    major_id int(11),
    
    foreign key (major_id)
    references majors(major_id)
);

create table payments(
	payment_id int(11) primary key auto_increment,
    payment_date date,
    payment_amount decimal(8,2),
    student_id int(11),
    
    foreign key (student_id)
    references students(student_id)
);

create table agenda(
	student_id int(11) not null,
    subject_id int(11) not null,
    primary key(student_id, subject_id),
    
    foreign key (student_id)
    references students(student_id),
    
    foreign key (subject_id)
    references subjects(subject_id)
);

-- 09. Peaks in Rila
use geography;

select  
	m.mountain_range,
    p.peak_name,
    p.elevation
from mountains as m
join peaks as p 
on p.mountain_id = m.id
where m.id = 17
order by p.elevation DESC;