package org.example.servlet.dto.secondary;

import org.example.servlet.dto.DTO;

import java.sql.Date;

public class SecondaryExamDTO extends DTO {
    private int id;
    private Date startDate;
    private SecondaryGroupDTO groupId;
    private SecondarySubjectDTO subject;
    private SecondaryTeacherDTO teacher;

    public SecondaryExamDTO() {}

    public SecondaryExamDTO(int id, Date startDate, SecondaryGroupDTO groupId, SecondarySubjectDTO subject, SecondaryTeacherDTO teacher) {
        this.id = id;
        this.startDate = startDate;
        this.groupId = groupId;
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

    public SecondaryGroupDTO getGroupId() {
        return groupId;
    }

    public void setGroupId(SecondaryGroupDTO groupId) {
        this.groupId = groupId;
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

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof SecondaryExamDTO)) return false;
        if (this == o) return true;

        SecondaryExamDTO e = (SecondaryExamDTO) o;
        return id == e.id
                && startDate.equals(e.startDate)
                && groupId == e.groupId
                && subject == e.subject
                && teacher == e.teacher;
    }

    @Override
    public int hashCode() {
        int result = id;

        result = 31 * result + startDate.hashCode();
        result = 31 * result + groupId.hashCode();
        result = 31 * result + subject.hashCode();
        result = 31 * result + teacher.hashCode();

        return result;
    }

    @Override
    public String toString() {
        return "{\n" +
                "    \"id\": " + id + ",\n" +
                "    \"startDate\": \"" + startDate + "\",\n" +
                "    \"groupId\": " + groupId + ",\n" +
                "    \"subjectId\": " + subject.getId() + ",\n" +
                "    \"teacherId\": " + teacher.getId() + "\n" +
                "}";
    }
}