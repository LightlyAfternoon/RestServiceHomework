package org.example.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.db.ConnectionManager;
import org.example.model.GradeEntity;
import org.example.service.GradeService;
import org.example.service.impl.GradeServiceImpl;
import org.example.servlet.dto.GradeDTO;
import org.example.servlet.mapper.GradeDTOMapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/grade/*")
public class GradeServlet extends HttpServlet {
    final transient GradeService gradeService;

    public GradeServlet() {
        this.gradeService = new GradeServiceImpl();

        ConnectionManager.setConfig();
    }
}