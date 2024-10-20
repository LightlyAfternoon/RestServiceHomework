package org.example.controller;

import org.example.service.GroupService;
import org.example.service.SubjectService;
import org.example.service.TeacherService;
import org.example.controller.dto.ExamDTO;
import org.example.controller.dto.GroupDTO;
import org.example.controller.dto.SubjectDTO;
import org.example.controller.dto.TeacherDTO;
import org.example.controller.dto.secondary.SecondaryGroupDTO;
import org.example.controller.dto.secondary.SecondaryTeacherDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/subject")
public class SubjectController {
    private static final Logger log = LoggerFactory.getLogger(SubjectController.class);
    SubjectService subjectService;
    TeacherService teacherService;
    GroupService groupService;

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
        SubjectDTO subjectDTO = subjectService.findById(id);
        Set<SecondaryTeacherDTO> secondaryTeacherDTOS = new HashSet<>();
        if (subjectDTO.getTeachers() != null) {
            secondaryTeacherDTOS.addAll(subjectDTO.getTeachers());
        }

        TeacherDTO teacherDTO = teacherService.findById(teacherId);
        SecondaryTeacherDTO secondaryTeacherDTO = new SecondaryTeacherDTO(teacherId, teacherDTO.getFirstName(), teacherDTO.getLastName(), teacherDTO.getPatronymic());

        if (secondaryTeacherDTOS.isEmpty() || !secondaryTeacherDTOS.contains(secondaryTeacherDTO)) {
            secondaryTeacherDTOS.add(secondaryTeacherDTO);
            subjectDTO.setTeachers(secondaryTeacherDTOS);
            subjectDTO.getGroups().forEach(t -> log.info(String.valueOf(t.getId())));

            return subjectService.save(subjectDTO, id);
        } else {
            return subjectDTO;
        }
    }

    @PostMapping(value = "/{id}/group/{groupId}")
    public SubjectDTO addGroup(@PathVariable("id") int id, @PathVariable("groupId") int groupId) throws SQLException {
        SubjectDTO subjectDTO = subjectService.findById(id);
        Set<SecondaryGroupDTO> secondaryGroupDTOS = new HashSet<>();
        if (subjectDTO.getTeachers() != null) {
            secondaryGroupDTOS.addAll(subjectDTO.getGroups());
        }

        GroupDTO groupDTO = groupService.findById(groupId);
        TeacherDTO teacherDTO = teacherService.findById(groupDTO.getTeacher().getId());
        SecondaryTeacherDTO secondaryTeacherDTO = new SecondaryTeacherDTO(teacherDTO.getId(), teacherDTO.getFirstName(), teacherDTO.getLastName(), teacherDTO.getPatronymic());
        SecondaryGroupDTO secondaryGroupDTO = new SecondaryGroupDTO(groupId, groupDTO.getName(), groupDTO.getStartDate(), groupDTO.getEndDate(), secondaryTeacherDTO);

        if (secondaryGroupDTOS.isEmpty() || !secondaryGroupDTOS.contains(secondaryGroupDTO)) {
            secondaryGroupDTOS.add(secondaryGroupDTO);
            subjectDTO.setGroups(secondaryGroupDTOS);

            return subjectService.save(subjectDTO, id);
        } else {
            return subjectDTO;
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