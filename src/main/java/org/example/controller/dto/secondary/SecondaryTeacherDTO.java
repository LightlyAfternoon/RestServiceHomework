package org.example.controller.dto.secondary;


public class SecondaryTeacherDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String patronymic;

    public SecondaryTeacherDTO() {}

    public SecondaryTeacherDTO(int id, String firstName, String lastName, String patronymic) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
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

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof SecondaryTeacherDTO)) return false;
        if (this == o) return true;

        SecondaryTeacherDTO t = (SecondaryTeacherDTO) o;
        return id == t.id
                && firstName.equals(t.firstName)
                && lastName.equals(t.lastName)
                && ((patronymic == null && t.patronymic == null) || (patronymic != null && patronymic.equals(t.patronymic)));
    }

    @Override
    public int hashCode() {
        int result = id;

        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + (patronymic == null ? 0 : patronymic.hashCode());

        return result;
    }
}