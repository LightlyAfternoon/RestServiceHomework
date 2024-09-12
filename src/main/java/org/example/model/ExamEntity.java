package org.example.model;

import java.util.Date;

public class ExamEntity {
    private int id;
    private Date startDate;
    private int groupId;
    private int subjectTeacherId;

    ExamEntity() {}

    ExamEntity(Date startDate, int groupId, int subjectTeacherId) {
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