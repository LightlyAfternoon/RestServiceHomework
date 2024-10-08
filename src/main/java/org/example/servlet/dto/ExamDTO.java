package org.example.servlet.dto;

import java.sql.Date;

public class ExamDTO extends DTO {
    private int id;
    private Date startDate;
    private int groupId;
    private int subjectTeacherId;

    public ExamDTO() {}

    public ExamDTO(int id, Date startDate, int groupId, int subjectTeacherId) {
        this.id = id;
        this.startDate = startDate;
        this.groupId = groupId;
        this.subjectTeacherId = subjectTeacherId;
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

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getSubjectTeacherId() {
        return subjectTeacherId;
    }

    public void setSubjectTeacherId(int subjectTeacherId) {
        this.subjectTeacherId = subjectTeacherId;
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
                && subjectTeacherId == e.subjectTeacherId;
    }

    @Override
    public int hashCode() {
        int result = id;

        result = 31 * result + startDate.hashCode();
        result = 31 * result + groupId;
        result = 31 * result + subjectTeacherId;

        return result;
    }

    @Override
    public String toString() {
        return "{\n" +
                "    \"id\": " + id + ",\n" +
                "    \"startDate\": \"" + startDate + "\",\n" +
                "    \"groupId\": " + groupId + ",\n" +
                "    \"subjectTeacherId\": " + subjectTeacherId + "\n" +
                "}";
    }
}