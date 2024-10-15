INSERT INTO teacher(first_name, last_name) VALUES ('Аркадий', 'Паровозов');
INSERT INTO teacher(first_name, last_name, patronymic) VALUES ('Мирон', 'Лаврентьев', 'Ильдусович');
INSERT INTO teacher(first_name, last_name, patronymic) VALUES ('Арина', 'Березина', 'Петровна');

INSERT INTO subject(name) VALUES ('Математика');
INSERT INTO subject(name) VALUES ('Внедрение информационных систем');
INSERT INTO subject(name) VALUES ('Информационные технологии');

INSERT INTO subject_teacher(subject_id, teacher_id) VALUES (1, 1);
INSERT INTO subject_teacher(subject_id, teacher_id) VALUES (1, 2);

INSERT INTO "group"(name, start_date, end_date, teacher_id) VALUES ('П-41', '2020-09-01', '2024-07-01', 1);
INSERT INTO "group"(name, start_date, end_date, teacher_id) VALUES ('П-31', '2021-09-01', '2025-07-01', 2);
INSERT INTO "group"(name, start_date, end_date, teacher_id) VALUES ('Э-11', '2023-09-01', '2028-07-01', 1);

INSERT INTO student(first_name, last_name, patronymic, group_id) VALUES ('Стефания', 'Крюкова', 'Артёмовна', 1);
INSERT INTO student(first_name, last_name, patronymic, group_id) VALUES ('Максимова', 'Пелагея', 'Артёмовна', 1);
INSERT INTO student(first_name, last_name, patronymic, group_id) VALUES ('Илья', 'Родин', 'Артёмович', 2);

INSERT INTO exam(start_date, group_id, subject_id, teacher_id) VALUES ('2021-12-13', 1, 2, 1);
INSERT INTO exam(start_date, group_id, subject_id, teacher_id) VALUES ('2023-12-02', 2, 1, 1);
INSERT INTO exam(start_date, group_id, subject_id, teacher_id) VALUES ('2024-01-05', 2, 1, 1);

INSERT INTO grade(student_id, exam_id, mark) VALUES (1, 1, 4);
INSERT INTO grade(student_id, exam_id, mark) VALUES (2, 1, 5);
INSERT INTO grade(student_id, exam_id, mark) VALUES (2, 2, 5);

INSERT INTO subject_group(subject_id, group_id) VALUES (2, 1);
INSERT INTO subject_group(subject_id, group_id) VALUES (1, 2);