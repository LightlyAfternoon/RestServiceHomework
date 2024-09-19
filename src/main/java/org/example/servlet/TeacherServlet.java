package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.TeacherService;
import org.example.service.impl.TeacherServiceImpl;
import org.example.servlet.dto.TeacherDTO;
import org.example.servlet.mapper.TeacherDTOMapperImpl;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/teacher/*")
public class TeacherServlet extends HttpServlet {
    TeacherService teacherService;
    TeacherDTO teacherDTO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        teacherService = new TeacherServiceImpl();
        String info = "";

        if (req.getPathInfo() != null) {
            info = req.getPathInfo().substring(1);
            try {
                teacherDTO = new TeacherDTOMapperImpl().mapToDTO(teacherService.findById(Integer.parseInt(info)));
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        }

        resp.setContentType("text/html; charset=UTF-8");
        try (PrintWriter printWriter = resp.getWriter()) {
            if (!info.isBlank()) {
                printWriter.write(String.format("Teacher %d %s %s %s", teacherDTO.getId(), teacherDTO.getFirstName(), teacherDTO.getLastName(), teacherDTO.getPatronymic()));
            } else {
                List<TeacherDTO> teachers = teacherService.findAll().stream().map(t -> new TeacherDTOMapperImpl().mapToDTO(t)).toList();
                for (TeacherDTO teacher : teachers) {
                    printWriter.write(String.format("Teacher %d %s %s %s %n", teacher.getId(), teacher.getFirstName(), teacher.getLastName(), teacher.getPatronymic()));
                }
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}