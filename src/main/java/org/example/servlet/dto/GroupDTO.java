package org.example.servlet.dto;

import org.example.servlet.dto.secondary.SecondaryExamDTO;
import org.example.servlet.dto.secondary.SecondaryStudentDTO;
import org.example.servlet.dto.secondary.SecondarySubjectDTO;

import java.sql.Date;
import java.util.Set;

public class GroupDTO {
    private int id;
    private String name;
    private Date startDate;
    private Date endDate;
    private TeacherDTO teacher;
    private Set<SecondaryStudentDTO> students;
    private Set<SecondaryExamDTO> exams;
    private Set<SecondarySubjectDTO> subjects;

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

    public Set<SecondaryStudentDTO> getStudents() {
        return students;
    }

    public void setStudents(Set<SecondaryStudentDTO> students) {
        this.students = students;
    }

    public Set<SecondaryExamDTO> getExams() {
        return exams;
    }

    public void setExams(Set<SecondaryExamDTO> exams) {
        this.exams = exams;
    }

    public Set<SecondarySubjectDTO> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<SecondarySubjectDTO> subjects) {
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
                && teacher.equals(g.teacher));
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
}