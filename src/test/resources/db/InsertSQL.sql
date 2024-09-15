INSERT INTO teacher(first_name, last_name) VALUES ('Аркадий', 'Паровозов');
INSERT INTO teacher(first_name, last_name, patronymic) VALUES ('Мирон', 'Лаврентьев', 'Ильдусович');
INSERT INTO teacher(first_name, last_name, patronymic) VALUES ('Арина', 'Березина', 'Петровна');

INSERT INTO subject(name) VALUES ('Математика');
INSERT INTO subject(name) VALUES ('Внедрение информационных систем');
INSERT INTO subject(name) VALUES ('Информационные технологии');

INSERT INTO subject_teacher(subject_id, teacher_id) VALUES (1, 1);
INSERT INTO subject_teacher(subject_id, teacher_id) VALUES (1, 2);

INSERT INTO "group"(name, start_date, end_date, teacher_id) VALUES ('П-41', '01.09.2020', '01.07.2024', 1);
INSERT INTO "group"(name, start_date, end_date, teacher_id) VALUES ('П-31', '01.09.2021', '01.07.2025', 2);
INSERT INTO "group"(name, start_date, end_date, teacher_id) VALUES ('Э-11', '01.09.2023', '01.07.2028', 1);

INSERT INTO student(first_name, last_name, patronymic, group_id) VALUES ('Стефания', 'Крюкова', 'Артёмовна', 1);
INSERT INTO student(first_name, last_name, patronymic, group_id) VALUES ('Максимова', 'Пелагея', 'Артёмовна', 1);
INSERT INTO student(first_name, last_name, patronymic, group_id) VALUES ('Илья', 'Родин', 'Артёмович', 2);