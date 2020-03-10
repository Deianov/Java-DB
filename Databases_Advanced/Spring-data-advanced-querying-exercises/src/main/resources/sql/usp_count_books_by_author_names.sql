#;
delimiter ^;
use spring_book_shop ^;
drop procedure if exists usp_count_books_by_author_names ^;

create procedure usp_count_books_by_author_names(first_name varchar(60), last_name varchar(60), OUT result int)
begin
    declare temp_count integer;
    set result = -1;
    set temp_count = -1;

    select count(*) into temp_count from authors a
    join books b on b.author_id = a.id
    where a.first_name = first_name and a.last_name = last_name;

    set result = temp_count;
end ^;

# test
# call usp_count_books_by_author_names('Amanda', 'Rice', @result);
# select @result;