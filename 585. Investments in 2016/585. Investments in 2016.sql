1# Write your MySQL query statement below
2select round(sum(tiv_2016),2) as tiv_2016
3from Insurance 
4where tiv_2015 in(
5    select tiv_2015 
6    from Insurance 
7    group by tiv_2015
8    having count(*) > 1
9) 
10and (lat,lon) in (
11    select lat,lon 
12    from Insurance 
13    group by lat,lon
14    having count(*) = 1
15)