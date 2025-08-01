--Home 1 level
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (1, 'Home', null, 1, 'квартира на Борщаговке');

--Home 2 level
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (2, 'Hall',    1, 2, 'коридор на Борщаговке');
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (3, 'Room',    1, 2, 'комната на Борщаговке');
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (4, 'Balcony', 1, 2, 'балкон на Борщаговке');

--Home 3 level
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (5, 'Shelf', 2, 3, 'шкаф в коридоре');
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (6, 'Shelf', 3, 3, 'шкаф в комнате');
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (7, 'Shelf', 4, 3, 'шкаф на балконе');


--Dacha 1 level
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (8, 'Dacha', null, 1, 'дача');
--Dacha 2 level
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (9, 'Garage',             8, 2, 'гараж на даче');
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (10, 'Basement',          8, 2, 'комната в подвале');
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (11, 'Kitchen',           8, 2, 'кухня на даче');
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (12, 'Living room',       8, 2, 'гостиная на 1 этаже');
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (13, 'Room 1 on 2 floor', 8, 2, 'комната 1 на 2 этаже');
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (14, 'Room 2 on 2 floor', 8, 2, 'комната 2 на 2 этаже');
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (15, 'Hall on 2 floor',   8, 2, 'коридор на 2 этаже');
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (16, 'Attic',             8, 2, 'чердак на даче');
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (17, 'Utility block',     8, 2, 'хозблок');

--Dacha 3 level
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (18, 'Shelf', 9, 3, 'стелаж в гараже'); --Basement
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (19, 'Shelf', 10, 3, 'стелаж в гараже'); --Basement
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (20, 'Nightstand', 12, 3, 'тумба в гостинной'); --1 floor
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (21, 'Shelf', 12, 3, 'шкаф в гостинной'); --1 floor

INSERT INTO things.places (id, name, parent_id, level, description) VALUES (22, 'Shelf', 13, 3, 'шкаф в 1-й комнате'); --2 floor
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (23, 'Shelf', 14, 3, 'шкаф во 2-й комнате'); --2 floor
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (24, 'Book shelf 1', 15, 3, 'книжный 1 на 2-м этаже'); --2 floor
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (25, 'Book shelf 2', 15, 3, 'книжный 2 на 2-м этаже'); --2 floor
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (26, 'Floor', 16, 3, 'пол на чердаке'); --Attic

INSERT INTO things.places (id, name, parent_id, level, description) VALUES (27, 'Bathroom', 17, 3, 'ванная');
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (28, 'Sauna', 17, 3, 'баня');
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (29, 'Henhouse', 17, 3, 'курятник');

-- Mother flat 1 level
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (30, 'Mother flat', null, 1, 'квартира мамы');

-- Mother flat 2 level
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (31, 'Big room mother flat', 30, 2, 'большая комната маминой квартиры');
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (32, 'Small room mother flat', 30, 2, 'маленькая комната маминой квартиры');
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (33, 'Hall mother flat', 30, 2, 'коридор маминой квартиры');

-- Mother flat 3 level
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (34, 'Shelf', 31, 3, 'шкаф в большой комнате');
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (35, 'Shelf', 32, 3, 'шкаф в маленькой комнате');
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (36, 'Shelf', 33, 3, 'шкаф в коридоре');
