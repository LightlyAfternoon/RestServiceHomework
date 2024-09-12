package org.example.model;

public class GradeEntity {
    private int id;
    private int studentId;
    private int examId;
    private int mark;

    public GradeEntity() {}

    public GradeEntity(int studentId, int examId, int mark) {
        this.studentId = studentId;
        this.examId = examId;
        this.mark = mark;
    }

    public int getId() {
        return id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }
}