SET GLOBAL log_bin_trust_function_creators = 1;
use soft_uni;
-- 01. Employees with Salary Above 35000
create procedure usp_get_employees_salary_above_35000()
begin
    select e.first_name, e.last_name
    from employees as e
    where e.salary > 35000
    order by e.first_name, e.last_name, e.employee_id;
end;
# test
call usp_get_employees_salary_above_35000();

-- 02. Employees with Salary Above Number
create procedure usp_get_employees_salary_above(salary decimal(19, 4))
begin
    select first_name, last_name
    from employees as e
    where salary is not null and e.salary >= salary
    order by e.first_name, e.last_name, e.employee_id;
end;
# test
call usp_get_employees_salary_above(48100);

-- 03. Town Names Starting With
create procedure usp_get_towns_starting_with(`name` varchar(50))
begin
    select t.`name` as 'town_name'
    from towns as t
    where t.`name` like concat(`name`, '%')
    order by t.`name`;
end;
# test
call usp_get_towns_starting_with('b');

-- 04. Employees from Town
create procedure usp_get_employees_from_town(`name` varchar(50))
begin
    select first_name, last_name
    from employees as e
    join addresses as a
    on e.address_id = a.address_id
    join towns as t
    on a.town_id = t.town_id
    where t.`name` = `name`
    order by e.first_name, e.last_name, e.employee_id;
end;
# test
call usp_get_employees_from_town('Sofia');

-- 05. Salary Level Function
create function ufn_get_salary_level(salary decimal)
returns varchar(10)
begin
    return (
        case
            when salary < 30000 then 'Low'
            when salary between 30000 and 50000 then 'Average'
            when salary > 50000 then 'High'
        end
    );
end;
# test
select ufn_get_salary_level(13500);

-- 06. Employees by Salary Level
create procedure usp_get_employees_by_salary_level(`level` varchar(10))
begin
    select e.first_name, e.last_name
    from employees as e
    where level = (select ufn_get_salary_level(e.salary))
    order by e.first_name desc, e.last_name desc;
end;
# test
call usp_get_employees_by_salary_level('high');

-- 07. Define Function
create function ufn_is_word_comprised(set_of_letters varchar(50), word varchar(50))
returns bit
begin
    declare result bit;
    set result = if(word regexp concat('^[', set_of_letters, ']+$'), 1, 0);
    return result;
end;
# test
select ufn_is_word_comprised('oistmiahf', 'Sofia');
select ufn_is_word_comprised('oistmiahf', 'halves');

-- 08. Find Full Name
create procedure usp_get_holders_full_name()
begin
    select
    concat(ah.first_name, ' ', ah.last_name) as 'full_name'
    from account_holders as ah
    order by `full_name` asc;
end;
# test
call usp_get_holders_full_name();

-- 9. People with Balance Higher Than
create procedure usp_get_holders_with_balance_higher_than(total double)
begin
    select
    ah.first_name,
    ah.last_name
    from accounts as a
    join account_holders as ah
    on a.account_holder_id = ah.id
    group by a.account_holder_id
    having sum(balance) > total
    order by account_holder_id;
end;
# test
call usp_get_holders_with_balance_higher_than(7000);

-- 10. Future Value Function
create function ufn_calculate_future_value(initial_sum  double, rate double, years int)
returns double
begin
    return (initial_sum * pow(1 + rate, years));
end;
# test
select ufn_calculate_future_value(1000, 0.1, 5) as 'Output';

-- 11. Calculating Interest
create function ufn_calculate_future_value(initial_sum  decimal(19, 4), rate decimal(19, 4), years int)
returns decimal(19, 4)
begin
    return (initial_sum * pow(1 + rate, years));
end;

create procedure usp_calculate_future_value_for_account(account_id int, rate decimal(19, 4))
begin
    select
           a.id as 'account_id',
           ac.first_name,
           ac.last_name,
           a.balance,
           ufn_calculate_future_value(a.balance, rate, 5)
               as 'balance_in_5_years'
    from accounts as a
    join account_holders as ac
    on a.account_holder_id = ac.id
    where a.id = account_id;
end;
# test
call usp_calculate_future_value_for_account(1, 0.1);

-- 12. Deposit Money
create procedure usp_deposit_money(account_id int, money_amount decimal(19, 4))
deposit:
begin
    if money_amount <= 0 then
        leave deposit;
    end if;

    update accounts as a
    set a.balance = a.balance + money_amount
    where a.id = account_id;
end;
# test
call usp_deposit_money(1,10);

-- 13. Withdraw Money
create procedure usp_withdraw_money(account_id int, money_amount decimal(19, 4))
withdraw:
begin
    declare acc_balance decimal(19, 4);

    if money_amount <= 0 then
        leave withdraw;
    end if;

    set acc_balance := (select balance from accounts where id = account_id);

    if acc_balance is null or acc_balance < money_amount then
        leave withdraw;
    end if;

    update accounts as a
    set a.balance = a.balance - money_amount
    where a.id = account_id;
end;
# test
call usp_withdraw_money(1, 10);

-- 14. Money Transfer
create procedure usp_transfer_money(from_account_id int, to_account_id int, amount decimal(19, 4))
transfer:
begin
    declare old_acc_balance_from decimal(19, 4);
    declare old_acc_balance_to decimal(19, 4);
    declare new_acc_balance_from decimal(19, 4);
    declare new_acc_balance_to decimal(19, 4);

    if amount <= 0 then
        leave transfer;
    end if;

    set old_acc_balance_from := (select balance from accounts where id = from_account_id);
    set old_acc_balance_to := (select balance from accounts where id = to_account_id);

    start transaction;
    call usp_withdraw_money(from_account_id, amount);
    call usp_deposit_money(to_account_id, amount);

    set new_acc_balance_from := (select balance from accounts where id = from_account_id);
    set new_acc_balance_to := (select balance from accounts where id = to_account_id);

    if (old_acc_balance_from - amount = new_acc_balance_from) and
       (old_acc_balance_to + amount = new_acc_balance_to) then
            commit;
        else
            rollback;
    end if;
end;
# test
call usp_transfer_money(1, 2, 10);

-- 15. Log Accounts Trigger
create table logs (
    log_id int not null primary key auto_increment,
    account_id int not null,
    old_sum decimal(19, 4),
    new_sum decimal(19, 4),
    foreign key (account_id) references accounts(id)
);

create trigger tr_update_accounts
after update on accounts
for each row
begin
    if  (new.balance is not null) and (old.balance <> new.balance) then
        insert into logs
            (account_id, old_sum, new_sum)
        values
            (old.id, old.balance, new.balance);
    end if;
end;
# test
call usp_transfer_money(1, 2, 10);

-- 16. Emails Trigger
create table notification_emails(
    id int unsigned primary key auto_increment,
    recipient int not null,
    subject varchar(255) not null,
    body varchar(255) not null,
    foreign key (recipient) references accounts(id)
);

create trigger tr_notification_emails
after insert on logs
for each row
begin
    insert into notification_emails(recipient, subject, body)
    values (new.account_id,
            concat('Balance change for account: ',new.account_id),
            concat('On ', current_timestamp,' your balance was changed from ', new.old_sum, ' to ', new.new_sum, '.'));
end;
