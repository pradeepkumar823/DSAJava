1SELECT
2    a.visited_on,
3    SUM(b.amount) AS amount,
4    ROUND(SUM(b.amount) / 7, 2) AS average_amount
5FROM (
6    SELECT DISTINCT visited_on
7    FROM Customer
8) a
9JOIN Customer b
10  ON b.visited_on BETWEEN DATE_SUB(a.visited_on, INTERVAL 6 DAY) AND a.visited_on
11GROUP BY a.visited_on
12HAVING COUNT(DISTINCT b.visited_on) = 7
13ORDER BY a.visited_on;