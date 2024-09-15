CREATE DATABASE "AcademicPerformanceDatabase";


CREATE TABLE public.exam (
    id integer NOT NULL,
    start_date date NOT NULL,
    group_id integer NOT NULL,
    subject_teacher_id integer NOT NULL
);

ALTER TABLE public.exam ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.exam_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);

CREATE TABLE public.grade (
    id integer NOT NULL,
    student_id integer NOT NULL,
    exam_id integer NOT NULL,
    mark smallint DEFAULT 1 NOT NULL
);

ALTER TABLE public.grade ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.grade_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);

CREATE TABLE public."group" (
    id integer NOT NULL,
    name character varying(30) NOT NULL,
    start_date date NOT NULL,
    end_date date,
    teacher_id integer NOT NULL
);

ALTER TABLE public."group" ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.group_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);

CREATE TABLE public.student (
    id integer NOT NULL,
    first_name character varying(30) NOT NULL,
    last_name character varying(30) NOT NULL,
    patronymic character varying(30),
    group_id integer NOT NULL
);

ALTER TABLE public.student ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.student_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);

CREATE TABLE public.subject (
    id integer NOT NULL,
    name character varying(100) NOT NULL
);

ALTER TABLE public.subject ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.subject_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);

CREATE TABLE public.subject_teacher (
    id integer NOT NULL,
    subject_id integer NOT NULL,
    teacher_id integer NOT NULL
);

ALTER TABLE public.subject_teacher ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.subject_teacher_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);

CREATE TABLE public.teacher (
    id integer NOT NULL,
    first_name character varying(50) NOT NULL,
    last_name character varying(50) NOT NULL,
    patronymic character varying(50)
);

ALTER TABLE public.teacher ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.teacher_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);

ALTER TABLE ONLY public.exam
    ADD CONSTRAINT exam_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.grade
    ADD CONSTRAINT grade_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public."group"
    ADD CONSTRAINT group_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.student
    ADD CONSTRAINT student_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.subject
    ADD CONSTRAINT subject_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.subject_teacher
    ADD CONSTRAINT subject_teacher_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.teacher
    ADD CONSTRAINT teacher_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.exam
    ADD CONSTRAINT exam_group_id_fkey FOREIGN KEY (group_id) REFERENCES public."group"(id) NOT VALID;

ALTER TABLE ONLY public.exam
    ADD CONSTRAINT exam_subject_teacher_id_fkey FOREIGN KEY (subject_teacher_id) REFERENCES public.subject_teacher(id) NOT VALID;

ALTER TABLE ONLY public.grade
    ADD CONSTRAINT grade_exam_id_fkey FOREIGN KEY (exam_id) REFERENCES public.exam(id) NOT VALID;

ALTER TABLE ONLY public.grade
    ADD CONSTRAINT grade_student_id_fkey FOREIGN KEY (student_id) REFERENCES public.student(id) NOT VALID;

ALTER TABLE ONLY public."group"
    ADD CONSTRAINT group_teacher_id_fkey FOREIGN KEY (teacher_id) REFERENCES public.teacher(id) NOT VALID;

ALTER TABLE ONLY public.student
    ADD CONSTRAINT student_group_id_fkey FOREIGN KEY (group_id) REFERENCES public."group"(id) NOT VALID;

ALTER TABLE ONLY public.subject_teacher
    ADD CONSTRAINT subject_teacher_subject_id_fkey FOREIGN KEY (subject_id) REFERENCES public.subject(id) NOT VALID;

ALTER TABLE ONLY public.subject_teacher
    ADD CONSTRAINT subject_teacher_teacher_id_fkey FOREIGN KEY (teacher_id) REFERENCES public.teacher(id) NOT VALID;
