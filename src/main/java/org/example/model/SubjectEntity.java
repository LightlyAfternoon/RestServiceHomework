package org.example.model;

import java.util.List;

public class SubjectEntity {
    private int id;
    private String name;
    private List<TeacherEntity> teachers;
    private List<ExamEntity> exams;

    public SubjectEntity() {}

    public SubjectEntity(String name) {
        this.name = name;
    }

    public SubjectEntity(int id, String name) {
        this(name);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TeacherEntity> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<TeacherEntity> teachers) {
        this.teachers = teachers;
    }

    public List<ExamEntity> getExams() {
        return exams;
    }

    public void setExams(List<ExamEntity> exams) {
        this.exams = exams;
    }
}