package org.example.servlet.dto.secondary;

import java.sql.Date;

public class SecondaryGroupDTO {
    private int id;
    private String name;
    private Date startDate;
    private Date endDate;
    private SecondaryTeacherDTO teacher;

    public SecondaryGroupDTO() {}

    public SecondaryGroupDTO(int id, String name, Date startDate, Date endDate, SecondaryTeacherDTO teacher) {
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

    public SecondaryTeacherDTO getTeacher() {
        return teacher;
    }

    public void setTeacher(SecondaryTeacherDTO teacher) {
        this.teacher = teacher;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof SecondaryGroupDTO)) return false;
        if (this == o) return true;

        SecondaryGroupDTO g = (SecondaryGroupDTO) o;

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