-- MySQL Exam 25-February-2018

-- Initialisation
SET GLOBAL log_bin_trust_function_creators = 1;
create database `buhtig`;
use `buhtig`;

-- 01. Table Design
create table users(
    id int(11) primary key auto_increment,
    username varchar(30) not null unique,
    password varchar(30) not null,
    email varchar(50) not null
);

create table repositories(
    id int(11) primary key auto_increment,
    `name` varchar(50) not null
);

create table repositories_contributors(
    repository_id int(11),
    contributor_id int(11),

    foreign key (repository_id) references repositories(id),
    foreign key (contributor_id) references users(id)
);

create table issues(
    id int(11) primary key auto_increment,
    title varchar(255) not null,
    issue_status varchar(6) not null,
    repository_id int(11) not null,
    assignee_id int(11) not null,

    foreign key (repository_id) references repositories(id),
    foreign key (assignee_id) references users(id)
);

create table commits(
    id int(11) primary key auto_increment,
    message varchar(255) not null,
    issue_id int(11),
    repository_id int(11) not null,
    contributor_id int(11) not null,

    foreign key (issue_id) references issues(id),
    foreign key (repository_id) references repositories(id),
    foreign key (contributor_id) references users(id)
);

create table files(
    id int(11) primary key auto_increment,
    `name` varchar(100) not null,
    size decimal(10,2) not null,
    parent_id int(11),
    commit_id int(11) not null,

    foreign key (parent_id) references files(id),
    foreign key (commit_id) references commits(id)
);

-- 02. Data Insertion
insert into issues
(title, issue_status, repository_id, assignee_id)
select
    concat('Critical Problem With ', f.`name`, '!') as 'title',
    'open' as 'issue_status',
    ceiling(f.id * 2 / 3) as 'repository_id',
    c.contributor_id as 'assignee_id'
from files as f
join commits as c on f.commit_id = c.id
where f.id between 46 and 50;

-- 03. Data Update
create procedure udp_update_contributors_to_repositories()
begin
    declare lowest_id int(11);
    set lowest_id :=
        (select
            r.id
            from repositories as r
            left join repositories_contributors rc on r.id = rc.repository_id
            group by r.id
            having count(contributor_id) = 0
            order by r.id limit 1
        );

    update repositories_contributors as rc
    set rc.repository_id = lowest_id
    where rc.contributor_id = rc.repository_id and lowest_id is not null;
end;
call udp_update_contributors_to_repositories();

-- variant 2 by Hristo Nakov
UPDATE repositories_contributors as rc
JOIN
	(SELECT r.id as repo_id
		FROM repositories as r
		WHERE r.id NOT IN (SELECT repository_id FROM repositories_contributors)
        ORDER BY r.id LIMIT 1
	) as d
SET rc.repository_id = d.repo_id
WHERE rc.contributor_id = rc.repository_id;

-- 04. Data Deletion
delete r from repositories as r
left join issues i on r.id = i.repository_id
where i.repository_id is null;

-- 05. Users
select
    u.id,
    u.username
from users as u
order by u.id;

-- 06. Lucky Numbers
select *
from repositories_contributors as rc
where rc.repository_id = rc.contributor_id;

-- 07. Heavy HTML
select id, `name`, size
from files as f
where f.size > 1000 and f.`name` like '%html%'
order by f.size desc;

-- 08. Issues and Users
select
    i.id,
    concat(u.username, ' : ', i.title)
from issues as i
join users u on i.assignee_id = u.id
order by i.id desc;

-- 09. Non-Directory Files
select
    f.id,
    f.`name`,
    concat(f.size, 'KB') as 'size'
from files as f
where f.id not in (select distinct i.parent_id from files as i where i.parent_id is not null)
order by f.id;

-- 10. Active Repositories
select
    r.id,
    r.`name`,
    count(i.id) issues
from repositories r
join issues i on r.id = i.repository_id
group by r.id
order by issues desc, r.id;

-- 11. Most Contributed Repository
select
    r.id,
    r.`name`,
    (select count(*) from commits c where c.repository_id = rc.repository_id) 'commits',
    count(rc.contributor_id) 'contributors'
from repositories_contributors rc
join repositories r on rc.repository_id = r.id
group by r.id
order by contributors desc, r.id;

-- 12. Fixing My Own Problems
select
    u.id,
    u.username,
    count(i.id) commits
from users u
join issues i on u.id = i.assignee_id
where i.issue_status like 'opened'
group by u.id
order by commits desc, u.id;

-- 13. Recursive Commits
select
    left(f.name, locate('.', f.`name`) - 1) 'file',
    '2' as recursive_count
from files f
join files f2
on f.id = f2.parent_id and f.parent_id = f2.id and f.id <> f2.id
order by `file`;

-- 14. Repositories and Commits
select
    r.id,
    r.`name`,
    count(c.contributor_id) 'users'
from repositories r
left join commits c on r.id = c.repository_id
group by r.id
order by users desc, r.id;

-- 15. Commit
create procedure udp_commit(
username varchar(30),
password varchar(30),
message varchar(255),
issue_id int(11)
)
begin
    declare logged_user_id int(11);

    if (select count(*) from users u where u.username like username) <> 1
        then
            signal sqlstate '45000'
            set message_text = 'No such user!';
    end if;

    set logged_user_id :=
    (select u.id from users u where u.username = username and u.password = password);

    if logged_user_id is null
        then
            signal sqlstate '45000'
            set message_text = 'Password is incorrect!';
    end if;

    if (select count(*) from issues i where i.id = issue_id) <> 1
        then
            signal sqlstate '45000'
            set message_text = 'The issue does not exist!';
    end if;

    insert into commits
        (`message`, `issue_id`, `repository_id`, `contributor_id`)
    values(
           message,
           issue_id,
           (select i.repository_id from issues i where i.id = issue_id),
           logged_user_id
    );

    select * from commits c
    where c.issue_id = issue_id
    order by c.id desc limit 1;
end;
# test
call udp_commit(
    'WhoDenoteBel',
    'ajmISQi*',
    'Fixed issue: Invalid welcoming message in READ.html',
    2
);

-- 16. Filter Extensions
create procedure udp_findbyextension(extension varchar(10))
begin
    select
        f.id,
        f.`name`,
        concat(f.size, 'KB') 'size'
    from files f
    where f.name like concat('%.', extension)
    order by f.id;
end;
# test
call udp_findbyextension('html');