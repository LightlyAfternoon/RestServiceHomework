package org.example.model;

public class StudentEntity {
    private int id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private int groupId;

    public StudentEntity() {}

    public StudentEntity(String firstName, String lastName, String patronymic, int groupId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.groupId = groupId;
    }

    public StudentEntity(int id, String firstName, String lastName, String patronymic, int groupId) {
        this(firstName, lastName, patronymic, groupId);
        this.id = id;
    }

    public int getId() {
        return id;
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
}