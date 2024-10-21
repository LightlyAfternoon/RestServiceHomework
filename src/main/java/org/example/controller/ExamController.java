package org.example.controller;

import org.example.controller.dto.GradeDTO;
import org.example.controller.dto.ExamDTO;
import org.example.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Set;

@RestController
@RequestMapping("/exam")
public class ExamController {
    ExamService examService;

    @Autowired
    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @GetMapping("/")
    public Set<ExamDTO> getExams() throws SQLException {
        return examService.findAll();
    }

    @GetMapping("/{id}")
    public ExamDTO getExam(@PathVariable("id") int id) throws SQLException {
        return examService.findById(id);
    }

    @GetMapping("/{id}/grade")
    public Set<GradeDTO> getGrades(@PathVariable("id") int id) throws SQLException {
        return examService.findAllGradesWithExamId(id);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ExamDTO createExam(@RequestBody ExamDTO examDTO) throws SQLException {
        return examService.save(examDTO);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ExamDTO updateExam(@PathVariable("id") int id, @RequestBody ExamDTO examDTO) throws SQLException {
        return examService.save(examDTO, id);
    }

    @DeleteMapping("/{id}")
    public String deleteExam(@PathVariable("id") int id) throws SQLException {
        ExamDTO examDTO = examService.findById(id);

        if (examDTO != null && examDTO.getGrades() != null) {
            return "Exam is connected to some grades!";
        } else if (examDTO == null) {
            return "There is no exam with id " + id;
        } else {
            examService.deleteById(id);

            if (examService.findById(id) == null) {
                return "Exam deleted!";
            } else {
                return "Failed to delete exam!";
            }
        }
    }
}