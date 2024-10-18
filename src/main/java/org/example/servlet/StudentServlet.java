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
import org.example.model.StudentEntity;
import org.example.service.StudentService;
import org.example.service.impl.StudentServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/student/*")
public class StudentServlet extends HttpServlet {
    final transient StudentService studentService;

    public StudentServlet() {
        this.studentService = new StudentServiceImpl();

        ConnectionManager.setConfig();
    }
}