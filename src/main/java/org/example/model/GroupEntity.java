package org.example.model;

import java.sql.Date;
import java.util.List;

public class GroupEntity {
    private int id;
    private String name;
    private Date startDate;
    private Date endDate;
    private int teacherId;
    private List<StudentEntity> students;
    private List<ExamEntity> exams;

    public GroupEntity() {}

    public GroupEntity(String name, Date startDate, Date endDate, int teacherId) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.teacherId = teacherId;
    }

    public GroupEntity(int id, String name, Date startDate, Date endDate, int teacherId) {
        this(name, startDate, endDate, teacherId);
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public List<StudentEntity> getStudents() {
        return students;
    }

    public void setStudents(List<StudentEntity> students) {
        this.students = students;
    }

    public List<ExamEntity> getExams() {
        return exams;
    }

    public void setExams(List<ExamEntity> exams) {
        this.exams = exams;
    }
}