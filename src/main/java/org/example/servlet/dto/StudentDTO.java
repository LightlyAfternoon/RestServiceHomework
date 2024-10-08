package org.example.servlet.dto;

public class StudentDTO extends DTO {
    private int id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private int groupId;

    public StudentDTO() {}

    public StudentDTO(int id, String firstName, String lastName, String patronymic, int groupId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.groupId = groupId;
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

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        if (patronymic == null) {
            return "{\n" +
                    "    \"id\": "+id+",\n" +
                    "    \"firstName\": \""+firstName+"\",\n" +
                    "    \"lastName\": \""+lastName+"\",\n" +
                    "    \"patronymic\": null,\n" +
                    "    \"groupId\": "+groupId+"\n" +
                    "}";
        }

        return "{\n" +
                "    \"id\": "+id+",\n" +
                "    \"firstName\": \""+firstName+"\",\n" +
                "    \"lastName\": \""+lastName+"\",\n" +
                "    \"patronymic\": \""+patronymic+"\",\n" +
                "    \"groupId\": "+groupId+"\n" +
                "}";
    }
}