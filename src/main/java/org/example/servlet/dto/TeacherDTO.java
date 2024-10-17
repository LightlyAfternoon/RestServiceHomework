package org.example.servlet.dto;

import org.example.servlet.dto.secondary.SecondaryExamDTO;
import org.example.servlet.dto.secondary.SecondaryGroupDTO;
import org.example.servlet.dto.secondary.SecondarySubjectDTO;

import java.util.List;

public class TeacherDTO extends DTO {
    private int id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private List<SecondaryGroupDTO> groups;
    private List<SecondarySubjectDTO> subjects;
    private List<SecondaryExamDTO> exams;

    public TeacherDTO() {}

    public TeacherDTO(int id, String firstName, String lastName, String patronymic) {
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

    public List<SecondaryGroupDTO> getGroups() {
        return groups;
    }

    public void setGroups(List<SecondaryGroupDTO> groups) {
        this.groups = groups;
    }

    public List<SecondarySubjectDTO> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SecondarySubjectDTO> subjects) {
        this.subjects = subjects;
    }

    public List<SecondaryExamDTO> getExams() {
        return exams;
    }

    public void setExams(List<SecondaryExamDTO> exams) {
        this.exams = exams;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof TeacherDTO)) return false;
        if (this == o) return true;

        TeacherDTO t = (TeacherDTO) o;
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

    @Override
    public String toString() {
        if (patronymic == null) {
            return "{\n" +
                    "    \"id\": "+id+",\n" +
                    "    \"firstName\": \""+firstName+"\",\n" +
                    "    \"lastName\": \""+lastName+"\",\n" +
                    "    \"patronymic\": null\n" +
                    "}";

        }

        return "{\n" +
                "    \"id\": "+id+",\n" +
                "    \"firstName\": \""+firstName+"\",\n" +
                "    \"lastName\": \""+lastName+"\",\n" +
                "    \"patronymic\": \""+patronymic+"\"\n" +
                "}";
    }
}