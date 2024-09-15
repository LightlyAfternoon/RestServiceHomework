package org.example.model;

import java.util.List;

public class TeacherEntity {
    private int id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private List<SubjectEntity> subjects;

    public TeacherEntity() {}

    public TeacherEntity(String firstName, String lastName, String patronymic) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
    }

    public TeacherEntity(int id, String firstName, String lastName, String patronymic) {
        this(firstName, lastName, patronymic);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public List<SubjectEntity> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectEntity> subjects) {
        this.subjects = subjects;
    }
}