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
import org.example.model.GroupEntity;
import org.example.service.GroupService;
import org.example.service.impl.GroupServiceImpl;
import org.example.servlet.dto.DTO;
import org.example.servlet.dto.GroupDTO;
import org.example.servlet.mapper.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/group/*")
public class GroupServlet extends HttpServlet {
    static final String CONTENT_TYPE = "application/json; charset=UTF-8";
    final transient GroupService groupService;

    static GroupDTOMapper groupMapper = GroupDTOMapper.INSTANCE;
    static StudentDTOMapper studentMapper = StudentDTOMapper.INSTANCE;
    static SubjectDTOMapper subjectMapper = SubjectDTOMapper.INSTANCE;
    static ExamDTOMapper examMapper = ExamDTOMapper.INSTANCE;

    public GroupServlet() {
        this.groupService = new GroupServiceImpl();

        ConnectionManager.setConfig();
    }

    public GroupServlet(GroupService groupService) {
        this.groupService = groupService;
    }

    private void setSettings(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType(CONTENT_TYPE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setSettings(req, resp);

        GroupDTO groupDTO = null;
        String info = "";
        int id = 0;
        String[] split = new String[5];

        if (req.getPathInfo() != null && !req.getPathInfo().substring(1).isBlank()) {
            info = req.getPathInfo();
            split = info.split("/");
            id = Integer.parseInt(split[1]);
            try {
                groupDTO = groupMapper.mapToDTO(groupService.findById(id));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        try (PrintWriter printWriter = resp.getWriter()) {
            if (groupDTO != null) {
                printDTO(split, groupDTO, printWriter);
            } else if (id != 0) {
                printWriter.write("Group is not found");
            } else {
                List<GroupDTO> groups = groupService.findAll().stream().map(t -> groupMapper.mapToDTO(t)).toList();
                for (GroupDTO group : groups) {
                    if (group != groups.getLast()) {
                        printWriter.write(group.toString() + ", \n");
                    } else {
                        printWriter.write(group.toString());
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void printDTO(String[] split, GroupDTO groupDTO, PrintWriter printWriter) throws SQLException {
        if (split.length > 2 && !split[2].isBlank()) {
            List<? extends DTO> dtos = findList(split[2], groupDTO);
            for (DTO dto : dtos) {
                if (dto != dtos.getLast()) {
                    printWriter.write(dto.toString() + ", \n");
                } else {
                    printWriter.write(dto.toString());
                }
            }
        } else {
            printWriter.write(groupDTO.toString());
        }
    }

    private List<? extends DTO> findList(String info, GroupDTO groupDTO) throws SQLException {
        if (info.equals("student")) {
            return groupService.findAllStudentsWithGroupId(groupDTO.getId())
                    .stream().map(e -> studentMapper.mapToDTO(e)).toList();
        } else if (info.equals("subject")) {
            return groupService.findAllSubjectsWithGroupId(groupDTO.getId())
                    .stream().map(e -> subjectMapper.mapToDTO(e)).toList();
        } else if (info.equals("exam")) {
            return groupService.findAllExamsWithGroupId(groupDTO.getId())
                    .stream().map(e -> examMapper.mapToDTO(e)).toList();
        }

        return new ArrayList<>();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setSettings(req, resp);

        String info = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        JsonObject json = JsonParser.parseString(info).getAsJsonObject();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

        GroupDTO groupDTO = gson.fromJson(json, GroupDTO.class);

        GroupEntity group = groupMapper.mapToEntity(groupDTO);
        try {
            group = groupService.save(group);
            groupDTO = groupMapper.mapToDTO(group);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (PrintWriter printWriter = resp.getWriter()) {
            printWriter.write(groupDTO.toString());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setSettings(req, resp);

        GroupDTO groupDTO = null;
        String info = "";
        int id = 0;
        String[] split;

        if (req.getPathInfo() != null && !req.getPathInfo().substring(1).isBlank()) {
            info = req.getPathInfo();
            split = info.split("/");
            id = Integer.parseInt(split[1]);
            try {
                groupDTO = groupMapper.mapToDTO(groupService.findById(id));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            if (id != 0 && groupDTO != null) {
                info = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
                JsonObject json = JsonParser.parseString(info).getAsJsonObject();
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

                groupDTO = gson.fromJson(json, GroupDTO.class);

                GroupEntity group = groupMapper.mapToEntity(groupDTO, id);
                groupDTO = groupMapper.mapToDTO(groupService.save(group));

                try (PrintWriter printWriter = resp.getWriter()) {
                    printWriter.write(groupDTO.toString());
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

        GroupDTO groupDTO = null;
        String info = "";
        int id = 0;

        if (req.getPathInfo() != null && !req.getPathInfo().substring(1).isBlank()) {
            info = req.getPathInfo();
            String[] split = info.split("/");
            id = Integer.parseInt(split[1]);
            try {
                groupDTO = groupMapper.mapToDTO(groupService.findById(id));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        try (PrintWriter printWriter = resp.getWriter()) {
            if (!info.isBlank() && groupDTO != null) {
                printWriter.write("{\"success\":\""+groupService.deleteById(id)+"\"}");
            } else if (!info.isBlank()) {
                printWriter.write("Group is not found");
            } else {
                printWriter.write("Must to write a group's id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}