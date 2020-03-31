SET GLOBAL log_bin_trust_function_creators = 1;

create database if not exists instd;
use instd;

create table users(
    id int primary key,
    username varchar(30) not null unique,
    `password` varchar(30) not null,
    email varchar(50) not null,
    gender char(1) not null,
    age int not null,
    job_title varchar(40) not null,
    ip varchar(30) not null
);

create table addresses(
    id int primary key auto_increment,
    address varchar(30) not null,
    town varchar(30) not null,
    country varchar(30) not null,
    user_id int not null,

    foreign key (user_id) references users(id)
);

create table photos(
    id int primary key auto_increment,
    `description` text not null,
    `date` datetime not null,
    views int not null default 0
);

create table comments(
    id int primary key auto_increment,
    `comment` varchar(255) not null,
    `date` datetime not null,
    photo_id int not null,

    foreign key (photo_id) references photos(id)
);

create table users_photos(
    user_id int not null,
    photo_id int not null,

    foreign key (user_id) references users(id),
    foreign key (photo_id) references photos(id)
);

create table likes(
    id int primary key auto_increment,
    photo_id int,
    user_id int,

    foreign key (photo_id) references photos(id),
    foreign key (user_id) references users(id)
);

# 02. Insert
insert into addresses(address, town, country, user_id)
select
       u.username address,
       u.password town,
       u.id country,
       u.age user_id
from users u
where u.gender = 'M';


# 03. Update
update addresses a
set a.country =
    case
        when a.country like 'B%' then 'Blocked'
        when a.country like 'T%' then 'Test'
        when a.country like 'P%' then 'In Progress'
        else a.country
    end;

# 04. Delete
delete from addresses a
where a.id % 3 = 0;

# 05. Users
select u.username,
       u.gender,
       u.age
from users u
order by u.age desc, u.username;

# 06. Extract 5 most commented photos
select p.id,
       p.date date_and_time,
       p.description,
       count(c.id) commentsCount
from photos p
left join comments c on p.id = c.photo_id
group by p.id
order by commentsCount desc, p.id limit 5;

# 07. Lucky users
select concat(u.id, ' ', u.username) id_username,
       u.email
from users u
left join users_photos up on u.id = up.user_id
left join photos p on up.photo_id = p.id
where u.id = p.id
order by u.id;

# 08. Count likes and comments
select p.id photo_id,
       count(l.id) likes_count,
       (select count(c.id)
           from photos p2
           left join comments c on p2.id = c.photo_id
           where p.id = p2.id
           group by p2.id
       ) comments_count
from photos p
left join likes l on p.id = l.photo_id
group by p.id
order by likes_count desc, comments_count desc, p.id;

# 09. The photo on the tenth day of the month
select concat(substr(p.description,1,30), '...') summary,
       p.date
from photos p
where substr(p.date,9,2) like '10'
order by p.date desc;

# 10. Get user’s photos count
create function udf_users_photos_count(username VARCHAR(30))
returns int
begin
    declare photos_count int;
    set photos_count := 0;

    select count(up.user_id) into photos_count
    from users u
    join users_photos up on u.id = up.user_id
    where u.username = username
    group by u.id;

    return photos_count;
end;

# test
SELECT udf_users_photos_count('ssantryd') AS photosCount;

# 11. Increase user age
create procedure udp_modify_user (address VARCHAR(30), town VARCHAR(30))
begin
    update users u
    left join addresses a on u.id = a.user_id
    set u.age = u.age + 10
    where a.address = address and a.town = town;
end;

# test
CALL udp_modify_user ('97 Valley Edge Parkway', 'Divinópolis');
SELECT u.username, u.email,u.gender,u.age,u.job_title FROM users AS u
WHERE u.username = 'eblagden21';
