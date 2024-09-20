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
import org.example.model.ExamEntity;
import org.example.service.ExamService;
import org.example.service.impl.ExamServiceImpl;
import org.example.servlet.dto.ExamDTO;
import org.example.servlet.mapper.ExamDTOMapper;
import org.example.servlet.mapper.ExamDTOMapperImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/exam/*")
public class ExamServlet extends HttpServlet {
    static final String CONTENT_TYPE = "application/json; charset=UTF-8";
    final transient ExamService examService;

    public ExamServlet() {
        this.examService = new ExamServiceImpl("C:\\Users\\Vika\\IdeaProjects\\Homeworks\\RestServiceHomework\\src\\main\\java\\org\\example\\db\\DbParameters");
    }

    public ExamServlet(ExamService examService) {
        this.examService = examService;
    }

    private void setSettings(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType(CONTENT_TYPE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setSettings(req, resp);

        ExamDTO examDTO = null;
        String info = "";
        int id = 0;
        String[] split = new String[5];

        if (req.getPathInfo() != null && !req.getPathInfo().substring(1).isBlank()) {
            info = req.getPathInfo();
            split = info.split("/");
            id = Integer.parseInt(split[1]);
            try {
                examDTO = new ExamDTOMapperImpl().mapToDTO(examService.findById(id));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try (PrintWriter printWriter = resp.getWriter()) {
            if (id != 0 && examDTO != null) {
                printWriter.write(examDTO.toString());
            } else if (id != 0) {
                printWriter.write("Exam is not found");
            } else {
                List<ExamDTO> exams = examService.findAll().stream().map(t -> new ExamDTOMapperImpl().mapToDTO(t)).toList();
                for (ExamDTO exam : exams) {
                    if (exam != exams.getLast()) {
                        printWriter.write(exam.toString() + ", \n");
                    } else {
                        printWriter.write(exam.toString());
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

        ExamDTOMapper mapper = new ExamDTOMapperImpl();

        String info = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        JsonObject json = JsonParser.parseString(info).getAsJsonObject();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();;

        ExamDTO examDTO = gson.fromJson(json, ExamDTO.class);

        ExamEntity exam = mapper.mapToEntity(examDTO);
        try {
            exam = examService.save(exam);
            examDTO = mapper.mapToDTO(exam);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (PrintWriter printWriter = resp.getWriter()) {
            printWriter.write(examDTO.toString());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setSettings(req, resp);

        ExamDTO examDTO = null;
        String info = "";
        int id = 0;
        String[] split;

        if (req.getPathInfo() != null && !req.getPathInfo().substring(1).isBlank()) {
            info = req.getPathInfo();
            split = info.split("/");
            id = Integer.parseInt(split[1]);
            try {
                examDTO = new ExamDTOMapperImpl().mapToDTO(examService.findById(id));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try {
            if (id != 0 && examDTO != null) {
                ExamDTOMapperImpl mapper = new ExamDTOMapperImpl();

                info = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
                JsonObject json = JsonParser.parseString(info).getAsJsonObject();
                Gson gson = new Gson().fromJson(json, Gson.class);

                examDTO = gson.fromJson(json, ExamDTO.class);

                ExamEntity exam = mapper.mapToEntity(examDTO, id);
                examDTO = mapper.mapToDTO(examService.save(exam));

                try (PrintWriter printWriter = resp.getWriter()) {
                    printWriter.write(examDTO.toString());
                }
            } else {
                throw new RuntimeException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setSettings(req, resp);

        ExamDTO examDTO = null;
        String info = "";
        int id = 0;

        if (req.getPathInfo() != null && !req.getPathInfo().substring(1).isBlank()) {
            info = req.getPathInfo();
            String[] split = info.split("/");
            id = Integer.parseInt(split[1]);
            try {
                examDTO = new ExamDTOMapperImpl().mapToDTO(examService.findById(id));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try (PrintWriter printWriter = resp.getWriter()) {
            if (!info.isBlank() && examDTO != null) {
                printWriter.write("{\"success\":\""+examService.deleteById(id)+"\"}");
            } else if (!info.isBlank()) {
                printWriter.write("Exam is not found");
            } else {
                printWriter.write("Must to write a exam's id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}