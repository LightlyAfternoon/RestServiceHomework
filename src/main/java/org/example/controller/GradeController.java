package org.example.controller;

import org.example.controller.dto.GradeDTO;
import org.example.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Set;

@RestController
@RequestMapping("/grade")
public class GradeController {
    GradeService gradeService;

    @Autowired
    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @GetMapping("/")
    public Set<GradeDTO> getGrades() throws SQLException {
        return gradeService.findAll();
    }

    @GetMapping("/{id}")
    public GradeDTO getGrade(@PathVariable("id") int id) throws SQLException {
        return gradeService.findById(id);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public GradeDTO createGrade(@RequestBody GradeDTO gradeDTO) throws SQLException {
        return gradeService.save(gradeDTO);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public GradeDTO updateGrade(@PathVariable("id") int id, @RequestBody GradeDTO gradeDTO) throws SQLException {
        return gradeService.save(gradeDTO, id);
    }

    @DeleteMapping("/{id}")
    public String deleteGrade(@PathVariable("id") int id) throws SQLException {
        GradeDTO gradeDTO = gradeService.findById(id);

        if (gradeDTO == null) {
            return "There is no grade with id " + id;
        } else {
            gradeService.deleteById(id);

            if (gradeService.findById(id) == null) {
                return "Grade deleted!";
            } else {
                return "Failed to delete grade!";
            }
        }
    }
}