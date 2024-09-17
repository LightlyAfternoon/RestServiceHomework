package org.example.servlet.dto;

import java.sql.Date;

public class ExamDTO {
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
}