package org.example.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.db.ConnectionManager;
import org.example.model.TeacherEntity;
import org.example.service.TeacherService;
import org.example.service.impl.TeacherServiceImpl;
import org.example.servlet.dto.TeacherDTO;
import org.example.servlet.mapper.TeacherDTOMapperImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/teacher/*")
public class TeacherServlet extends HttpServlet {
    static final String CONTENT_TYPE = "application/json; charset=UTF-8";
    TeacherService teacherService;
    TeacherDTO teacherDTO;

    public TeacherServlet() {
        this.teacherService = new TeacherServiceImpl("C:\\Users\\Vika\\IdeaProjects\\Homeworks\\RestServiceHomework\\src\\main\\java\\org\\example\\db\\DbParameters");
    }

    public TeacherServlet(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    private void setSettings(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType(CONTENT_TYPE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setSettings(req, resp);

        String info = "";

        if (req.getPathInfo() != null && !req.getPathInfo().substring(1).isBlank()) {
            info = req.getPathInfo().substring(1);
            try {
                teacherDTO = new TeacherDTOMapperImpl().mapToDTO(teacherService.findById(Integer.parseInt(info)));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        try (PrintWriter printWriter = resp.getWriter()) {
            if (!info.isBlank() && teacherDTO != null) {
                printWriter.write(teacherDTO.toString());
            } else if (!info.isBlank()) {
                printWriter.write("Teacher is not found");
            } else {
                List<TeacherDTO> teachers = teacherService.findAll().stream().map(t -> new TeacherDTOMapperImpl().mapToDTO(t)).toList();
                for (TeacherDTO teacher : teachers) {
                    printWriter.write(teacher.toString());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setSettings(req, resp);

        TeacherDTOMapperImpl mapper = new TeacherDTOMapperImpl();

        String info = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        JsonObject json = JsonParser.parseString(info).getAsJsonObject();
        Gson gson = new Gson().fromJson(json, Gson.class);

        teacherDTO = gson.fromJson(json, TeacherDTO.class);

        TeacherEntity teacher = mapper.mapToEntity(teacherDTO);
        try {
            teacher = teacherService.save(teacher);
            teacherDTO = mapper.mapToDTO(teacher);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (PrintWriter printWriter = resp.getWriter()) {
            printWriter.write(teacherDTO.toString());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setSettings(req, resp);

        String info = "";

        if (req.getPathInfo() != null && !req.getPathInfo().substring(1).isBlank()) {
            info = req.getPathInfo().substring(1);
            try {
                teacherDTO = new TeacherDTOMapperImpl().mapToDTO(teacherService.findById(Integer.parseInt(info)));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        try (PrintWriter printWriter = resp.getWriter()) {
            if (!info.isBlank() && teacherDTO != null) {
                printWriter.write("{\"success\":\""+teacherService.deleteById(Integer.parseInt(info))+"\"}");
            } else if (!info.isBlank()) {
                printWriter.write("Teacher is not found");
            } else {
                printWriter.write("Must to write a teacher's id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}