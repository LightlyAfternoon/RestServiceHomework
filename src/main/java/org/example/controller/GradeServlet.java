package org.example.controller;

import jakarta.servlet.annotation.WebServlet;
import org.example.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;

@WebServlet("/grade/*")
public class GradeServlet {
    GradeService gradeService;

    @Autowired
    public GradeServlet(GradeService gradeService) {
        this.gradeService = gradeService;
    }
}