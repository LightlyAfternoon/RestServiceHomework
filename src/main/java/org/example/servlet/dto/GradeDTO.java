package org.example.servlet.dto;

public class GradeDTO extends DTO {
    private int id;
    private int studentId;
    private int examId;
    private short mark;

    public GradeDTO() {}

    public GradeDTO(int id, int studentId, int examId, short mark) {
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

        result = 31 * result + studentId;
        result = 31 * result + examId;
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