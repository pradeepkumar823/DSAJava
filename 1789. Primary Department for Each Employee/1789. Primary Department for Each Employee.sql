1# Write your MySQL query statement below
2SELECT employee_id,department_id
3from Employee
4where primary_flag='Y' or
5   employee_id in (
6    select employee_id
7    from Employee
8    group by employee_id
9    having count(*)=1
10)
11