package org.example.controller.dto;

import org.example.controller.dto.secondary.SecondaryExamDTO;
import org.example.controller.dto.secondary.SecondaryStudentDTO;

public class GradeDTO {
    private int id;
    private SecondaryStudentDTO student;
    private SecondaryExamDTO exam;
    private short mark;

    public GradeDTO() {}

    public GradeDTO(int id, SecondaryStudentDTO student, SecondaryExamDTO exam, short mark) {
        this.id = id;
        this.student = student;
        this.exam = exam;
        this.mark = mark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SecondaryStudentDTO getStudent() {
        return student;
    }

    public void setStudent(SecondaryStudentDTO student) {
        this.student = student;
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
                && student.equals(g.student)
                && exam.equals(g.exam)
                && mark == g.mark;
    }

    @Override
    public int hashCode() {
        int result = id;

        result = 31 * result + student.hashCode();
        result = 31 * result + exam.hashCode();
        result = 31 * result + mark;

        return result;
    }
}