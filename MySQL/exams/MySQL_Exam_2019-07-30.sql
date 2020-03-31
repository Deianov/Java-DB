
SET GLOBAL log_bin_trust_function_creators = 1;

create database if not exists colonial_blog_db;
use colonial_blog_db;

# 01. Table Design

create table users(
    id int primary key auto_increment,
    username varchar(30) not null unique,
    password varchar(30) not null,
    email varchar(50) not null
);

create table categories(
    id int primary key auto_increment,
    category varchar(30) not null
);

create table articles(
    id int primary key auto_increment,
    title varchar(50) not null,
    content text not null,
    category_id int,

    foreign key (category_id) references categories(id)
);

create table users_articles(
    user_id int not null,
    article_id int not null,

    primary key (user_id, article_id),
    foreign key (user_id) references users(id),
    foreign key (article_id) references articles(id)
);

create table comments(
    id int primary key auto_increment,
    `comment` varchar(255) not null,
    article_id int not null,
    user_id int not null,

    foreign key (article_id) references articles(id),
    foreign key (user_id) references users(id)
);

create table likes(
    id int primary key auto_increment,
    article_id int,
    comment_id int,
    user_id int not null,

    foreign key (article_id) references articles(id),
    foreign key (comment_id) references comments(id),
    foreign key (user_id) references users(id)
);

# 02. Insert
insert into likes(article_id, comment_id, user_id)
select if(u.id % 2 = 0, char_length(u.username), null) article_id,
       if(u.id % 2 = 0, null, char_length(u.email)) comment_id,
       u.id
from users u
where u.id between 16 and 20;

# variant 2
insert into likes(article_id, comment_id, user_id)
select
	(case
		when u.id % 2 = 0
		then char_length(u.username)
		else null
    end) article_id,
    (case
		when u.id % 2 != 0
		then char_length(u.email)
		else null
    end) comment_id,
    u.id
from users u
where u.id between 16 and 20;

# 03. Update
update comments c
set c.comment =
    case
        when c.id % 2 = 0 then 'Very good article.'
        when c.id % 3 = 0 then 'This is interesting.'
        when c.id % 5 = 0 then 'I definitely will read the article again.'
        when c.id % 7 = 0 then 'The universe is such an amazing thing.'
        else c.comment
    end
where c.id between 1 and 15;

# 04. Delete
delete from articles a
where a.category_id is null;

# 05. Extract 3 biggest articles
select title, summary
from
    (
    select
           a.id,
           a.title,
           concat(substr(a.content,1,20), '...') summary
    from articles a
    order by char_length(a.content) desc limit 3
    ) arts
order by arts.id;

# 06. Golden articles
select a.id article_id,
       a.title
from articles a
join users_articles ua on a.id = ua.article_id
where a.id = ua.user_id
order by a.id;

# 07. Extract categories
select c.category,
       count(a.id) articles,
       (
           select count(l.id)
           from likes l
           join articles a2 on l.article_id = a2.id
           where a2.category_id = c.id
       ) likes
from categories c
join articles a on c.id = a.category_id
group by c.id
order by likes desc, articles desc, c.id;

# 08. Extract the most commented social article
select a.title, count(distinct c.id) comments from articles a
join comments c on a.id = c.article_id
join categories c2 on a.category_id = c2.id
where c2.category = 'Social'
group by a.id
order by comments desc limit 1;

# 09. Extract the less liked comments
select concat(substr(c.comment,1,20), '...') summary
from comments c
left join articles a on c.article_id = a.id
join likes l on a.id = l.article_id
group by c.id
order by c.id desc;

