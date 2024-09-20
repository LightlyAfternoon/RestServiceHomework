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
import org.example.model.GroupEntity;
import org.example.service.GroupService;
import org.example.service.impl.GroupServiceImpl;
import org.example.servlet.dto.GroupDTO;
import org.example.servlet.mapper.GroupDTOMapperImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/group/*")
public class GroupServlet extends HttpServlet {
    static final String CONTENT_TYPE = "application/json; charset=UTF-8";
    final transient GroupService groupService;

    public GroupServlet() {
        this.groupService = new GroupServiceImpl("C:\\Users\\Vika\\IdeaProjects\\Homeworks\\RestServiceHomework\\src\\main\\java\\org\\example\\db\\DbParameters");
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

        if (req.getPathInfo() != null && !req.getPathInfo().substring(1).isBlank()) {
            info = req.getPathInfo().substring(1);
            try {
                groupDTO = new GroupDTOMapperImpl().mapToDTO(groupService.findById(Integer.parseInt(info)));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try (PrintWriter printWriter = resp.getWriter()) {
            if (!info.isBlank() && groupDTO != null) {
                printWriter.write(groupDTO.toString());
            } else if (!info.isBlank()) {
                printWriter.write("Group is not found");
            } else {
                List<GroupDTO> groups = groupService.findAll().stream().map(s -> new GroupDTOMapperImpl().mapToDTO(s)).toList();
                for (GroupDTO group : groups) {
                    if (group != groups.getLast()) {
                        printWriter.write(group.toString() + ", \n");
                    } else {
                        printWriter.write(group.toString());
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

        GroupDTOMapperImpl mapper = new GroupDTOMapperImpl();

        String info = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        JsonObject json = JsonParser.parseString(info).getAsJsonObject();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

        GroupDTO groupDTO = gson.fromJson(json, GroupDTO.class);

        GroupEntity group = mapper.mapToEntity(groupDTO);
        try {
            group = groupService.save(group);
            groupDTO = mapper.mapToDTO(group);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (PrintWriter printWriter = resp.getWriter()) {
            printWriter.write(groupDTO.toString());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setSettings(req, resp);

        GroupDTO groupDTO = null;
        String info = "";

        if (req.getPathInfo() != null && !req.getPathInfo().substring(1).isBlank()) {
            info = req.getPathInfo().substring(1);
            try {
                groupDTO = new GroupDTOMapperImpl().mapToDTO(groupService.findById(Integer.parseInt(info)));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try (PrintWriter printWriter = resp.getWriter()) {
            if (!info.isBlank() && groupDTO != null) {
                printWriter.write("{\"success\":\""+groupService.deleteById(Integer.parseInt(info))+"\"}");
            } else if (!info.isBlank()) {
                printWriter.write("Group is not found");
            } else {
                printWriter.write("Must to write a group's id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}