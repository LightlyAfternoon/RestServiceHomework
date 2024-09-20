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
    public String toString() {
        return "{\n" +
                "    \"id\": " + id + ",\n" +
                "    \"studentId\": " + studentId + ",\n" +
                "    \"examId\": " + examId + ",\n" +
                "    \"mark\": " + mark + "\n" +
                "}";
    }
}