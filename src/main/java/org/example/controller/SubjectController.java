package org.example.controller;

import org.example.controller.mapper.GroupDTOMapper;
import org.example.controller.mapper.SubjectDTOMapper;
import org.example.controller.mapper.TeacherDTOMapper;
import org.example.model.GroupEntity;
import org.example.model.SubjectEntity;
import org.example.model.TeacherEntity;
import org.example.service.GroupService;
import org.example.service.SubjectService;
import org.example.service.TeacherService;
import org.example.controller.dto.ExamDTO;
import org.example.controller.dto.GroupDTO;
import org.example.controller.dto.SubjectDTO;
import org.example.controller.dto.TeacherDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/subject")
public class SubjectController {
    SubjectService subjectService;
    TeacherService teacherService;
    GroupService groupService;

    SubjectDTOMapper subjectDTOMapper = SubjectDTOMapper.INSTANCE;
    TeacherDTOMapper teacherDTOMapper = TeacherDTOMapper.INSTANCE;
    GroupDTOMapper groupDTOMapper = GroupDTOMapper.INSTANCE;

    @Autowired
    public SubjectController(SubjectService subjectService, TeacherService teacherService, GroupService groupService) {
        this.subjectService = subjectService;
        this.teacherService = teacherService;
        this.groupService = groupService;
    }

    @GetMapping("/")
    public Set<SubjectDTO> getSubjects() throws SQLException {
        return subjectService.findAll();
    }

    @GetMapping("/{id}")
    public SubjectDTO getSubject(@PathVariable("id") int id) throws SQLException {
        return subjectService.findById(id);
    }

    @GetMapping("/{id}/teacher")
    public Set<TeacherDTO> getTeachers(@PathVariable("id") int id) throws SQLException {
        return subjectService.findAllTeachersWithSubjectId(id);
    }

    @GetMapping("/{id}/exam")
    public Set<ExamDTO> getExams(@PathVariable("id") int id) throws SQLException {
        return subjectService.findAllExamsWithSubjectId(id);
    }

    @GetMapping("/{id}/group")
    public Set<GroupDTO> getGroups(@PathVariable("id") int id) throws SQLException {
        return subjectService.findAllGroupsWithSubjectId(id);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public SubjectDTO createSubject(@RequestBody SubjectDTO subjectDTO) throws SQLException {
        return subjectService.save(subjectDTO);
    }

    @PostMapping(value = "/{id}/teacher/{teacherId}")
    public SubjectDTO addTeacher(@PathVariable("id") int id, @PathVariable("teacherId") int teacherId) throws SQLException {
        SubjectEntity subject = subjectDTOMapper.mapToEntity(subjectService.findById(id));
        Set<TeacherEntity> teachers = new HashSet<>();
        if (subject.getTeachers() != null) {
            teachers.addAll(subject.getTeachers());
        }

        TeacherEntity teacher = teacherDTOMapper.mapToEntity(teacherService.findById(teacherId), teacherId);

        if (teachers.isEmpty() || !teachers.contains(teacher)) {
            teachers.add(teacher);
            subject.setTeachers(teachers);

            return subjectService.save(subjectDTOMapper.mapToDTO(subject), id);
        } else {
            return subjectDTOMapper.mapToDTO(subject);
        }
    }

    @PostMapping(value = "/{id}/group/{groupId}")
    public SubjectDTO addGroup(@PathVariable("id") int id, @PathVariable("groupId") int groupId) throws SQLException {
        SubjectEntity subject = subjectDTOMapper.mapToEntity(subjectService.findById(id), id);
        Set<GroupEntity> groups = new HashSet<>();
        if (subject.getTeachers() != null) {
            groups.addAll(subject.getGroups());
        }

        GroupEntity group = groupDTOMapper.mapToEntity(groupService.findById(groupId), groupId);

        if (groups.isEmpty() || !groups.contains(group)) {
            groups.add(group);
            subject.setGroups(groups);

            return subjectService.save(subjectDTOMapper.mapToDTO(subject), id);
        } else {
            return subjectDTOMapper.mapToDTO(subject);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public SubjectDTO updateSubject(@PathVariable("id") int id, @RequestBody SubjectDTO subjectDTO) throws SQLException {
        return subjectService.save(subjectDTO, id);
    }

    @DeleteMapping("/{id}")
    public String deleteSubject(@PathVariable("id") int id) throws SQLException {
        SubjectDTO subjectDTO = subjectService.findById(id);

        if (subjectDTO != null && (subjectDTO.getGroups() != null || subjectDTO.getExams() != null)) {
            return "Subject is connected to some groups and/or exams!";
        } else if (subjectDTO == null) {
            return "There is no subject with id " + id;
        } else {
            subjectService.deleteById(id);

            if (subjectService.findById(id) == null) {
                return "Subject deleted!";
            } else {
                return "Failed to delete subject!";
            }
        }
    }
}