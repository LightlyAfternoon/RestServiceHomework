package org.example.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Set;

@Entity
@Table(name = "teacher")
public class TeacherEntity {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) int id;
    private @Column(name = "first_name") String firstName;
    private @Column(name = "last_name") String lastName;
    private @Column(name = "patronymic") String patronymic;
    @OneToMany(mappedBy = "teacher", cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private Set<GroupEntity> groups;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "subject_teacher", joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id", referencedColumnName = "id"))
    @Fetch(FetchMode.SUBSELECT)
    private Set<SubjectEntity> subjects;
    @OneToMany(mappedBy = "teacher", cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private Set<ExamEntity> exams;

    public TeacherEntity() {}

    public TeacherEntity(String firstName, String lastName, String patronymic) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
    }

    public TeacherEntity(int id, String firstName, String lastName, String patronymic) {
        this(firstName, lastName, patronymic);
        this.id = id;
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

    public Set<GroupEntity> getGroups() {
        return groups;
    }

    public void setGroups(Set<GroupEntity> groups) {
        this.groups = groups;
    }

    public Set<SubjectEntity> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<SubjectEntity> subjects) {
        this.subjects = subjects;
    }

    public Set<ExamEntity> getExams() {
        return exams;
    }

    public void setExams(Set<ExamEntity> exams) {
        this.exams = exams;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof TeacherEntity)) return false;
        if (this == o) return true;

        TeacherEntity t = (TeacherEntity) o;

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