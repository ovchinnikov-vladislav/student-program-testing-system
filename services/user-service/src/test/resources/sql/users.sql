INSERT INTO users (id_user, user_name, pass, email, first_name, last_name, gr, status, created_at)
VALUES (random_uuid(), 'admin', '25D55AD283AA400AF464C76D713C07AD', 'admin@gmail.com', 'admin', 'admin', 1, 0, current_date()),
       (random_uuid(), 'user1', '25D55AD283AA400AF464C76D713C07AD', 'user1@gmail.com', 'user1fn', 'user1ln', 0, 0, current_date()),
       (random_uuid(), 'user2', '25D55AD283AA400AF464C76D713C07AD', 'user2@gmail.com', 'user2fn', 'user2ln', 0, 0, current_date());
