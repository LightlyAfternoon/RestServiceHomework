package org.example.controller;

import org.example.service.TeacherService;
import org.example.controller.dto.ExamDTO;
import org.example.controller.dto.GroupDTO;
import org.example.controller.dto.SubjectDTO;
import org.example.controller.dto.TeacherDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Set;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/")
    public Set<TeacherDTO> getTeachers() throws SQLException {
        return teacherService.findAll();
    }

    @GetMapping("/{id}")
    public TeacherDTO getTeacher(@PathVariable("id") int id) throws SQLException {
        return teacherService.findById(id);
    }

    @GetMapping("/{id}/group")
    public Set<GroupDTO> getGroups(@PathVariable("id") int id) throws SQLException {
        return teacherService.findAllGroupsWithTeacherId(id);
    }

    @GetMapping("/{id}/subject")
    public Set<SubjectDTO> getSubjects(@PathVariable("id") int id) throws SQLException {
        return teacherService.findAllSubjectsWithTeacherId(id);
    }

    @GetMapping("/{id}/exam")
    public Set<ExamDTO> getExams(@PathVariable("id") int id) throws SQLException {
        return teacherService.findAllExamsWithTeacherId(id);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public TeacherDTO createTeacher(@RequestBody TeacherDTO teacherDTO) throws SQLException {
        return teacherService.save(teacherDTO);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public TeacherDTO updateTeacher(@PathVariable("id") int id, @RequestBody TeacherDTO teacherDTO) throws SQLException {
        return teacherService.save(teacherDTO, id);
    }

    @DeleteMapping("/{id}")
    public String deleteTeacher(@PathVariable("id") int id) throws SQLException {
        TeacherDTO teacherDTO = teacherService.findById(id);

        if (teacherDTO != null && (teacherDTO.getGroups() != null || teacherDTO.getExams() != null)) {
            return "Teacher is connected to some groups and/or exams!";
        } else if (teacherDTO == null) {
            return "There is no teacher with id " + id;
        } else {
            teacherService.deleteById(id);

            if (teacherService.findById(id) == null) {
                return "Teacher deleted!";
            } else {
                return "Failed to delete teacher!";
            }
        }
    }
}