package org.example.servlet.dto;

import org.example.servlet.dto.secondary.SecondaryExamDTO;
import org.example.servlet.dto.secondary.SecondaryGroupDTO;
import org.example.servlet.dto.secondary.SecondarySubjectDTO;

import java.util.Set;

public class TeacherDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private Set<SecondaryGroupDTO> groups;
    private Set<SecondarySubjectDTO> subjects;
    private Set<SecondaryExamDTO> exams;

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

    public Set<SecondaryGroupDTO> getGroups() {
        return groups;
    }

    public void setGroups(Set<SecondaryGroupDTO> groups) {
        this.groups = groups;
    }

    public Set<SecondarySubjectDTO> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<SecondarySubjectDTO> subjects) {
        this.subjects = subjects;
    }

    public Set<SecondaryExamDTO> getExams() {
        return exams;
    }

    public void setExams(Set<SecondaryExamDTO> exams) {
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
}