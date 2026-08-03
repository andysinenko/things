delete from things.genre;

INSERT INTO things.genres (id, name) VALUES (1, 'Роман');
INSERT INTO things.genres (id, name) VALUES (2, 'Фантастика');
INSERT INTO things.genres (id, name) VALUES (3, 'Фэнтези');
INSERT INTO things.genres (id, name) VALUES (4, 'Детектив');
INSERT INTO things.genres (id, name) VALUES (5, 'Триллер');
INSERT INTO things.genres (id, name) VALUES (6, 'Приключения');
INSERT INTO things.genres (id, name) VALUES (7, 'Исторический роман');
INSERT INTO things.genres (id, name) VALUES (8, 'Биография');
INSERT INTO things.genres (id, name) VALUES (9, 'Мемуары');
INSERT INTO things.genres (id, name) VALUES (10, 'Научная литература');
INSERT INTO things.genres (id, name) VALUES (11, 'Научно-популярная литература');
INSERT INTO things.genres (id, name) VALUES (12, 'Философия');
INSERT INTO things.genres (id, name) VALUES (13, 'Поэзия');
INSERT INTO things.genres (id, name) VALUES (14, 'Драма');
INSERT INTO things.genres (id, name) VALUES (15, 'Комедия');
INSERT INTO things.genres (id, name) VALUES (16, 'Ужасы');
INSERT INTO things.genres (id, name) VALUES (17, 'Религиозная литература');
INSERT INTO things.genres (id, name) VALUES (18, 'Эссе');
INSERT INTO things.genres (id, name) VALUES (19, 'Справочная литература');
INSERT INTO things.genres (id, name) VALUES (20, 'Детская литература');
INSERT INTO things.genres (id, name) VALUES (21, 'Юмор и сатира');
INSERT INTO things.genres (id, name) VALUES (22, 'Фольклор');
INSERT INTO things.genres (id, name) VALUES (23, 'Мифология');
INSERT INTO things.genres (id, name) VALUES (24, 'Эпопея');
INSERT INTO things.genres (id, name) VALUES (25, 'Эротическая литература');
INSERT INTO things.genres (id, name) VALUES (26, 'Публицистика');
INSERT INTO things.genres (id, name) VALUES (27, 'Политическая литература');
INSERT INTO things.genres (id, name) VALUES (28, 'Фэнтези-эпик');
INSERT INTO things.genres (id, name) VALUES (29, 'Киберпанк');
INSERT INTO things.genres (id, name) VALUES (30, 'Постапокалипсис');
INSERT INTO things.genres (id, name) VALUES (31, 'Альтернативная история');
INSERT INTO things.genres (id, name) VALUES (32, 'Магический реализм');
INSERT INTO things.genres (id, name) VALUES (33, 'Социальная проза');
INSERT INTO things.genres (id, name) VALUES (34, 'Классическая литература');
INSERT INTO things.genres (id, name) VALUES (35, 'Современная литература');
INSERT INTO things.genres (id, name) VALUES (36, 'Любовный роман');
INSERT INTO things.genres (id, name) VALUES (37, 'Военная проза');
INSERT INTO things.genres (id, name) VALUES (38, 'ЛитРПГ');
INSERT INTO things.genres (id, name) VALUES (39, 'Хай-тек фантастика');
INSERT INTO things.genres (id, name) VALUES (40, 'Космическая опера');
INSERT INTO things.genres (id, name) VALUES (41, 'Паропанк (стимпанк)');
INSERT INTO things.genres (id, name) VALUES (42, 'Антиутопия');
INSERT INTO things.genres (id, name) VALUES (43, 'Утопия');
INSERT INTO things.genres (id, name) VALUES (44, 'Готическая литература');
INSERT INTO things.genres (id, name) VALUES (45, 'Полицейский роман');
INSERT INTO things.genres (id, name) VALUES (46, 'Шпионский роман');
INSERT INTO things.genres (id, name) VALUES (47, 'Юмористическая фантастика');
INSERT INTO things.genres (id, name) VALUES (48, 'Психологическая проза');
INSERT INTO things.genres (id, name) VALUES (49, 'Критика и литературоведение');
INSERT INTO things.genres (id, name) VALUES (50, 'Пьеса');
INSERT INTO things.genres (id, name) VALUES (51, 'IT: алгоритмы');
INSERT INTO things.genres (id, name) VALUES (52, 'IT: криптография');
INSERT INTO things.genres (id, name) VALUES (53, 'IT: языки программирования');
INSERT INTO things.genres (id, name) VALUES (54, 'IT: сети');

INSERT INTO things.genres (id, name) VALUES (55, 'IT: Operating systems');

SELECT setval('things.author_sequence', COALESCE((SELECT MAX(id) FROM things.authors), 1));
ALTER TABLE things.authors
    ALTER COLUMN id SET DEFAULT nextval('things.author_sequence');
ALTER TABLE things.series
    ALTER COLUMN id SET DEFAULT nextval('things.series_sequence');
SELECT setval('things.series_sequence', COALESCE((SELECT MAX(id) FROM things.series), 1));

INSERT INTO things.authors (name, note, genre_id) VALUES ( 'Брюс Шнайдер', '', 52 );
INSERT INTO things.authors (name, note, genre_id) VALUES ( 'Бьёрн Страуструп', '', 53 );
INSERT INTO things.authors (name, note, genre_id) VALUES ( 'Николас Солтер', '', 53 );
INSERT INTO things.authors (name, note, genre_id) VALUES ( 'Скотт Дж.Клепер', '', 53 );
INSERT INTO things.authors (name, note, genre_id) VALUES ( 'Стефан Кочан', '', 53 );
INSERT INTO things.authors (name, note, genre_id) VALUES ( 'Банахан М', '', 55 );
INSERT INTO things.authors (name, note, genre_id) VALUES ( 'Тим Макнамара', '', 53 );
INSERT INTO things.authors (name, note, genre_id) VALUES ( 'Макс Шлее', '', 53 );
INSERT INTO things.authors (name, note, genre_id) VALUES ( 'Андрей Александреску', '', 53 );
INSERT INTO things.authors (name, note, genre_id) VALUES ( 'Герб Саттер', '', 53 );
INSERT INTO things.authors (name, note, genre_id) VALUES ( 'В.Н.Пильщиков', '', 53 );
INSERT INTO things.authors (name, note, genre_id) VALUES ( 'Александр Степанов', 'A.Stepanov', 53 );
INSERT INTO things.authors (name, note, genre_id) VALUES ( 'Себастьян Дашнер', 'Sebastian Dashner', 53 );

INSERT INTO things.authors (name, note, genre_id) VALUES ( 'Дэвид Гери', '', 53 );
INSERT INTO things.authors (name, note, genre_id) VALUES ( 'Кей Хорстман', '', 53 );

INSERT INTO things.series (name, note) VALUES ( 'В действии', 'In action');
INSERT INTO things.series (name, note) VALUES ( 'Для профессионалов', 'Professional');
INSERT INTO things.series (name, note) VALUES ( 'В подлиннике', '');
INSERT INTO things.series (name, note) VALUES ( 'C++ In-Depth', '');
INSERT INTO things.series (name, note) VALUES ( 'Библиотека профессионала', '');
