INSERT INTO attendance (date, client_id)
SELECT '2020-11-17' AS date, 2 AS client_id FROM attendance
WHERE NOT EXISTS(
        SELECT id FROM attendance WHERE client_id = 2 AND date = '2020-11-17'
    )
LIMIT 1;
-- если убрать LIMIT 1, то начнётся ад