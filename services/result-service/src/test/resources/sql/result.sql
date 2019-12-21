INSERT INTO result (id_task, id_user, count_attempt, created_at, mark)
VALUES (random_uuid(), random_uuid(), 0, CURRENT_DATE(), 0),
(random_uuid(), random_uuid(), 3, CURRENT_DATE(), 80),
(random_uuid(), random_uuid(), 1, CURRENT_DATE(), 100),
(random_uuid(), random_uuid(), 1, CURRENT_DATE(), 100),
(random_uuid(), random_uuid(), 1, CURRENT_DATE(), 100);