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
import org.example.servlet.dto.StudentDTO;
import org.example.servlet.mapper.StudentDTOMapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/student/*")
public class StudentServlet extends HttpServlet {
    static final String CONTENT_TYPE = "application/json; charset=UTF-8";
    final transient StudentService studentService;

    static StudentDTOMapper studentMapper = StudentDTOMapper.INSTANCE;

    public StudentServlet() {
        this.studentService = new StudentServiceImpl();

        ConnectionManager.setConfig();
    }

    public StudentServlet(StudentService studentService) {
        this.studentService = studentService;
    }

    private void setSettings(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType(CONTENT_TYPE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setSettings(req, resp);

        StudentDTO studentDTO = null;
        String info = "";
        int id = 0;
        String[] split = new String[5];

        if (req.getPathInfo() != null && !req.getPathInfo().substring(1).isBlank()) {
            info = req.getPathInfo();
            split = info.split("/");
            id = Integer.parseInt(split[1]);
            try {
                studentDTO = studentMapper.mapToDTO(studentService.findById(id));
            } catch (SQLException e) {
            throw new RuntimeException(e);
            }
        }

        try (PrintWriter printWriter = resp.getWriter()) {
            if (id != 0 && studentDTO != null) {
                printWriter.write(studentDTO.toString());
            } else if (id != 0) {
                printWriter.write("Student is not found");
            } else {
                List<StudentDTO> students = studentService.findAll().stream().map(t -> studentMapper.mapToDTO(t)).toList();
                for (StudentDTO student : students) {
                    if (student != students.getLast()) {
                        printWriter.write(student.toString() + ", \n");
                    } else {
                        printWriter.write(student.toString());
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setSettings(req, resp);

        String info = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        JsonObject json = JsonParser.parseString(info).getAsJsonObject();
        Gson gson = new GsonBuilder().create();

        StudentDTO studentDTO = gson.fromJson(json, StudentDTO.class);

        StudentEntity student = studentMapper.mapToEntity(studentDTO);
        try {
            student = studentService.save(student);
            studentDTO = studentMapper.mapToDTO(student);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (PrintWriter printWriter = resp.getWriter()) {
            printWriter.write(studentDTO.toString());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setSettings(req, resp);

        StudentDTO studentDTO = null;
        String info = "";
        int id = 0;
        String[] split;

        if (req.getPathInfo() != null && !req.getPathInfo().substring(1).isBlank()) {
            info = req.getPathInfo();
            split = info.split("/");
            id = Integer.parseInt(split[1]);
            try {
                studentDTO = studentMapper.mapToDTO(studentService.findById(id));
            } catch (SQLException e) {
            throw new RuntimeException(e);
            }
        }

        try {
            if (studentDTO != null) {
                info = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
                JsonObject json = JsonParser.parseString(info).getAsJsonObject();
                Gson gson = new GsonBuilder().create();

                studentDTO = gson.fromJson(json, StudentDTO.class);

                StudentEntity student = studentMapper.mapToEntity(studentDTO, id);
                studentDTO = studentMapper.mapToDTO(studentService.save(student));

                try (PrintWriter printWriter = resp.getWriter()) {
                    printWriter.write(studentDTO.toString());
                }
            } else {
                throw new RuntimeException();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setSettings(req, resp);

        StudentDTO studentDTO = null;
        String info = "";
        int id = 0;

        if (req.getPathInfo() != null && !req.getPathInfo().substring(1).isBlank()) {
            info = req.getPathInfo();
            String[] split = info.split("/");
            id = Integer.parseInt(split[1]);
            try {
                studentDTO = studentMapper.mapToDTO(studentService.findById(id));
            } catch (SQLException e) {
            throw new RuntimeException(e);
            }
        }

        try (PrintWriter printWriter = resp.getWriter()) {
            if (!info.isBlank() && studentDTO != null) {
                printWriter.write("{\"success\":\""+studentService.deleteById(id)+"\"}");
            } else if (!info.isBlank()) {
                printWriter.write("Student is not found");
            } else {
                printWriter.write("Must to write a student's id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}