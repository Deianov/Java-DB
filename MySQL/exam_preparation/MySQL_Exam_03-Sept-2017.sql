
SET GLOBAL log_bin_trust_function_creators = 1;
create database instagraph_db;
use instagraph_db;

-- 01. Table Design
create table pictures(
    id int(11) primary key auto_increment,
    path varchar(255) not null,
    size decimal(10,2) not null
);

create table users(
    id int(11) primary key auto_increment,
    username varchar(30) not null unique,
    password varchar(30) not null,
    profile_picture_id int(11),

    foreign key (profile_picture_id) references pictures(id)
);

create table posts(
    id int(11) primary key auto_increment,
    caption varchar(255) not null,
    user_id int(11) not null,
    picture_id int(11) not null,

    foreign key (user_id) references users(id),
    foreign key (picture_id) references pictures(id)
);

create table comments(
    id int(11) primary key auto_increment,
    content varchar(255) not null,
    user_id int(11) not null,
    post_id int(11) not null,

    foreign key (user_id) references users(id),
    foreign key (post_id) references posts(id)
);

create table users_followers(
    user_id int(11),
    follower_id int(11),

    foreign key (user_id) references users(id),
    foreign key (follower_id) references users(id)
);

-- 02. Data Insertion
insert into comments (content, user_id, post_id)
select
    concat('Omg!', u.username, '!This is so cool!') 'content',
    ceiling(p.id * 3 / 2) 'user_id',
    p.id 'post_id'
from posts p
join users u on p.user_id = u.id
where p.id between 1 and 10;

-- 03. Data Update
update users u
set u.profile_picture_id =
    if (
        (select count(*) from users_followers uf where uf.follower_id = u.id) = 0,
        u.id,
        (select count(*) from users_followers uf where uf.follower_id = u.id)
    )
where u.profile_picture_id is null;

-- variant 2
update users u
left join
    (
        select user_id, count(follower_id) 'count'
        from users_followers
        group by user_id
    ) uf
on u.id = uf.user_id
set u.profile_picture_id = if(count is null or count = 0, u.id, count)
where u.profile_picture_id is null;

-- 04. Data Deletion
delete u from users u
left join
    (
        select user_id, count(follower_id) 'count'
        from users_followers
        group by user_id
    ) uf
on u.id = uf.user_id
where (uf.count is null or uf.count = 0) and
      u.id not in
      (
          select distinct follower_id
          from users_followers
#         -> -> -> -> -> !!!!!
          where follower_id is not null
      );

-- 05. Users
select
    u.id,
    u.username
from users u
order by u.id;

-- 06. Cheaters
select
    uf.user_id,
    u.username
from users_followers uf
join users u on uf.follower_id = u.id
where uf.user_id = uf.follower_id
order by uf.user_id;
