package org.example.model;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "exam")
public class ExamEntity {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) int id;
    private @Column(name = "start_date") Date startDate;
    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupEntity group;
//    @ManyToOne
//    @JoinColumn(name = "subject_teacher_id")
//    private TeacherEntity teacher;
    @ManyToOne
    @JoinColumn(name = "subject_id")
    private SubjectEntity subject;
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private TeacherEntity teacher;

    public ExamEntity() {}

    public ExamEntity(Date startDate, GroupEntity group, SubjectEntity subject, TeacherEntity teacher) {
        this.startDate = startDate;
        this.group = group;
        this.subject = subject;
        this.teacher = teacher;
    }

    public ExamEntity(int id, Date startDate, GroupEntity group, SubjectEntity subject, TeacherEntity teacher) {
        this(startDate, group, subject, teacher);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public GroupEntity getGroup() {
        return group;
    }

    public void setGroup(GroupEntity group) {
        this.group = group;
    }

    public SubjectEntity getSubject() {
        return subject;
    }

    public void setSubject(SubjectEntity subject) {
        this.subject = subject;
    }

    public TeacherEntity getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherEntity teacher) {
        this.teacher = teacher;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof ExamEntity)) return false;
        if (this == o) return true;

        ExamEntity e = (ExamEntity) o;
        return id == e.id
                && startDate.equals(e.startDate)
                && group == e.group
                && subject == e.subject
                && teacher == e.teacher;
    }

    @Override
    public int hashCode() {
        int result = id;

        result = 31 * result + startDate.hashCode();
        result = 31 * result + group.hashCode();
        result = 31 * result + subject.hashCode();
        result = 31 * result + teacher.hashCode();

        return result;
    }
}