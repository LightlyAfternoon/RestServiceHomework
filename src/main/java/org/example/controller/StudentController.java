package org.example.controller;

import org.example.service.StudentService;
import org.example.controller.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Set;

@RestController
@RequestMapping("/student")
public class StudentController {
    StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/")
    public Set<StudentDTO> getStudents() throws SQLException {
        return studentService.findAll();
    }

    @GetMapping("/{id}")
    public StudentDTO getStudent(@PathVariable("id") int id) throws SQLException {
        return studentService.findById(id);
    }

    @GetMapping("/{id}/grade")
    public Set<GradeDTO> getGrades(@PathVariable("id") int id) throws SQLException {
        return studentService.findAllGradesWithServiceId(id);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StudentDTO createStudent(@RequestBody StudentDTO studentDTO) throws SQLException {
        return studentService.save(studentDTO);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StudentDTO updateStudent(@PathVariable("id") int id, @RequestBody StudentDTO studentDTO) throws SQLException {
        return studentService.save(studentDTO, id);
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable("id") int id) throws SQLException {
        StudentDTO studentDTO = studentService.findById(id);

        if (studentDTO != null && studentDTO.getGrades() != null) {
            return "Student is connected to some grades!";
        } else if (studentDTO == null) {
            return "There is no student with id " + id;
        } else {
            studentService.deleteById(id);

            if (studentService.findById(id) == null) {
                return "Student deleted!";
            } else {
                return "Failed to delete student!";
            }
        }
    }
}