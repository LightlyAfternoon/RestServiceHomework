package org.example.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.SubjectEntity;
import org.example.service.SubjectService;
import org.example.service.impl.SubjectServiceImpl;
import org.example.servlet.dto.SubjectDTO;
import org.example.servlet.mapper.SubjectDTOMapperImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/subject/*")
public class SubjectServlet extends HttpServlet {
    static final String CONTENT_TYPE = "application/json; charset=UTF-8";
    final transient SubjectService subjectService;

    public SubjectServlet() {
        this.subjectService = new SubjectServiceImpl("C:\\Users\\Vika\\IdeaProjects\\Homeworks\\RestServiceHomework\\src\\main\\java\\org\\example\\db\\DbParameters");
    }

    public SubjectServlet(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    private void setSettings(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType(CONTENT_TYPE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setSettings(req, resp);

        SubjectDTO subjectDTO = null;
        String info = "";

        if (req.getPathInfo() != null && !req.getPathInfo().substring(1).isBlank()) {
            info = req.getPathInfo().substring(1);
            try {
                subjectDTO = new SubjectDTOMapperImpl().mapToDTO(subjectService.findById(Integer.parseInt(info)));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try (PrintWriter printWriter = resp.getWriter()) {
            if (!info.isBlank() && subjectDTO != null) {
                printWriter.write(subjectDTO.toString());
            } else if (!info.isBlank()) {
                printWriter.write("Subject is not found");
            } else {
                List<SubjectDTO> subjects = subjectService.findAll().stream().map(s -> new SubjectDTOMapperImpl().mapToDTO(s)).toList();
                for (SubjectDTO subject : subjects) {
                    if (subject != subjects.getLast()) {
                        printWriter.write(subject.toString() + ", \n");
                    } else {
                        printWriter.write(subject.toString());
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

        SubjectDTO subjectDTO = null;
        SubjectDTOMapperImpl mapper = new SubjectDTOMapperImpl();

        String info = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        JsonObject json = JsonParser.parseString(info).getAsJsonObject();
        Gson gson = new Gson().fromJson(json, Gson.class);

        subjectDTO = gson.fromJson(json, SubjectDTO.class);

        SubjectEntity subject = mapper.mapToEntity(subjectDTO);
        try {
            subject = subjectService.save(subject);
            subjectDTO = mapper.mapToDTO(subject);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (PrintWriter printWriter = resp.getWriter()) {
            printWriter.write(subjectDTO.toString());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setSettings(req, resp);

        SubjectDTO subjectDTO = null;
        String info = "";

        if (req.getPathInfo() != null && !req.getPathInfo().substring(1).isBlank()) {
            info = req.getPathInfo().substring(1);
            try {
                subjectDTO = new SubjectDTOMapperImpl().mapToDTO(subjectService.findById(Integer.parseInt(info)));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try (PrintWriter printWriter = resp.getWriter()) {
            if (!info.isBlank() && subjectDTO != null) {
                printWriter.write("{\"success\":\""+subjectService.deleteById(Integer.parseInt(info))+"\"}");
            } else if (!info.isBlank()) {
                printWriter.write("Subject is not found");
            } else {
                printWriter.write("Must to write a subject's id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}