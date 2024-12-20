package org.example.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Set;

@Entity
@Table(name = "student")
public class StudentEntity {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) int id;
    private @Column(name = "first_name") String firstName;
    private @Column(name = "last_name") String lastName;
    private @Column(name = "patronymic") String patronymic;
    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupEntity group;
    @OneToMany(mappedBy = "student", cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private Set<GradeEntity> grades;

    public StudentEntity() {}

    public StudentEntity(String firstName, String lastName, String patronymic, GroupEntity group) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.group = group;
    }

    public StudentEntity(int id, String firstName, String lastName, String patronymic, GroupEntity group) {
        this(firstName, lastName, patronymic, group);
        this.id = id;
    }

    @PreRemove
    public void preRemove() {
        if (grades == null || grades.isEmpty()) {
            this.group.getStudents().remove(this);
            this.group = null;
        }
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

    public Set<GradeEntity> getGrades() {
        return grades;
    }

    public void setGrades(Set<GradeEntity> grades) {
        this.grades = grades;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof StudentEntity)) return false;
        if (this == o) return true;

        StudentEntity s = (StudentEntity) o;

        return id == s.id
                && firstName.equals(s.firstName)
                && lastName.equals(s.lastName)
                && ((patronymic == null && s.patronymic == null) || (patronymic != null && patronymic.equals(s.patronymic))
                && group.equals(s.group));
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
}