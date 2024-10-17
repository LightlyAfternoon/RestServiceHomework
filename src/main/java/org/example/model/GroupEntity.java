package org.example.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "\"group\"")
public class GroupEntity {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) int id;
    private String name;
    private @Column(name = "start_date") Date startDate;
    private @Column(name = "end_date") Date endDate;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id")
    private TeacherEntity teacher;
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<StudentEntity> students;
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ExamEntity> exams;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "subject_group", joinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"),
                                       inverseJoinColumns = @JoinColumn(name = "subject_id", referencedColumnName = "id"))
    private List<SubjectEntity> subjects;

    public GroupEntity() {}

    public GroupEntity(String name, Date startDate, Date endDate, TeacherEntity teacher) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.teacher = teacher;
    }

    public GroupEntity(int id, String name, Date startDate, Date endDate, TeacherEntity teacher) {
        this(name, startDate, endDate, teacher);
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public TeacherEntity getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherEntity teacher) {
        this.teacher = teacher;
    }

    public List<StudentEntity> getStudents() {
        return students;
    }

    public void setStudents(List<StudentEntity> students) {
        this.students = students;
    }

    public List<ExamEntity> getExams() {
        return exams;
    }

    public void setExams(List<ExamEntity> exams) {
        this.exams = exams;
    }

    public List<SubjectEntity> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectEntity> subjects) {
        this.subjects = subjects;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof GroupEntity)) return false;
        if (this == o) return true;

        GroupEntity g = (GroupEntity) o;

        return id == g.id
                && name.equals(g.name)
                && startDate.equals(g.startDate)
                && ((endDate == null && g.endDate == null) || (endDate != null && endDate.equals(g.endDate))
                && teacher == g.teacher);
    }

    @Override
    public int hashCode() {
        int result = id;

        result = 31 * result + name.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        result = 31 * result + teacher.hashCode();

        return result;
    }
}