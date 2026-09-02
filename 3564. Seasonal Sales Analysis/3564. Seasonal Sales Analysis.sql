1# Write your MySQL query statement below
2select season,category,total_quantity,total_revenue 
3from(
4    select case when month(sale_date) between 3 and 5 then 'Spring'
5                when month(sale_date) between 6 and 8 then 'Summer'
6                when month(sale_date) between 9 and 11 then 'Fall'
7                else 'Winter' end as season,
8                category,
9                sum(quantity)as total_quantity,
10                sum(quantity*price) as total_revenue 
11    from sales s
12    join products p
13    on s.product_id=p.product_id
14    group by season,category
15    order by total_quantity desc, total_revenue  desc
16)t
17group by season
18order by season;