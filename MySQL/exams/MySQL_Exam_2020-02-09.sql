SET GLOBAL log_bin_trust_function_creators = 1;

create database if not exists `fsd`;
use `fsd`;

create table countries(
    id int primary key auto_increment,
    `name` varchar(45) not null
);

create table towns(
    id int primary key auto_increment,
    `name` varchar(45) not null,
    country_id int not null,

    foreign key (country_id) references countries(id)
);

create table stadiums(
    id int primary key auto_increment,
    `name` varchar(45) not null ,
    capacity int not null ,
    town_id int not null ,

    foreign key (town_id) references towns(id)
);

create table teams(
    id int primary key auto_increment,
    `name` varchar(45) not null ,
    established date,
    fan_base bigint(20) not null ,
    stadium_id int not null ,

    foreign key (stadium_id) references stadiums(id)
);

create table skills_data(
    id int primary key auto_increment,
    dribbling int default 0,
    pace int default 0,
    passing int default 0,
    shooting int default 0,
    speed int default 0,
    strength int default 0
);

create table coaches(
    id int primary key auto_increment,
    first_name varchar(10) not null ,
    last_name varchar(20) not null ,
    salary decimal(10,2) not null default 0,
    coach_level int not null default 0
);

create table players(
    id int primary key auto_increment,
    first_name varchar(10) not null ,
    last_name varchar(20) not null ,
    age int not null default 0 ,
    position char(1) not null ,
    salary decimal(10, 2) not null default 0,
    hire_date datetime,
    skills_data_id int not null ,
    team_id int,

    foreign key (skills_data_id) references skills_data(id),
    foreign key (team_id) references teams(id)
);

create table players_coaches(
    player_id int not null,
    coach_id int not null,

    primary key (player_id, coach_id),
    foreign key (player_id) references players(id),
    foreign key (coach_id) references coaches(id)
);

# 02.	Insert
insert into coaches(first_name, last_name, salary, coach_level)
select p.first_name, p.last_name, (2 * p.salary) salary, char_length(p.first_name) coach_level from players p
where p.age >= 45;

# 03. Update
update coaches c
set c.coach_level = c.coach_level + 1
where c.first_name like 'A%' and
(select count(pc.player_id) count from players_coaches pc where pc.coach_id = c.id) >= 1;

select * from coaches c
where c.first_name like 'A%' and
(select count(pc.player_id) count from players_coaches pc where pc.coach_id = c.id) >= 1;

# 04. Delete
delete from players p
where p.age >= 45;

# 05. Players
select p.first_name, p.age, p.salary from players p
order by p.salary desc;

# 06. Young offense players without contract
select p.id, concat(p.first_name, ' ', p.last_name) full_name, p.age, p.position, p.hire_date from players p
join skills_data sd on p.skills_data_id = sd.id
where p.position = 'A' and p.hire_date is null and sd.strength > 50
order by p.salary, p.age;

# 07. Detail info for all teams
select t.name team_name, t.established, t.fan_base, count(p.id) players_count from teams t
join players p on t.id = p.team_id
group by t.id
order by players_count desc, t.fan_base desc;

# 08. The fastest player by towns
select max(sd.speed) max_speed, t.name town_name from players p
right join teams tm on p.team_id = tm.id
right join stadiums s on tm.stadium_id = s.id
right join towns t on s.town_id = t.id
left join skills_data sd on p.skills_data_id = sd.id
where tm.name != 'Devify'
group by t.id, t.name
order by max_speed desc, t.name;

# 09. Total salaries and players by country
select c.name, count(p.id) total_count_of_players, sum(p.salary) total_sum_of_salaries from countries c
left join towns t on c.id = t.country_id
left join stadiums s on t.id = s.town_id
left join teams t2 on s.id = t2.stadium_id
left join players p on t2.id = p.team_id
group by c.id, c.name
order by total_count_of_players desc, c.name;

# 10. Find all players that play on stadium
create function udf_stadium_players_count (stadium_name VARCHAR(30))
returns int
begin
    declare players_count int;
    set players_count = 0;

    select count(p.id) into players_count from stadiums s
    left join teams t on s.id = t.stadium_id
    join players p on t.id = p.team_id
    where s.name = stadium_name
    group by s.id;

    return players_count;
end;

# test
SELECT udf_stadium_players_count ('Linklinks') as `count`;

#  11. Find good playmaker by teams
create procedure udp_find_playmaker(min_dribble_points int, team_name varchar(45))
begin
    declare average_speed decimal;
    set average_speed = 0.0;

    select avg(speed) into average_speed from players p
    join skills_data sd on p.skills_data_id = sd.id
    join teams t on p.team_id = t.id
    where t.name = team_name
    group by t.id;

    select
           concat(p.first_name, ' ', p.last_name) full_name,
           p.age,
           p.salary,
           s.dribbling,
           s.speed,
           t.name team_name
    from players p
    join skills_data s on p.skills_data_id = s.id
    right join teams t on p.team_id = t.id
    where s.dribbling > min_dribble_points and t.name = team_name and s.speed > average_speed
    order by s.speed desc limit 1;
end;

# test
CALL udp_find_playmaker (20, 'Skyble');