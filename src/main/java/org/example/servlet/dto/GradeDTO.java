package org.example.servlet.dto;

import org.example.model.ExamEntity;
import org.example.model.StudentEntity;

public class GradeDTO extends DTO {
    private int id;
    private StudentEntity studentId;
    private ExamEntity examId;
    private short mark;

    public GradeDTO() {}

    public GradeDTO(int id, StudentEntity studentId, ExamEntity examId, short mark) {
        this.id = id;
        this.studentId = studentId;
        this.examId = examId;
        this.mark = mark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public StudentEntity getStudentId() {
        return studentId;
    }

    public void setStudentId(StudentEntity studentId) {
        this.studentId = studentId;
    }

    public ExamEntity getExamId() {
        return examId;
    }

    public void setExamId(ExamEntity examId) {
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
        if (!(o instanceof GradeDTO)) return false;
        if (this == o) return true;

        GradeDTO g = (GradeDTO) o;
        return id == g.id
                && studentId == g.studentId
                && examId == g.examId
                && mark == g.mark;
    }

    @Override
    public int hashCode() {
        int result = id;

        result = 31 * result + studentId.hashCode();
        result = 31 * result + examId.hashCode();
        result = 31 * result + mark;

        return result;
    }

    @Override
    public String toString() {
        return "{\n" +
                "    \"id\": " + id + ",\n" +
                "    \"studentId\": " + studentId + ",\n" +
                "    \"examId\": " + examId + ",\n" +
                "    \"mark\": " + mark + "\n" +
                "}";
    }
}