package org.example.servlet.dto;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.example.model.GroupEntity;
import org.example.model.SubjectEntity;
import org.example.model.TeacherEntity;

import java.sql.Date;

public class ExamDTO extends DTO {
    private int id;
    private Date startDate;
    private GroupEntity groupId;
//    private TeacherEntity subjectTeacherId;
    @ManyToOne
    @JoinColumn(name = "subject_id")
    private SubjectEntity subject;
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private TeacherEntity teacher;

    public ExamDTO() {}

    public ExamDTO(int id, Date startDate, GroupEntity groupId, SubjectEntity subject, TeacherEntity teacher) {
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

    public GroupEntity getGroupId() {
        return groupId;
    }

    public void setGroupId(GroupEntity groupId) {
        this.groupId = groupId;
    }

    public SubjectEntity getSubject() {
        return subject;
    }

    public void setSubject(SubjectEntity subject) {
        this.subject = subject;
    }

    public TeacherEntity getSubjectTeacherId() {
        return teacher;
    }

    public void setSubjectTeacherId(TeacherEntity teacher) {
        this.teacher = teacher;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof ExamDTO)) return false;
        if (this == o) return true;

        ExamDTO e = (ExamDTO) o;
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