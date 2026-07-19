1# Write your MySQL query statement below
2select id,count(id)as num
3from(
4    select accepter_id as id
5    from RequestAccepted
6    union all
7    select requester_id as id
8    from RequestAccepted
9) temp
10group by id
11order by num desc limit 1;