1# Write your MySQL query statement below
2
3select employee_id
4from Employees
5where salary<30000 and manager_id not in(
6    select employee_id
7    from employees
8)
9order by employee_id;