package org.example.servlet.dto;

import org.example.model.GroupEntity;

public class StudentDTO extends DTO {
    private int id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private GroupEntity group;

    public StudentDTO() {}

    public StudentDTO(int id, String firstName, String lastName, String patronymic, GroupEntity group) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.group = group;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public GroupEntity getGroup() {
        return group;
    }

    public void setGroup(GroupEntity group) {
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof StudentDTO)) return false;
        if (this == o) return true;

        StudentDTO s = (StudentDTO) o;
        return id == s.id
                && firstName.equals(s.firstName)
                && lastName.equals(s.lastName)
                && ((patronymic == null && s.patronymic == null) || (patronymic != null && patronymic.equals(s.patronymic))
                && group == s.group);
    }

    @Override
    public int hashCode() {
        int result = id;

        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + (patronymic == null ? 0 : patronymic.hashCode());
        result = 31 * result + group.hashCode();

        return result;
    }

    @Override
    public String toString() {
        if (patronymic == null) {
            return "{\n" +
                    "    \"id\": "+id+",\n" +
                    "    \"firstName\": \""+firstName+"\",\n" +
                    "    \"lastName\": \""+lastName+"\",\n" +
                    "    \"patronymic\": null,\n" +
                    "    \"groupId\": "+ group +"\n" +
                    "}";
        }

        return "{\n" +
                "    \"id\": "+id+",\n" +
                "    \"firstName\": \""+firstName+"\",\n" +
                "    \"lastName\": \""+lastName+"\",\n" +
                "    \"patronymic\": \""+patronymic+"\",\n" +
                "    \"groupId\": "+ group +"\n" +
                "}";
    }
}