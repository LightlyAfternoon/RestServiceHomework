package org.example.controller.dto;

import org.example.controller.dto.secondary.SecondaryExamDTO;
import org.example.controller.dto.secondary.SecondaryGroupDTO;
import org.example.controller.dto.secondary.SecondaryTeacherDTO;

import java.util.Set;

public class SubjectDTO {
    private int id;
    private String name;
    private Set<SecondaryTeacherDTO> teachers;
    private Set<SecondaryExamDTO> exams;
    private Set<SecondaryGroupDTO> groups;

    public SubjectDTO() {}

    public SubjectDTO(int id, String name) {
        this.id = id;
        this.name = name;
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

    public Set<SecondaryTeacherDTO> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<SecondaryTeacherDTO> teachers) {
        this.teachers = teachers;
    }

    public Set<SecondaryExamDTO> getExams() {
        return exams;
    }

    public void setExams(Set<SecondaryExamDTO> exams) {
        this.exams = exams;
    }

    public Set<SecondaryGroupDTO> getGroups() {
        return groups;
    }

    public void setGroups(Set<SecondaryGroupDTO> groups) {
        this.groups = groups;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof SubjectDTO)) return false;
        if (this == o) return true;

        SubjectDTO s = (SubjectDTO) o;

        return id == s.id
                && name.equals(s.name);
    }

    @Override
    public int hashCode() {
        int result = id;

        result = 31 * result + name.hashCode();

        return result;
    }
}