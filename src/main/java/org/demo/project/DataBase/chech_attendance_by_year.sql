-- считает количество посещений за последний год
SELECT * FROM attendance WHERE client_id = 2 and date >= (NOW() - INTERVAL '365 DAYS')