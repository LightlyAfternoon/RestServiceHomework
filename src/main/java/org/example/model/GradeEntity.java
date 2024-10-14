package org.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "grade")
public class GradeEntity {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) int id;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentEntity student;
    @ManyToOne
    @JoinColumn(name = "exam_id")
    private ExamEntity exam;
    private short mark;

    public GradeEntity() {}

    public GradeEntity(StudentEntity student, ExamEntity exam, short mark) {
        this.student = student;
        this.exam = exam;
        this.mark = mark;
    }

    public GradeEntity(int id, StudentEntity student, ExamEntity exam, short mark) {
        this(student, exam, mark);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public StudentEntity getStudent() {
        return student;
    }

    public void setStudent(StudentEntity student) {
        this.student = student;
    }

    public ExamEntity getExam() {
        return exam;
    }

    public void setExam(ExamEntity exam) {
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
        if (!(o instanceof GradeEntity)) return false;
        if (this == o) return true;

        GradeEntity g = (GradeEntity) o;
        return id == g.id
                && student == g.student
                && exam == g.exam
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