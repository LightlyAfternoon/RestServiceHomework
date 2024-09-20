package org.example.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.StudentEntity;
import org.example.service.StudentService;
import org.example.service.impl.StudentServiceImpl;
import org.example.servlet.dto.StudentDTO;
import org.example.servlet.mapper.StudentDTOMapperImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/student/*")
public class StudentServlet extends HttpServlet {
    static final String CONTENT_TYPE = "application/json; charset=UTF-8";
    final transient StudentService studentService;

    public StudentServlet() {
        this.studentService = new StudentServiceImpl("C:\\Users\\Vika\\IdeaProjects\\Homeworks\\RestServiceHomework\\src\\main\\java\\org\\example\\db\\DbParameters");
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

        if (req.getPathInfo() != null && !req.getPathInfo().substring(1).isBlank()) {
            info = req.getPathInfo().substring(1);
            try {
                studentDTO = new StudentDTOMapperImpl().mapToDTO(studentService.findById(Integer.parseInt(info)));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try (PrintWriter printWriter = resp.getWriter()) {
            if (!info.isBlank() && studentDTO != null) {
                printWriter.write(studentDTO.toString());
            } else if (!info.isBlank()) {
                printWriter.write("Student is not found");
            } else {
                List<StudentDTO> students = studentService.findAll().stream().map(s -> new StudentDTOMapperImpl().mapToDTO(s)).toList();
                for (StudentDTO student : students) {
                    if (student != students.getLast()) {
                        printWriter.write(student.toString() + ", \n");
                    } else {
                        printWriter.write(student.toString());
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setSettings(req, resp);

        StudentDTO studentDTO = null;
        StudentDTOMapperImpl mapper = new StudentDTOMapperImpl();

        String info = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        JsonObject json = JsonParser.parseString(info).getAsJsonObject();
        Gson gson = new Gson().fromJson(json, Gson.class);

        studentDTO = gson.fromJson(json, StudentDTO.class);

        StudentEntity student = mapper.mapToEntity(studentDTO);
        try {
            student = studentService.save(student);
            studentDTO = mapper.mapToDTO(student);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (PrintWriter printWriter = resp.getWriter()) {
            printWriter.write(studentDTO.toString());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setSettings(req, resp);

        StudentDTO studentDTO = null;
        String info = "";

        if (req.getPathInfo() != null && !req.getPathInfo().substring(1).isBlank()) {
            info = req.getPathInfo().substring(1);
            try {
                studentDTO = new StudentDTOMapperImpl().mapToDTO(studentService.findById(Integer.parseInt(info)));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try (PrintWriter printWriter = resp.getWriter()) {
            if (!info.isBlank() && studentDTO != null) {
                printWriter.write("{\"success\":\""+studentService.deleteById(Integer.parseInt(info))+"\"}");
            } else if (!info.isBlank()) {
                printWriter.write("Student is not found");
            } else {
                printWriter.write("Must to write a student's id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}