INSERT INTO things.things_users (id, user_name, password, email, first_name, last_name, phone_number, is_account_non_expired, is_account_non_locked, is_credentials_non_expired, is_enabled, create_date) VALUES (1, 'testuser', '$2a$10$IQNJXp13YUwPbZS363d4yOUaftpFuz0J..5Se7MHiuIAXD/aR/hwq', 'testuser@example.com', 'testuser', 'testuser', '1234567890', true, true, true, true, '2024-10-31 17:58:36.264000');
INSERT INTO things.authorities (id, name) VALUES (1, 'ADMIN');
INSERT INTO things.authorities (id, name) VALUES (2, 'USER');
INSERT INTO things.user_roles (user_id, role_id) VALUES (1, 1);