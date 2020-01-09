INSERT INTO result (id_task, id_user, count_attempt, created_at, updated_at, status, mark)
VALUES (random_uuid(), random_uuid(), 0, CURRENT_DATE(), CURRENT_DATE(), 0, 0),
(random_uuid(), random_uuid(), 3, CURRENT_DATE(), CURRENT_DATE(), 0, 80),
(random_uuid(), random_uuid(), 1, CURRENT_DATE(), CURRENT_DATE(), 0, 100),
(random_uuid(), random_uuid(), 1, CURRENT_DATE(), CURRENT_DATE(), 0, 100),
(random_uuid(), random_uuid(), 1, CURRENT_DATE(), CURRENT_DATE(), 0, 100);