package org.example.model;

public class GradeEntity extends Entity {
    private int id;
    private int studentId;
    private int examId;
    private short mark;

    public GradeEntity() {}

    public GradeEntity(int studentId, int examId, short mark) {
        this.studentId = studentId;
        this.examId = examId;
        this.mark = mark;
    }

    public GradeEntity(int id, int studentId, int examId, short mark) {
        this(studentId, examId, mark);
        this.id = id;
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

    public short getMark() {
        return mark;
    }

    public void setMark(short mark) {
        this.mark = mark;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof GradeEntity)) return false;
        if (this == o) return true;

        GradeEntity g = (GradeEntity) o;
        return id == g.id
                && studentId == g.studentId
                && examId == g.examId
                && mark == g.mark;
    }

    @Override
    public int hashCode() {
        int result = id;

        result = 31 * result + studentId;
        result = 31 * result + examId;
        result = 31 * result + mark;

        return result;
    }
}