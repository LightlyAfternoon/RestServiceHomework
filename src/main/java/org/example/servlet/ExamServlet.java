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
import org.example.model.ExamEntity;
import org.example.service.ExamService;
import org.example.service.impl.ExamServiceImpl;
import org.example.servlet.dto.ExamDTO;
import org.example.servlet.mapper.ExamDTOMapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/exam/*")
public class ExamServlet extends HttpServlet {
    final transient ExamService examService;

    static ExamDTOMapper examMapper = ExamDTOMapper.INSTANCE;

    public ExamServlet() {
        this.examService = new ExamServiceImpl();

        ConnectionManager.setConfig();
    }

    public ExamServlet(ExamService examService) {
        this.examService = examService;
    }
}