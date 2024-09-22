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
import org.example.servlet.dto.*;
import org.example.servlet.mapper.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/teacher/*")
public class TeacherServlet extends HttpServlet {
    static final String CONTENT_TYPE = "application/json; charset=UTF-8";
    final transient TeacherService teacherService;

    public TeacherServlet() {
        this.teacherService = new TeacherServiceImpl();

        ConnectionManager.setConfig();
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

        TeacherDTO teacherDTO = null;
        String info = "";
        int id = 0;
        String[] split = new String[5];

        if (req.getPathInfo() != null && !req.getPathInfo().substring(1).isBlank()) {
            info = req.getPathInfo();
            split = info.split("/");
            id = Integer.parseInt(split[1]);
            try {
                teacherDTO = new TeacherDTOMapperImpl().mapToDTO(teacherService.findById(id));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        try (PrintWriter printWriter = resp.getWriter()) {
            if (teacherDTO != null) {
                printDTO(split, teacherDTO, printWriter);
            } else if (id != 0) {
                printWriter.write("Teacher is not found");
            } else {
                List<TeacherDTO> teachers = teacherService.findAll().stream().map(t -> new TeacherDTOMapperImpl().mapToDTO(t)).toList();
                for (TeacherDTO teacher : teachers) {
                    if (teacher != teachers.getLast()) {
                        printWriter.write(teacher.toString() + ", \n");
                    } else {
                        printWriter.write(teacher.toString());
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // if var split, that represents pathInfo split by a '/', contains only the entity id,
    // then printWriter writes a json of this entity, otherwise printWriter writes json of
    // all found elements from a list, returned by findList method
    private void printDTO(String[] split, TeacherDTO teacherDTO, PrintWriter printWriter) throws SQLException {
        if (split.length > 2 && !split[2].isBlank()) {
            List<? extends DTO> dtos = findList(split[2], teacherDTO);
            for (DTO dto : dtos) {
                if (dto != dtos.getLast()) {
                    printWriter.write(dto.toString() + ", \n");
                } else {
                    printWriter.write(dto.toString());
                }
            }
        } else {
            printWriter.write(teacherDTO.toString());
        }
    }

    // find and return list of all groups, subjects or exams which have a teacherDTO's id
    private List<? extends DTO> findList(String info, TeacherDTO teacherDTO) throws SQLException {
        if (info.equals("group")) {
            return teacherService.findAllGroupsWithTeacherId(teacherDTO.getId())
                    .stream().map(e -> new GroupDTOMapperImpl().mapToDTO(e)).toList();
        } else if (info.equals("subject")) {
            return teacherService.findAllSubjectsWithTeacherId(teacherDTO.getId())
                    .stream().map(e -> new SubjectDTOMapperImpl().mapToDTO(e)).toList();
        } else if (info.equals("exam")) {
            return teacherService.findAllExamsWithTeacherId(teacherDTO.getId())
                    .stream().map(e -> new ExamDTOMapperImpl().mapToDTO(e)).toList();
        }

        return new ArrayList<>();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setSettings(req, resp);

        TeacherDTOMapper mapper = new TeacherDTOMapperImpl();

        String info = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        JsonObject json = JsonParser.parseString(info).getAsJsonObject();
        Gson gson = new Gson().fromJson(json, Gson.class);

        TeacherDTO teacherDTO = gson.fromJson(json, TeacherDTO.class);

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
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setSettings(req, resp);

        TeacherDTO teacherDTO = null;
        String info = "";
        int id = 0;
        String[] split;

        if (req.getPathInfo() != null && !req.getPathInfo().substring(1).isBlank()) {
            info = req.getPathInfo();
            split = info.split("/");
            id = Integer.parseInt(split[1]);
            try {
                teacherDTO = new TeacherDTOMapperImpl().mapToDTO(teacherService.findById(id));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            if (id != 0 && teacherDTO != null) {
                TeacherDTOMapperImpl mapper = new TeacherDTOMapperImpl();

                info = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
                JsonObject json = JsonParser.parseString(info).getAsJsonObject();
                Gson gson = new Gson().fromJson(json, Gson.class);

                teacherDTO = gson.fromJson(json, TeacherDTO.class);

                TeacherEntity teacher = mapper.mapToEntity(teacherDTO, id);
                teacherDTO = mapper.mapToDTO(teacherService.save(teacher));

                try (PrintWriter printWriter = resp.getWriter()) {
                    printWriter.write(teacherDTO.toString());
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

        TeacherDTO teacherDTO = null;
        String info = "";
        int id = 0;

        if (req.getPathInfo() != null && !req.getPathInfo().substring(1).isBlank()) {
            info = req.getPathInfo();
            String[] split = info.split("/");
            id = Integer.parseInt(split[1]);
            try {
                teacherDTO = new TeacherDTOMapperImpl().mapToDTO(teacherService.findById(id));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        try (PrintWriter printWriter = resp.getWriter()) {
            if (!info.isBlank() && teacherDTO != null) {
                printWriter.write("{\"success\":\""+teacherService.deleteById(id)+"\"}");
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