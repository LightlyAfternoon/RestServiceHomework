package org.example.model;

import java.util.List;

public class SubjectEntity extends Entity {
    private int id;
    private String name;
    private List<TeacherEntity> teachers;
    private List<ExamEntity> exams;
    private List<GroupEntity> groups;

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

    public void setId(int id) {
        this.id = id;
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

    public List<GroupEntity> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupEntity> groups) {
        this.groups = groups;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof SubjectEntity)) return false;
        if (this == o) return true;

        SubjectEntity s = (SubjectEntity) o;

        return id == s.id
                && name.equals(s.name);
    }

    @Override
    public int hashCode() {
        int result = id;

        result = 31 * result + name.hashCode();

        return result;
    }
}