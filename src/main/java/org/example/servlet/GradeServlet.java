package org.example.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.GradeEntity;
import org.example.service.GradeService;
import org.example.service.impl.GradeServiceImpl;
import org.example.servlet.dto.GradeDTO;
import org.example.servlet.mapper.GradeDTOMapperImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/grade/*")
public class GradeServlet extends HttpServlet {
    static final String CONTENT_TYPE = "application/json; charset=UTF-8";
    final transient GradeService gradeService;

    public GradeServlet() {
        this.gradeService = new GradeServiceImpl("C:\\Users\\Vika\\IdeaProjects\\Homeworks\\RestServiceHomework\\src\\main\\java\\org\\example\\db\\DbParameters");
    }

    public GradeServlet(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    private void setSettings(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType(CONTENT_TYPE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setSettings(req, resp);

        GradeDTO gradeDTO = null;
        String info = "";

        if (req.getPathInfo() != null && !req.getPathInfo().substring(1).isBlank()) {
            info = req.getPathInfo().substring(1);
            try {
                gradeDTO = new GradeDTOMapperImpl().mapToDTO(gradeService.findById(Integer.parseInt(info)));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try (PrintWriter printWriter = resp.getWriter()) {
            if (!info.isBlank() && gradeDTO != null) {
                printWriter.write(gradeDTO.toString());
            } else if (!info.isBlank()) {
                printWriter.write("Grade is not found");
            } else {
                List<GradeDTO> grades = gradeService.findAll().stream().map(s -> new GradeDTOMapperImpl().mapToDTO(s)).toList();
                for (GradeDTO grade : grades) {
                    if (grade != grades.getLast()) {
                        printWriter.write(grade.toString() + ", \n");
                    } else {
                        printWriter.write(grade.toString());
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

        GradeDTO gradeDTO = null;
        GradeDTOMapperImpl mapper = new GradeDTOMapperImpl();

        String info = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        JsonObject json = JsonParser.parseString(info).getAsJsonObject();
        Gson gson = new Gson().fromJson(json, Gson.class);

        gradeDTO = gson.fromJson(json, GradeDTO.class);

        GradeEntity grade = mapper.mapToEntity(gradeDTO);
        try {
            grade = gradeService.save(grade);
            gradeDTO = mapper.mapToDTO(grade);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (PrintWriter printWriter = resp.getWriter()) {
            printWriter.write(gradeDTO.toString());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setSettings(req, resp);

        GradeDTO gradeDTO = null;
        String info = "";

        if (req.getPathInfo() != null && !req.getPathInfo().substring(1).isBlank()) {
            info = req.getPathInfo().substring(1);
            try {
                gradeDTO = new GradeDTOMapperImpl().mapToDTO(gradeService.findById(Integer.parseInt(info)));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try (PrintWriter printWriter = resp.getWriter()) {
            if (!info.isBlank() && gradeDTO != null) {
                printWriter.write("{\"success\":\""+gradeService.deleteById(Integer.parseInt(info))+"\"}");
            } else if (!info.isBlank()) {
                printWriter.write("Grade is not found");
            } else {
                printWriter.write("Must to write a grade's id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}