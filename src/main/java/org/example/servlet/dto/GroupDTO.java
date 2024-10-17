package org.example.servlet.dto;

import org.example.servlet.dto.secondary.SecondaryExamDTO;
import org.example.servlet.dto.secondary.SecondaryStudentDTO;
import org.example.servlet.dto.secondary.SecondarySubjectDTO;

import java.sql.Date;
import java.util.List;

public class GroupDTO extends DTO {
    private int id;
    private String name;
    private Date startDate;
    private Date endDate;
    private TeacherDTO teacher;
    private List<SecondaryStudentDTO> students;
    private List<SecondaryExamDTO> exams;
    private List<SecondarySubjectDTO> subjects;

    public GroupDTO() {}

    public GroupDTO(int id, String name, Date startDate, Date endDate, TeacherDTO teacher) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.teacher = teacher;
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

    public TeacherDTO getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherDTO teacher) {
        this.teacher = teacher;
    }

    public List<SecondaryStudentDTO> getStudents() {
        return students;
    }

    public void setStudents(List<SecondaryStudentDTO> students) {
        this.students = students;
    }

    public List<SecondaryExamDTO> getExams() {
        return exams;
    }

    public void setExams(List<SecondaryExamDTO> exams) {
        this.exams = exams;
    }

    public List<SecondarySubjectDTO> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SecondarySubjectDTO> subjects) {
        this.subjects = subjects;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof GroupDTO)) return false;
        if (this == o) return true;

        GroupDTO g = (GroupDTO) o;

        return id == g.id
                && name.equals(g.name)
                && startDate.equals(g.startDate)
                && ((endDate == null && g.endDate == null) || (endDate != null && endDate.equals(g.endDate))
                && teacher == g.teacher);
    }

    @Override
    public int hashCode() {
        int result = id;

        result = 31 * result + name.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        result = 31 * result + teacher.hashCode();

        return result;
    }

    @Override
    public String toString() {
        if (endDate == null) {
            return "{\n" +
                    "    \"id\": " + id + ",\n" +
                    "    \"name\": \"" + name + "\",\n" +
                    "    \"startDate\": \"" + startDate + "\",\n" +
                    "    \"endDate\": null,\n" +
                    "    \"teacherId\": " + teacher + "\n" +
                    "}";
        }

        return "{\n" +
                "    \"id\": " + id + ",\n" +
                "    \"name\": \"" + name + "\",\n" +
                "    \"startDate\": \"" + startDate + "\",\n" +
                "    \"endDate\": \"" + endDate + "\",\n" +
                "    \"teacherId\": " + teacher + "\n" +
                "}";
    }
}