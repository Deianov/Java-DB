-- Database Basics MySQL Exam - 9 June 2019
SET GLOBAL log_bin_trust_function_creators = 1;
create database `ruk_database`;
use `ruk_database`;

-- 01.	Table Design
create table branches(
    id int(11) primary key auto_increment,
    `name` varchar(30) not null unique
);

create table employees(
    id int(11) primary key auto_increment,
    first_name varchar(20) not null,
    last_name varchar(20) not null,
    salary decimal(10,2) not null,
    started_on date not null,
    branch_id int(11) not null,
    foreign key (branch_id) references branches(id)
);

create table clients(
    id int(11) primary key auto_increment,
    full_name varchar(50) not null,
    age int(11) not null
);

create table employees_clients(
    employee_id int(11) not null,
    client_id int(11) not null,

    foreign key (employee_id) references employees(id),
    foreign key (client_id) references clients(id)
);

create table bank_accounts(
    id int(11) primary key auto_increment,
    account_number varchar(10) not null,
    balance decimal(10,2) not null,
    client_id int(11) not null unique,

    foreign key (client_id) references clients(id)
);

create table cards(
    id int(11) primary key auto_increment,
    card_number varchar(19) not null,
    card_status varchar(7) not null,
    bank_account_id int(11) not null,

    foreign key (bank_account_id) references bank_accounts(id)
);

-- 02. Insert
insert into cards (card_number, card_status, bank_account_id)
select
    reverse(c.full_name),
    'Active',
    c.id
from clients as c
where c.id between 191 and 200;

-- 03. Update
create procedure update_employees_clients()
begin
    declare lowest_count int(10);
    set lowest_count := (
        select employee_id from employees_clients
        group by employee_id
        order by count(client_id), employee_id limit 1);

    update employees_clients
    set employee_id = lowest_count
    where client_id = employee_id;
end;
call update_employees_clients();

-- 04. Delete
delete e from employees as e
left join employees_clients as ec on e.id = ec.employee_id
where client_id is null;

-- 05. Clients
select c.id, c.full_name
from clients as c
order by c.id;

-- 06. Newbies
select
    e.id,
    concat(e.first_name, ' ', e.last_name) as 'full_name',
    concat('$', e.salary) as 'salary',
    e.started_on
from employees as e
where e.salary >= 100000 and  date(e.started_on) >= '20180101'
order by e.salary desc, e.id;

-- 07. Cards against Humanity
select
    c.id,
    concat(c.card_number, ' : ', c2.full_name)
from cards as c
join bank_accounts ba on c.bank_account_id = ba.id
join clients c2 on ba.client_id = c2.id
order by c.id desc;

-- 08. Top 5 Employees
select
    concat(e.first_name, ' ', e.last_name) as 'name',
    e.started_on,
    count(client_id) as 'count_of_clients'
from employees as e
join employees_clients ec on e.id = ec.employee_id
group by e.id
order by `count_of_clients` desc, e.id limit 5;

--  09. Branch cards
select
b.`name` as 'name',
count(c.id) as 'count_of_cards'
from cards as c
join bank_accounts ba on c.bank_account_id = ba.id
join clients c2 on ba.client_id = c2.id
join employees_clients ec on c2.id = ec.client_id
join employees e on ec.employee_id = e.id
right join branches b on e.branch_id = b.id
group by b.`name`
order by `count_of_cards` desc, b.`name`;

-- 10. Extract card's count
create function udf_client_cards_count(`name` VARCHAR(30))
returns int
begin
    declare result int;
    set result :=
        (select
          count(*)
          from clients as c
          join bank_accounts ba on c.id = ba.client_id
          join cards c2 on ba.id = c2.bank_account_id
          where c.full_name like `name`
          group by c.full_name);
    return result;
end;

# test
SELECT c.full_name, udf_client_cards_count('Baxy David') as `cards` FROM clients c
WHERE c.full_name = 'Baxy David';

-- 11. Client Info
create procedure udp_clientinfo(full_name varchar(50))
begin
    select
        c.full_name,
        c.age,
        ba.account_number,
        concat('$', ba.balance) as 'balance'
    from clients as c
    join bank_accounts ba on c.id = ba.client_id
    where c.full_name like full_name;
end;
# test
call udp_clientinfo('Hunter Wesgate');