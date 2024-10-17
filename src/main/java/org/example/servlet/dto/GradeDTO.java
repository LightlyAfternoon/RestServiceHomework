package org.example.servlet.dto;

import org.example.servlet.dto.secondary.SecondaryExamDTO;
import org.example.servlet.dto.secondary.SecondaryStudentDTO;

public class GradeDTO extends DTO {
    private int id;
    private SecondaryStudentDTO studentId;
    private SecondaryExamDTO exam;
    private short mark;

    public GradeDTO() {}

    public GradeDTO(int id, SecondaryStudentDTO studentId, SecondaryExamDTO exam, short mark) {
        this.id = id;
        this.studentId = studentId;
        this.exam = exam;
        this.mark = mark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SecondaryStudentDTO getStudentId() {
        return studentId;
    }

    public void setStudentId(SecondaryStudentDTO studentId) {
        this.studentId = studentId;
    }

    public SecondaryExamDTO getExam() {
        return exam;
    }

    public void setExam(SecondaryExamDTO exam) {
        this.exam = exam;
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
                && exam == g.exam
                && mark == g.mark;
    }

    @Override
    public int hashCode() {
        int result = id;

        result = 31 * result + studentId.hashCode();
        result = 31 * result + exam.hashCode();
        result = 31 * result + mark;

        return result;
    }

    @Override
    public String toString() {
        return "{\n" +
                "    \"id\": " + id + ",\n" +
                "    \"studentId\": " + studentId + ",\n" +
                "    \"examId\": " + exam + ",\n" +
                "    \"mark\": " + mark + "\n" +
                "}";
    }
}