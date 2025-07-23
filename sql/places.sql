--Home 1 level
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (1, 'Home', null, 1, 'Appartment in Borschagovka');

--Home 2 level
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (2, 'Hall',    1, 2, 'Appartment in Borschagovka');
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (3, 'Room',    1, 2, 'Dacha');
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (4, 'Balcony', 1, 2, 'Mother Appartment');

--Home 3 level
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (5, 'Shelf', 2, 3, 'Shelf in the corridor');
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (6, 'Shelf', 3, 3, 'Shelf in the room');
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (7, 'Shelf', 4, 3, 'Shelf on the balcony');


--Dacha 1 level
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (8, 'Dacha', null, 1, 'Dacha');
--Dacha 2 level
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (9, 'Garage',             8, 2, '');
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (10, 'Basement',          8, 2, 'Room near the garage');
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (11, 'Kitchen',           8, 2, '');
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (12, 'Living room',       8, 2, '');
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (13, 'Room 1 on 2 floor', 8, 2, '');
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (14, 'Room 2 on 2 floor', 8, 2, '');
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (15, 'Hall on 2 floor',   8, 2, '');
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (16, 'Attic',             8, 2, '');
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (17, 'Utility block',     8, 2, 'Bathroom, shower');

--Dacha 3 level
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (18, 'Shelf', 9, 3, 'Shelf in garage'); --Basement
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (19, 'Shelf', 10, 3, 'Shelf in basement'); --Basement
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (20, 'Nightstand', 12, 3, 'in livingroom'); --1 floor
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (21, 'Shelf', 12, 3, 'in livingroom'); --1 floor

INSERT INTO things.places (id, name, parent_id, level, description) VALUES (22, 'Shelf', 13, 3, 'in 1 room'); --2 floor
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (23, 'Shelf', 14, 3, 'in 2 room'); --2 floor
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (24, 'Book shelf 1', 15, 3, 'in hall of 2 floor'); --2 floor
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (25, 'Book shelf 2', 15, 3, 'in hall of 2 floor'); --2 floor
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (26, 'Floor', 16, 3, 'floor of the attic'); --Attic

INSERT INTO things.places (id, name, parent_id, level, description) VALUES (27, 'Bathroom', 17, 3, 'Bathroom, shower');
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (28, 'Sauna', 17, 3, 'Bathroom, shower');
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (29, 'Henhouse', 17, 3, 'Bathroom, shower');

-- Mother flat 1 level
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (30, 'Mother flat', null, 1, 'Mother flat');

-- Mother flat 2 level
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (31, 'Big room mother flat', 30, 2, 'Mother flat');
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (32, 'Small room mother flat', 30, 2, 'Mother flat');
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (33, 'Hall mother flat', 30, 2, 'Mother flat');

-- Mother flat 3 level
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (34, 'Shelf', 31, 3, 'in big room');
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (35, 'Shelf', 32, 3, 'in small room');
INSERT INTO things.places (id, name, parent_id, level, description) VALUES (36, 'Shelf', 33, 3, 'hall');
