package org.example.controller.dto;

import org.example.controller.dto.secondary.SecondaryGroupDTO;
import org.example.controller.dto.secondary.SecondarySubjectDTO;
import org.example.controller.dto.secondary.SecondaryTeacherDTO;

import java.sql.Date;
import java.util.Set;

public class ExamDTO {
    private int id;
    private Date startDate;
    private SecondaryGroupDTO group;
    private SecondarySubjectDTO subject;
    private SecondaryTeacherDTO teacher;
    private Set<GradeDTO> grades;

    public ExamDTO() {}

    public ExamDTO(int id, Date startDate, SecondaryGroupDTO group, SecondarySubjectDTO subject, SecondaryTeacherDTO teacher) {
        this.id = id;
        this.startDate = startDate;
        this.group = group;
        this.subject = subject;
        this.teacher = teacher;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public SecondaryGroupDTO getGroup() {
        return group;
    }

    public void setGroup(SecondaryGroupDTO group) {
        this.group = group;
    }

    public SecondarySubjectDTO getSubject() {
        return subject;
    }

    public void setSubject(SecondarySubjectDTO subject) {
        this.subject = subject;
    }

    public SecondaryTeacherDTO getTeacher() {
        return teacher;
    }

    public void setTeacher(SecondaryTeacherDTO teacher) {
        this.teacher = teacher;
    }

    public Set<GradeDTO> getGrades() {
        return grades;
    }

    public void setGrades(Set<GradeDTO> grades) {
        this.grades = grades;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof ExamDTO)) return false;
        if (this == o) return true;

        ExamDTO e = (ExamDTO) o;

        return id == e.id
                && startDate.equals(e.startDate)
                && group.equals(e.group)
                && subject.equals(e.subject)
                && teacher.equals(e.teacher);
    }

    @Override
    public int hashCode() {
        int result = id;

        result = 31 * result + startDate.hashCode();
        result = 31 * result + group.hashCode();
        result = 31 * result + subject.hashCode();
        result = 31 * result + teacher.hashCode();

        return result;
    }
}