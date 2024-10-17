package org.example.servlet.dto;

import org.example.servlet.dto.secondary.SecondaryExamDTO;
import org.example.servlet.dto.secondary.SecondaryGroupDTO;
import org.example.servlet.dto.secondary.SecondaryTeacherDTO;

import java.util.List;

public class SubjectDTO extends DTO {
    private int id;
    private String name;
    private List<SecondaryTeacherDTO> teachers;
    private List<SecondaryExamDTO> exams;
    private List<SecondaryGroupDTO> groups;

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

    public List<SecondaryTeacherDTO> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<SecondaryTeacherDTO> teachers) {
        this.teachers = teachers;
    }

    public List<SecondaryExamDTO> getExams() {
        return exams;
    }

    public void setExams(List<SecondaryExamDTO> exams) {
        this.exams = exams;
    }

    public List<SecondaryGroupDTO> getGroups() {
        return groups;
    }

    public void setGroups(List<SecondaryGroupDTO> groups) {
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

    @Override
    public String toString() {
        return "{\n" +
                "    \"id\": "+id+",\n" +
                "    \"name\": \""+name+"\"\n" +
                "}";
    }
}