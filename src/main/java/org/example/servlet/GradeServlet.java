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
    static final String CONTENT_TYPE = "application/json; charset=UTF-8";
    final transient GradeService gradeService;

    static GradeDTOMapper gradeMapper = GradeDTOMapper.INSTANCE;

    public GradeServlet() {
        this.gradeService = new GradeServiceImpl();

        ConnectionManager.setConfig();
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
        int id = 0;
        String[] split = new String[5];

        if (req.getPathInfo() != null && !req.getPathInfo().substring(1).isBlank()) {
            info = req.getPathInfo();
            split = info.split("/");
            id = Integer.parseInt(split[1]);
            try {
                gradeDTO = gradeService.findById(id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        try (PrintWriter printWriter = resp.getWriter()) {
            if (gradeDTO != null) {
                printWriter.write(gradeDTO.toString());
            } else if (id != 0) {
                printWriter.write("Grade is not found");
            } else {
                List<GradeDTO> grades = gradeService.findAll().stream().toList();
                for (GradeDTO grade : grades) {
                    if (grade != grades.getLast()) {
                        printWriter.write(grade.toString() + ", \n");
                    } else {
                        printWriter.write(grade.toString());
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

        GradeDTOMapper mapper = gradeMapper;

        String info = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        JsonObject json = JsonParser.parseString(info).getAsJsonObject();
        Gson gson = new GsonBuilder().create();

        GradeDTO gradeDTO = gson.fromJson(json, GradeDTO.class);

        GradeEntity grade = mapper.mapToEntity(gradeDTO);
        try {
            gradeDTO = gradeService.save(grade);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (PrintWriter printWriter = resp.getWriter()) {
            printWriter.write(gradeDTO.toString());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setSettings(req, resp);

        GradeDTO gradeDTO = null;
        String info = "";
        int id = 0;
        String[] split;

        if (req.getPathInfo() != null && !req.getPathInfo().substring(1).isBlank()) {
            info = req.getPathInfo();
            split = info.split("/");
            id = Integer.parseInt(split[1]);
            try {
                gradeDTO = gradeService.findById(id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            if (id != 0 && gradeDTO != null) {
                info = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
                JsonObject json = JsonParser.parseString(info).getAsJsonObject();
                Gson gson = new GsonBuilder().create();

                gradeDTO = gson.fromJson(json, GradeDTO.class);

                GradeEntity grade = gradeMapper.mapToEntity(gradeDTO, id);
                gradeDTO = gradeService.save(grade);

                try (PrintWriter printWriter = resp.getWriter()) {
                    printWriter.write(gradeDTO.toString());
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

        GradeDTO gradeDTO = null;
        String info = "";
        int id = 0;

        if (req.getPathInfo() != null && !req.getPathInfo().substring(1).isBlank()) {
            info = req.getPathInfo();
            String[] split = info.split("/");
            id = Integer.parseInt(split[1]);
            try {
                gradeDTO = gradeService.findById(id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        try (PrintWriter printWriter = resp.getWriter()) {
            if (!info.isBlank() && gradeDTO != null) {
                printWriter.write("{\"success\":\""+gradeService.deleteById(id)+"\"}");
            } else if (!info.isBlank()) {
                printWriter.write("Grade is not found");
            } else {
                printWriter.write("Must to write a grade's id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}