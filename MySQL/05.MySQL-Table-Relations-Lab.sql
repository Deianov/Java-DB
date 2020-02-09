CREATE DATABASE `camp`;
USE `camp`;

-- 1. Mountains and Peaks
CREATE TABLE `mountains`(
	id int primary key auto_increment,
    `name` varchar(50) not null
);

CREATE TABLE `peaks`(
	id int primary key auto_increment,
    `name` varchar(50),
    mountain_id int not null,
    
    constraint `fk_peaks_mountains` foreign key (mountain_id)
    references mountains(id)
);

-- 2. Trip Organization
SELECT
	v.driver_id,
    v.vehicle_type,
    concat(c.first_name, ' ', c.last_name) AS 'driver_name'
FROM vehicles AS v
JOIN campers AS c 
ON v.driver_id = c.id;

-- debug / speed
explain select * from campers;

-- 3. SoftUni Hiking
SELECT 
	r.starting_point as 'route_starting_point',
    r.end_point as 'route_ending_point',
    r.leader_id,
	concat(c.first_name, ' ', c.last_name) as 'leader_name'
FROM routes AS r
JOIN campers AS c
ON r.leader_id = c.id;

-- 4. Delete Mountains
create table mountains(
	id int primary key auto_increment unique,
    `name` varchar(50) not null
);
	-- One to One -> One to Many (mountain_id field should be UNIQUE.)
create table peaks(
	id int primary key auto_increment unique,
    `name` varchar(20) not null,
    mountain_id int not null,
    
    constraint fk_peaks_mountains foreign key(mountain_id)
    references mountains(id)
    on delete cascade
);

-- Delete Mountains - test
insert into mountains(`name`)
values
	('Mountain A'),
    ('Mountain B'),
    ('Mountain C');

insert into peaks(`name`, mountain_id)
values
	('Peak 1', 1),
    ('Peak 2', 2),
    ('Peak 3', 3),
    ('Peak 4', 3);

delete from mountains
where id = 3;