create procedure usp_get_older(minion_id int)
begin
    update minions m
    set m.age = m.age + 1
    where m.id = minion_id;
end;