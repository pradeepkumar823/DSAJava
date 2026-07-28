WITH machine_id_process_id_start_time AS (
    SELECT a.machine_id AS machine_id, a.process_id AS process_id, a.timestamp AS timestamp
    FROM Activity a
    WHERE a.activity_type IN ('start')
    ORDER BY a.process_id ASC
),
machine_id_process_id_end_time AS (
    SELECT a.machine_id AS machine_id, a.process_id AS process_id, a.timestamp AS timestamp
    FROM Activity a
    WHERE a.activity_type IN ('end')
    ORDER BY a.process_id ASC
),
machine_id_process_id_total_time AS (
    SELECT a1.machine_id AS machine_id, a1.process_id AS process_id, a2.timestamp - a1.timestamp AS total_time
    FROM machine_id_process_id_start_time a1
    INNER JOIN machine_id_process_id_end_time a2
    ON a1.machine_id = a2.machine_id AND a1.process_id = a2.process_id
)

SELECT a.machine_id AS machine_id, ROUND(CAST(SUM(a.total_time)/COUNT(a.process_id) AS numeric), 3) AS processing_time
FROM machine_id_process_id_total_time a
GROUP BY a.machine_id
ORDER BY a.machine_id ASC