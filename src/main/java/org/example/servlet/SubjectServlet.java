package org.example.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.GroupEntity;
import org.example.model.SubjectEntity;
import org.example.model.TeacherEntity;
import org.example.service.SubjectService;
import org.example.service.impl.GroupServiceImpl;
import org.example.service.impl.SubjectServiceImpl;
import org.example.service.impl.TeacherServiceImpl;
import org.example.servlet.dto.DTO;
import org.example.servlet.dto.SubjectDTO;
import org.example.servlet.mapper.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
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
        int id = 0;
        String[] split = new String[5];

        if (req.getPathInfo() != null && !req.getPathInfo().substring(1).isBlank()) {
            info = req.getPathInfo();
            split = info.split("/");
            id = Integer.parseInt(split[1]);
            try {
                subjectDTO = new SubjectDTOMapperImpl().mapToDTO(subjectService.findById(id));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try (PrintWriter printWriter = resp.getWriter()) {
            if (id != 0 && subjectDTO != null) {
                if (split.length > 2 && !split[2].isBlank()) {
                    List<? extends DTO> dtos = findList(split[2], subjectDTO);
                    for (DTO dto : dtos) {
                        if (dto != dtos.getLast()) {
                            printWriter.write(dto.toString() + ", \n");
                        } else {
                            printWriter.write(dto.toString());
                        }
                    }
                } else {
                    printWriter.write(subjectDTO.toString());
                }
            } else if (id != 0) {
                printWriter.write("Subject is not found");
            } else {
                List<SubjectDTO> subjects = subjectService.findAll().stream().map(t -> new SubjectDTOMapperImpl().mapToDTO(t)).toList();
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

    private List<? extends DTO> findList(String info, SubjectDTO subjectDTO) throws SQLException {
        if (info.equals("group")) {
            return subjectService.findAllGroupsWithSubjectId(subjectDTO.getId())
                    .stream().map(e -> new GroupDTOMapperImpl().mapToDTO(e)).toList();
        } else if (info.equals("teacher")) {
            return subjectService.findAllTeachersWithSubjectId(subjectDTO.getId())
                    .stream().map(e -> new TeacherDTOMapperImpl().mapToDTO(e)).toList();
        } else if (info.equals("exam")) {
            return subjectService.findAllExamsWithSubjectId(subjectDTO.getId())
                    .stream().map(e -> new ExamDTOMapperImpl().mapToDTO(e)).toList();
        }

        return new ArrayList<>();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setSettings(req, resp);

        SubjectDTOMapper mapper = new SubjectDTOMapperImpl();

        String info = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        JsonObject json = JsonParser.parseString(info).getAsJsonObject();
        Gson gson = new Gson().fromJson(json, Gson.class);

        SubjectDTO subjectDTO = gson.fromJson(json, SubjectDTO.class);

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
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setSettings(req, resp);

        SubjectDTO subjectDTO = null;
        String info = "";
        int id = 0;
        String[] split = new String[5];

        if (req.getPathInfo() != null && !req.getPathInfo().substring(1).isBlank()) {
            info = req.getPathInfo();
            split = info.split("/");
            id = Integer.parseInt(split[1]);
            try {
                subjectDTO = new SubjectDTOMapperImpl().mapToDTO(subjectService.findById(id));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try {
            if (id != 0 && subjectDTO != null) {
                if (split.length > 3 && !split[3].isBlank()) {
                    id = Integer.parseInt(split[3]);
                    DTO dto = findDTO(split[2], subjectDTO, id);

                    try (PrintWriter printWriter = resp.getWriter()) {
                        printWriter.write(dto.toString());
                    }
                } else {
                    SubjectDTOMapperImpl mapper = new SubjectDTOMapperImpl();

                    info = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
                    JsonObject json = JsonParser.parseString(info).getAsJsonObject();
                    Gson gson = new Gson().fromJson(json, Gson.class);

                    subjectDTO = gson.fromJson(json, SubjectDTO.class);

                    SubjectEntity subject = mapper.mapToEntity(subjectDTO, id);
                    subjectDTO = mapper.mapToDTO(subjectService.save(subject));

                    try (PrintWriter printWriter = resp.getWriter()) {
                        printWriter.write(subjectDTO.toString());
                    }
                }
            } else {
                throw new RuntimeException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private DTO findDTO(String info, SubjectDTO subjectDTO, int id) throws SQLException {
        if (info.equals("teacher")) {
            TeacherEntity teacher = subjectService.save(new SubjectDTOMapperImpl().mapToEntity(subjectDTO), new TeacherServiceImpl().findById(id));
            return new TeacherDTOMapperImpl().mapToDTO(teacher);
        } else if (info.equals("group")) {
            GroupEntity group = subjectService.save(new SubjectDTOMapperImpl().mapToEntity(subjectDTO), new GroupServiceImpl().findById(id));
            return new GroupDTOMapperImpl().mapToDTO(group);
        }

        return new SubjectDTO();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setSettings(req, resp);

        SubjectDTO subjectDTO = null;
        String info = "";
        int id = 0;

        if (req.getPathInfo() != null && !req.getPathInfo().substring(1).isBlank()) {
            info = req.getPathInfo();
            String[] split = info.split("/");
            id = Integer.parseInt(split[1]);
            try {
                subjectDTO = new SubjectDTOMapperImpl().mapToDTO(subjectService.findById(id));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try (PrintWriter printWriter = resp.getWriter()) {
            if (!info.isBlank() && subjectDTO != null) {
                printWriter.write("{\"success\":\""+subjectService.deleteById(id)+"\"}");
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