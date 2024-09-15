INSERT INTO teacher(first_name, last_name) VALUES ('Аркадий', 'Паровозов');
INSERT INTO teacher(first_name, last_name, patronymic) VALUES ('Мирон', 'Лаврентьев', 'Ильдусович');
INSERT INTO teacher(first_name, last_name, patronymic) VALUES ('Арина', 'Березина', 'Петровна');
INSERT INTO subject(name) VALUES ('Математика');
INSERT INTO subject(name) VALUES ('Внедрение информационных систем');
INSERT INTO subject(name) VALUES ('Информационные технологии');
INSERT INTO subject_teacher(subject_id, teacher_id) VALUES (1, 1);
INSERT INTO subject_teacher(subject_id, teacher_id) VALUES (1, 2);