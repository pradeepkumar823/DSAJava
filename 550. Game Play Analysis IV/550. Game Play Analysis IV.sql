1# Write your MySQL query statement below
2SELECT 
3    ROUND(
4        COUNT(DISTINCT a1.player_id) / 
5        (SELECT COUNT(DISTINCT player_id) FROM activity) 
6    ,2) 
7    AS fraction
8FROM 
9    (SELECT player_id, MIN(event_date) AS first_login
10    FROM activity
11    GROUP BY player_id) a1
12JOIN activity a2
13    ON a1.player_id=a2.player_id
14    AND a2.event_date=DATE_ADD(a1.first_login, INTERVAL 1 DAY)