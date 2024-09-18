package org.example.model;

import java.sql.Date;

public class ExamEntity {
    private int id;
    private Date startDate;
    private int groupId;
    private int subjectTeacherId;

    public ExamEntity() {}

    public ExamEntity(Date startDate, int groupId, int subjectTeacherId) {
        this.startDate = startDate;
        this.groupId = groupId;
        this.subjectTeacherId = subjectTeacherId;
    }

    public ExamEntity(int id, Date startDate, int groupId, int subjectTeacherId) {
        this(startDate, groupId, subjectTeacherId);
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof ExamEntity)) return false;
        if (this == o) return true;

        ExamEntity e = (ExamEntity) o;
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
}