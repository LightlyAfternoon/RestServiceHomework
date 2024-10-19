package org.example.controller;

import jakarta.servlet.annotation.WebServlet;
import org.example.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;

@WebServlet("/exam/*")
public class ExamServlet {
    ExamService examService;

    @Autowired
    public ExamServlet(ExamService examService) {
        this.examService = examService;
    }
}