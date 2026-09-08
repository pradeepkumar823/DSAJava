1WITH
2first_positive AS (
3    SELECT 
4        patient_id
5        ,MIN(test_date) AS first_positive_date
6    FROM covid_tests
7    WHERE result='Positive'
8    GROUP BY patient_id
9)
10,first_negative_after_positive AS(
11    SELECT 
12        fp.patient_id
13        ,MIN(ct.test_date) AS first_negative
14    FROM first_positive fp
15    JOIN covid_tests ct
16        ON fp.patient_id=ct.patient_id
17    WHERE ct.result='Negative' 
18        AND first_positive_date<ct.test_date
19    GROUP BY fp.patient_id
20)
21SELECT 
22    p.patient_id
23    ,p.patient_name
24    ,p.age
25    ,DATEDIFF(fn.first_negative ,fp.first_positive_date) AS recovery_time
26FROM first_positive fp
27JOIN first_negative_after_positive fn
28    ON fp.patient_id=fn.patient_id
29JOIN patients p
30    ON fp.patient_id=p.patient_id
31ORDER BY recovery_time ASC, p.patient_name ASC