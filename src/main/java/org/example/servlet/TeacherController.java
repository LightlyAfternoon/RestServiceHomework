package org.example.servlet;

import org.example.service.TeacherService;
import org.example.servlet.dto.ExamDTO;
import org.example.servlet.dto.GroupDTO;
import org.example.servlet.dto.SubjectDTO;
import org.example.servlet.dto.TeacherDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/")
    public List<TeacherDTO> getTeacher() throws SQLException {
        return teacherService.findAll();
    }

    @GetMapping("/{id}")
    public TeacherDTO getTeacher(@PathVariable("id") int id) throws SQLException {
        return teacherService.findById(id);
    }

    @GetMapping("/{id}/group")
    public List<GroupDTO> getGroups(@PathVariable("id") int id) throws SQLException {
        return teacherService.findAllGroupsWithTeacherId(id);
    }

    @GetMapping("/{id}/subject")
    public List<SubjectDTO> getSubjects(@PathVariable("id") int id) throws SQLException {
        return teacherService.findAllSubjectsWithTeacherId(id);
    }

    @GetMapping("/{id}/exam")
    public List<ExamDTO> getExams(@PathVariable("id") int id) throws SQLException {
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
        teacherService.deleteById(id);

        if (teacherDTO != null) {
            return "Teacher deleted!";
        } else {
            return "There is no teacher with id " + id;
        }
    }
}