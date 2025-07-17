INSERT INTO things.authorities (id, name) VALUES (1, 'ADMIN');
INSERT INTO things.authorities (id, name) VALUES (2, 'USER');

INSERT INTO things.authors (id, name, note) VALUES (1, 'Author1', 'note1');
INSERT INTO things.authors (id, name, note) VALUES (2, 'Author2', 'note2');
INSERT INTO things.authors (id, name, note) VALUES (3, 'Author3', 'note3');
INSERT INTO things.authors (id, name, note) VALUES (4, 'Author4', 'note4');
INSERT INTO things.authors (id, name, note) VALUES (5, 'Author5', 'note5');

INSERT INTO things.genre (id, name) values (1, 'genre A');
INSERT INTO things.genre (id, name) values (2, 'genre A');
INSERT INTO things.genre (id, name) values (3, 'genre A');
INSERT INTO things.genre (id, name) values (4, 'genre A');
INSERT INTO things.genre (id, name) values (5, 'genre A');
INSERT INTO things.genre (id, name) values (6, 'genre A');

INSERT INTO things.series (id, name) values (1, 'series A');
INSERT INTO things.series (id, name) values (2, 'series A');
INSERT INTO things.series (id, name) values (3, 'series A');
INSERT INTO things.series (id, name) values (4, 'series A');
INSERT INTO things.series (id, name) values (5, 'series A');
INSERT INTO things.series (id, name) values (6, 'series A');


DELETE FROM things.books;

INSERT INTO things.books (id, title, genre_id, series_id, publisher, year, volume_number, description)
VALUES (1, 'book 1 title', 1, 1, 'publisher A', '1975-01-01', '1', 'description A');
INSERT INTO things.books (id, title, genre_id, series_id, publisher, year, volume_number, description)
VALUES (2, 'book 2 title', 1, 1, 'publisher B', '1975-01-01', '2', 'description B');
INSERT INTO things.books (id, title, genre_id, series_id, publisher, year, volume_number, description)
VALUES (3, 'book 3 title', 1, 1, 'publisher C', '1975-01-01', '3', 'description C');
INSERT INTO things.books (id, title, genre_id, series_id, publisher, year, volume_number, description)
VALUES (4, 'book 4 title', 1, 1, 'publisher D', '1975-01-01', '4', 'description D');
INSERT INTO things.books (id, title, genre_id, series_id, publisher, year, volume_number, description)
VALUES (5, 'book 5 title', 1, 1, 'publisher E', '1975-01-01', '5', 'description E');
INSERT INTO things.books (id, title, genre_id, series_id, publisher, year, volume_number, description)
VALUES (6, 'book 6 title', 1, 1, 'publisher F', '1975-01-01', '6', 'description F');


INSERT INTO things.books_authors (book_id, author_id) VALUES (1, 1);
INSERT INTO things.books_authors (book_id, author_id) VALUES (1, 2);
INSERT INTO things.books_authors (book_id, author_id) VALUES (2, 2);
INSERT INTO things.books_authors (book_id, author_id) VALUES (3, 3);
INSERT INTO things.books_authors (book_id, author_id) VALUES (4, 4);
INSERT INTO things.books_authors (book_id, author_id) VALUES (5, 5);
INSERT INTO things.books_authors (book_id, author_id) VALUES (6, 1);

INSERT INTO things.places (id, name, parent_id, description) VALUES (1, 'Home', null, 'Appartment in Borschagovka');
INSERT INTO things.places (id, name, parent_id, description) VALUES (2, 'Dacha', null, 'Dacha');
INSERT INTO things.places (id, name, parent_id, description) VALUES (3, 'Mama', null, 'Mother Appartment');

INSERT INTO things.things_tokens (id, token, revoked, expired, things_user) VALUES (1, 'eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJTaW5lbmtvLmNvbS51YSIsInN1YiI6InRlc3R1c2VyIiwiaWF0IjoxNzMwMzkwMzE2LCJleHAiOjE3MzA0NzY3MTZ9.Atj7fp5szmkxrBpRATw_8o1c03Ha6OmYNBLk92JIekI', true, true, 1);
INSERT INTO things.things_tokens (id, token, revoked, expired, things_user) VALUES (7, 'eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJTaW5lbmtvLmNvbS51YSIsInN1YiI6InRlc3R1c2VyIiwiaWF0IjoxNzMwNTY2NDQwLCJleHAiOjE3MzA2NTI4NDB9.SgCRzp2IChCZC26ls-cUVE0Zta9xLogVgunmrv5qeHM', true, true, 1);
INSERT INTO things.things_tokens (id, token, revoked, expired, things_user) VALUES (8, 'eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJTaW5lbmtvLmNvbS51YSIsInN1YiI6InRlc3R1c2VyIiwiaWF0IjoxNzMwNTY3MTgxLCJleHAiOjE3MzA2NTM1ODF9.kfZNrew2RlifskM3ImyU32yaB7wZvBPWFkRgHS45_Fw', true, true, 1);
INSERT INTO things.things_tokens (id, token, revoked, expired, things_user) VALUES (9, 'eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJTaW5lbmtvLmNvbS51YSIsInN1YiI6InRlc3R1c2VyIiwiaWF0IjoxNzMwNTY3MzI0LCJleHAiOjE3MzA2NTM3MjR9.useqX0LiKSb4Tk-QUa4vkjMQgLdhO7ZU5_u7xCDLDp8', true, true, 1);
INSERT INTO things.things_tokens (id, token, revoked, expired, things_user) VALUES (10, 'eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJTaW5lbmtvLmNvbS51YSIsInN1YiI6InRlc3R1c2VyIiwiaWF0IjoxNzMwNTY3NDg0LCJleHAiOjE3MzA2NTM4ODR9.hAeS8SaX7lZ3lpOITDB7DvBG98VNAM_nsdqAiBAWQDg', true, true, 1);
INSERT INTO things.things_tokens (id, token, revoked, expired, things_user) VALUES (11, 'eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJTaW5lbmtvLmNvbS51YSIsInN1YiI6InRlc3R1c2VyIiwiaWF0IjoxNzMwNTY3Njc2LCJleHAiOjE3MzA2NTQwNzZ9.pNcl_JIsmbCbrs_d8NLwkgdKAKooEyX7D29x4_wLmWc', true, true, 1);
INSERT INTO things.things_tokens (id, token, revoked, expired, things_user) VALUES (12, 'eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJTaW5lbmtvLmNvbS51YSIsInN1YiI6InRlc3R1c2VyIiwiaWF0IjoxNzMwNTY3OTI3LCJleHAiOjE3MzA2NTQzMjd9.dVUyIuP9xK4MjbD_2XneU7uALCHhcagHk_TGSIg-EYM', true, true, 1);
INSERT INTO things.things_tokens (id, token, revoked, expired, things_user) VALUES (13, 'eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJTaW5lbmtvLmNvbS51YSIsInN1YiI6InRlc3R1c2VyIiwiaWF0IjoxNzMxMjUyMDI1LCJleHAiOjE3MzEzMzg0MjV9.TjjE3DgIipNJ9bOljsfc8yYojdPERK_sEw74dVROILc', true, true, 1);
INSERT INTO things.things_tokens (id, token, revoked, expired, things_user) VALUES (14, 'eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJTaW5lbmtvLmNvbS51YSIsInN1YiI6InRlc3R1c2VyIiwiaWF0IjoxNzMxMjU2MTQxLCJleHAiOjE3MzEzNDI1NDF9.QvpK5yNiWfa5RO5D9RO-ye9X9kQ2YhWbgcAFON4LXlM', true, true, 1);
INSERT INTO things.things_tokens (id, token, revoked, expired, things_user) VALUES (15, 'eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJTaW5lbmtvLmNvbS51YSIsInN1YiI6InRlc3R1c2VyIiwiaWF0IjoxNzM0NTI2NTgzLCJleHAiOjE3MzQ2MTI5ODN9.3FPqtbuGDzHM7JEKGV2hzFdeg1D8fUb9eJeC9TjqkCs', false, false, 1);



INSERT INTO things.things_users (id, user_name, password, email, first_name, last_name, phone_number, is_account_non_expired, is_account_non_locked, is_credentials_non_expired, is_enabled, create_date)
VALUES (1, 'testuser', '$2a$10$IQNJXp13YUwPbZS363d4yOUaftpFuz0J..5Se7MHiuIAXD/aR/hwq', 'testuser@example.com', 'testuser', 'testuser', '1234567890', true, true, true, true, '2024-10-31 17:58:36.264000');


INSERT INTO things.user_roles (user_id, role_id) VALUES (1, 1);

commit;
