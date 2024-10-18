package org.example.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Set;

@Entity
@Table(name = "subject")
public class SubjectEntity {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) int id;
    private String name;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "subject_teacher", joinColumns = @JoinColumn(name = "subject_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id", referencedColumnName = "id"))
    @Fetch(FetchMode.SUBSELECT)
    private Set<TeacherEntity> teachers;
    @OneToMany(mappedBy = "subject", cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private Set<ExamEntity> exams;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "subject_group", joinColumns = @JoinColumn(name = "subject_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"))
    @Fetch(FetchMode.SUBSELECT)
    private Set<GroupEntity> groups;

    public SubjectEntity() {}

    public SubjectEntity(String name) {
        this.name = name;
    }

    public SubjectEntity(int id, String name) {
        this(name);
        this.id = id;
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

    public Set<TeacherEntity> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<TeacherEntity> teachers) {
        this.teachers = teachers;
    }

    public Set<ExamEntity> getExams() {
        return exams;
    }

    public void setExams(Set<ExamEntity> exams) {
        this.exams = exams;
    }

    public Set<GroupEntity> getGroups() {
        return groups;
    }

    public void setGroups(Set<GroupEntity> groups) {
        this.groups = groups;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof SubjectEntity)) return false;
        if (this == o) return true;

        SubjectEntity s = (SubjectEntity) o;

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