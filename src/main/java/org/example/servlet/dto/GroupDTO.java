package org.example.servlet.dto;

import org.example.model.TeacherEntity;

import java.sql.Date;

public class GroupDTO extends DTO {
    private int id;
    private String name;
    private Date startDate;
    private Date endDate;
    private TeacherEntity teacherId;

    public GroupDTO() {}

    public GroupDTO(int id, String name, Date startDate, Date endDate, TeacherEntity teacherId) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.teacherId = teacherId;
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

    public TeacherEntity getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(TeacherEntity teacherId) {
        this.teacherId = teacherId;
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
                && teacherId == g.teacherId);
    }

    @Override
    public int hashCode() {
        int result = id;

        result = 31 * result + name.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        result = 31 * result + teacherId.hashCode();

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
                    "    \"teacherId\": " + teacherId + "\n" +
                    "}";
        }

        return "{\n" +
                "    \"id\": " + id + ",\n" +
                "    \"name\": \"" + name + "\",\n" +
                "    \"startDate\": \"" + startDate + "\",\n" +
                "    \"endDate\": \"" + endDate + "\",\n" +
                "    \"teacherId\": " + teacherId + "\n" +
                "}";
    }
}