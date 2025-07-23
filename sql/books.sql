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

INSERT INTO things.books (id, genre_id, series_id, publisher, year, volume_number, description, title) VALUES (1, 1, 1, 'publisher A', '1975-01-01', '1', null, 'series A');
INSERT INTO things.books (id, genre_id, series_id, publisher, year, volume_number, description, title) VALUES (2, 1, 1, 'publisher B', '1975-01-01', '2', null, 'series B');
INSERT INTO things.books (id, genre_id, series_id, publisher, year, volume_number, description, title) VALUES (3, 1, 1, 'publisher C', '1975-01-01', '3', null, 'series C');
INSERT INTO things.books (id, genre_id, series_id, publisher, year, volume_number, description, title) VALUES (4, 1, 1, 'publisher D', '1975-01-01', '4', null, 'series D');
INSERT INTO things.books (id, genre_id, series_id, publisher, year, volume_number, description, title) VALUES (5, 1, 1, 'publisher E', '1975-01-01', '5', null, 'series E');
INSERT INTO things.books (id, genre_id, series_id, publisher, year, volume_number, description, title) VALUES (6, 1, 1, 'publisher F', '1975-01-01', '6', null, 'series F');
