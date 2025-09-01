delete from things.genre;

INSERT INTO things.genre (id, name) VALUES (1, 'Роман');
INSERT INTO things.genre (id, name) VALUES (2, 'Фантастика');
INSERT INTO things.genre (id, name) VALUES (3, 'Фэнтези');
INSERT INTO things.genre (id, name) VALUES (4, 'Детектив');
INSERT INTO things.genre (id, name) VALUES (5, 'Триллер');
INSERT INTO things.genre (id, name) VALUES (6, 'Приключения');
INSERT INTO things.genre (id, name) VALUES (7, 'Исторический роман');
INSERT INTO things.genre (id, name) VALUES (8, 'Биография');
INSERT INTO things.genre (id, name) VALUES (9, 'Мемуары');
INSERT INTO things.genre (id, name) VALUES (10, 'Научная литература');
INSERT INTO things.genre (id, name) VALUES (11, 'Научно-популярная литература');
INSERT INTO things.genre (id, name) VALUES (12, 'Философия');
INSERT INTO things.genre (id, name) VALUES (13, 'Поэзия');
INSERT INTO things.genre (id, name) VALUES (14, 'Драма');
INSERT INTO things.genre (id, name) VALUES (15, 'Комедия');
INSERT INTO things.genre (id, name) VALUES (16, 'Ужасы');
INSERT INTO things.genre (id, name) VALUES (17, 'Религиозная литература');
INSERT INTO things.genre (id, name) VALUES (18, 'Эссе');
INSERT INTO things.genre (id, name) VALUES (19, 'Справочная литература');
INSERT INTO things.genre (id, name) VALUES (20, 'Детская литература');
INSERT INTO things.genre (id, name) VALUES (21, 'Юмор и сатира');
INSERT INTO things.genre (id, name) VALUES (22, 'Фольклор');
INSERT INTO things.genre (id, name) VALUES (23, 'Мифология');
INSERT INTO things.genre (id, name) VALUES (24, 'Эпопея');
INSERT INTO things.genre (id, name) VALUES (25, 'Эротическая литература');
INSERT INTO things.genre (id, name) VALUES (26, 'Публицистика');
INSERT INTO things.genre (id, name) VALUES (27, 'Политическая литература');
INSERT INTO things.genre (id, name) VALUES (28, 'Фэнтези-эпик');
INSERT INTO things.genre (id, name) VALUES (29, 'Киберпанк');
INSERT INTO things.genre (id, name) VALUES (30, 'Постапокалипсис');
INSERT INTO things.genre (id, name) VALUES (31, 'Альтернативная история');
INSERT INTO things.genre (id, name) VALUES (32, 'Магический реализм');
INSERT INTO things.genre (id, name) VALUES (33, 'Социальная проза');
INSERT INTO things.genre (id, name) VALUES (34, 'Классическая литература');
INSERT INTO things.genre (id, name) VALUES (35, 'Современная литература');
INSERT INTO things.genre (id, name) VALUES (36, 'Любовный роман');
INSERT INTO things.genre (id, name) VALUES (37, 'Военная проза');
INSERT INTO things.genre (id, name) VALUES (38, 'ЛитРПГ');
INSERT INTO things.genre (id, name) VALUES (39, 'Хай-тек фантастика');
INSERT INTO things.genre (id, name) VALUES (40, 'Космическая опера');
INSERT INTO things.genre (id, name) VALUES (41, 'Паропанк (стимпанк)');
INSERT INTO things.genre (id, name) VALUES (42, 'Антиутопия');
INSERT INTO things.genre (id, name) VALUES (43, 'Утопия');
INSERT INTO things.genre (id, name) VALUES (44, 'Готическая литература');
INSERT INTO things.genre (id, name) VALUES (45, 'Полицейский роман');
INSERT INTO things.genre (id, name) VALUES (46, 'Шпионский роман');
INSERT INTO things.genre (id, name) VALUES (47, 'Юмористическая фантастика');
INSERT INTO things.genre (id, name) VALUES (48, 'Психологическая проза');
INSERT INTO things.genre (id, name) VALUES (49, 'Критика и литературоведение');
INSERT INTO things.genre (id, name) VALUES (50, 'Пьеса');

INSERT INTO things.series (id, name) values (1, 'series 1');
INSERT INTO things.series (id, name) values (2, 'series 2');
INSERT INTO things.series (id, name) values (3, 'series 3');
INSERT INTO things.series (id, name) values (4, 'series 4');
INSERT INTO things.series (id, name) values (5, 'series 5');
INSERT INTO things.series (id, name) values (6, 'series 6');
INSERT INTO things.series (id, name) values (7, 'series 7');
INSERT INTO things.series (id, name) values (8, 'series 8');
INSERT INTO things.series (id, name) values (9, 'series 9');
INSERT INTO things.series (id, name) values (10, 'series 10');



INSERT INTO things.books
(id, title, publication_year, volume_number, genre_id, series_id, description, place_id)
VALUES
    (1, 'Book 1', '1975-01-01', 1, 1, 1, 'Description of Book 1', 1),
    (2, 'Book 2', '1976-01-01', 2, 2, 1, 'Description of Book 2', 2),
    (3, 'Book 3', '1977-01-01', 3, 3, 2, 'Description of Book 3', 3),
    (4, 'Book 4', '1978-01-01', 4, 1, 2, 'Description of Book 4', 4),
    (5, 'Book 5', '1979-01-01', 5, 2, 3, 'Description of Book 5', 5),
    (6, 'Book 6', '1980-01-01', 6, 3, 3, 'Description of Book 6', 6),
    (7, 'Book 7', '1981-01-01', 7, 1, 4, 'Description of Book 7', 7),
    (8, 'Book 8', '1982-01-01', 8, 2, 4, 'Description of Book 8', 8),
    (9, 'Book 9', '1983-01-01', 9, 3, 5, 'Description of Book 9', 9),
    (10, 'Book 10', '1984-01-01', 10, 1, 5, 'Description of Book 10', 10),
    (11, 'Book 11', '1985-01-01', 11, 2, 6, 'Description of Book 11', 11),
    (12, 'Book 12', '1986-01-01', 12, 3, 6, 'Description of Book 12', 12),
    (13, 'Book 13', '1987-01-01', 13, 1, 7, 'Description of Book 13', 13),
    (14, 'Book 14', '1988-01-01', 14, 2, 7, 'Description of Book 14', 14),
    (15, 'Book 15', '1989-01-01', 15, 3, 8, 'Description of Book 15', 15),
    (16, 'Book 16', '1990-01-01', 16, 1, 8, 'Description of Book 16', 16),
    (17, 'Book 17', '1991-01-01', 17, 2, 9, 'Description of Book 17', 17),
    (18, 'Book 18', '1992-01-01', 18, 3, 9, 'Description of Book 18', 18),
    (19, 'Book 19', '1993-01-01', 19, 1, 10, 'Description of Book 19', 19),
    (20, 'Book 20', '1994-01-01', 20, 2, 10, 'Description of Book 20', 20),
    (21, 'Book 21', '1995-01-01', 21, 3, 1, 'Description of Book 21', 1),
    (22, 'Book 22', '1996-01-01', 22, 1, 2, 'Description of Book 22', 2),
    (23, 'Book 23', '1997-01-01', 23, 2, 3, 'Description of Book 23', 3),
    (24, 'Book 24', '1998-01-01', 24, 3, 4, 'Description of Book 24', 4),
    (25, 'Book 25', '1999-01-01', 25, 1, 5, 'Description of Book 25', 5),
    (26, 'Book 26', '2000-01-01', 26, 2, 6, 'Description of Book 26', 6),
    (27, 'Book 27', '2001-01-01', 27, 3, 7, 'Description of Book 27', 7),
    (28, 'Book 28', '2002-01-01', 28, 1, 8, 'Description of Book 28', 8),
    (29, 'Book 29', '2003-01-01', 29, 2, 9, 'Description of Book 29', 9),
    (30, 'Book 30', '2004-01-01', 30, 3, 10, 'Description of Book 30', 10),
    (31, 'Book 31', '2005-01-01', 31, 1, 1, 'Description of Book 31', 11),
    (32, 'Book 32', '2006-01-01', 32, 2, 2, 'Description of Book 32', 12),
    (33, 'Book 33', '2007-01-01', 33, 3, 3, 'Description of Book 33', 13),
    (34, 'Book 34', '2008-01-01', 34, 1, 4, 'Description of Book 34', 14),
    (35, 'Book 35', '2009-01-01', 35, 2, 5, 'Description of Book 35', 15),
    (36, 'Book 36', '2010-01-01', 36, 3, 6, 'Description of Book 36', 16),
    (37, 'Book 37', '2011-01-01', 37, 1, 7, 'Description of Book 37', 17),
    (38, 'Book 38', '2012-01-01', 38, 2, 8, 'Description of Book 38', 18),
    (39, 'Book 39', '2013-01-01', 39, 3, 9, 'Description of Book 39', 19),
    (40, 'Book 40', '2014-01-01', 40, 1, 10, 'Description of Book 40', 20),
    (41, 'Book 41', '2015-01-01', 41, 2, 1, 'Description of Book 41', 1),
    (42, 'Book 42', '2016-01-01', 42, 3, 2, 'Description of Book 42', 2),
    (43, 'Book 43', '2017-01-01', 43, 1, 3, 'Description of Book 43', 3),
    (44, 'Book 44', '2018-01-01', 44, 2, 4, 'Description of Book 44', 4),
    (45, 'Book 45', '2019-01-01', 45, 3, 5, 'Description of Book 45', 5),
    (46, 'Book 46', '2020-01-01', 46, 1, 6, 'Description of Book 46', 6),
    (47, 'Book 47', '2021-01-01', 47, 2, 7, 'Description of Book 47', 7),
    (48, 'Book 48', '2022-01-01', 48, 3, 8, 'Description of Book 48', 8),
    (49, 'Book 49', '2023-01-01', 49, 1, 9, 'Description of Book 49', 9),
    (50, 'Book 50', '2024-01-01', 50, 2, 10, 'Description of Book 50', 10);
