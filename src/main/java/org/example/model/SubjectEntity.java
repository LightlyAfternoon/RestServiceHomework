package org.example.model;

import java.util.List;

public class SubjectEntity {
    private int id;
    private String name;
    private List<TeacherEntity> teachers;

    public SubjectEntity() {}

    public SubjectEntity(String name) {
        this.name = name;
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
}