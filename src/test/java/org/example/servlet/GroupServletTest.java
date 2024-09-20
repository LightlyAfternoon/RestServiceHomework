package org.example.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.GroupEntity;
import org.example.service.GroupService;
import org.example.service.impl.GroupServiceImpl;
import org.example.servlet.dto.GroupDTO;
import org.example.servlet.mapper.GroupDTOMapperImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

class GroupServletTest {
    GroupServlet groupServlet;
    GroupService mockGroupService;
    GroupDTO groupDTO;
    GroupEntity groupEntity;
    HttpServletRequest mockRequest;
    HttpServletResponse mockResponse;

    @BeforeEach
    void setUp() {
        mockRequest = Mockito.mock(HttpServletRequest.class);
        mockResponse = Mockito.mock(HttpServletResponse.class);

        mockGroupService = Mockito.mock(GroupServiceImpl.class);
        groupServlet = new GroupServlet(mockGroupService);
        groupEntity = new GroupEntity(1, "t", new Date(new GregorianCalendar(2029, Calendar.SEPTEMBER, 1).getTimeInMillis()),
                new Date(new GregorianCalendar(2033, Calendar.JULY, 3).getTimeInMillis()), 1);
    }

    @Test
    void doGetTest() throws ServletException, IOException, SQLException {
        Mockito.when(mockRequest.getPathInfo()).thenReturn("/1");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));

        Mockito.when(mockGroupService.findById(1)).thenReturn(groupEntity);
        groupDTO = new GroupDTOMapperImpl().mapToDTO(mockGroupService.findById(1));

        groupServlet.doGet(mockRequest, mockResponse);

        groupEntity = new GroupEntity(1, "t", new Date(new GregorianCalendar(2029, Calendar.SEPTEMBER, 1).getTimeInMillis()),
                new Date(new GregorianCalendar(2033, Calendar.JULY, 3).getTimeInMillis()), 1);
        Assertions.assertEquals(byteArrayOutputStream.toString(), groupDTO.toString());

        Mockito.when(mockRequest.getPathInfo()).thenReturn("/2");
        byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));

        Mockito.when(mockGroupService.findById(1)).thenReturn(null);
        groupServlet.doGet(mockRequest, mockResponse);

        Assertions.assertEquals(byteArrayOutputStream.toString(), "Group is not found");

        Mockito.when(mockRequest.getPathInfo()).thenReturn("/");
        byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));

        Mockito.when(mockGroupService.findAll()).thenReturn(List.of(new GroupDTOMapperImpl().mapToEntity(groupDTO)));
        List<GroupDTO> groups = mockGroupService.findAll().stream().map(t -> new GroupDTOMapperImpl().mapToDTO(t)).toList();

        groupServlet.doGet(mockRequest, mockResponse);

        StringBuilder s = new StringBuilder();
        for (GroupDTO dto : groups) {
            s.append(dto.toString());
        }

        Assertions.assertEquals(byteArrayOutputStream.toString(), s.toString());
    }

    @Test
    void doPostTest() throws ServletException, IOException, SQLException {
        String jsonS = """
                    {
                        "id": 1,
                        "name": "t",
                        "startDate": "2029-09-01",
                        "endDate": "2033-07-03",
                        "teacherId": 1
                    }""";

        JsonObject json = JsonParser.parseString(jsonS).getAsJsonObject();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

        GroupDTO group = gson.fromJson(json, GroupDTO.class);

        Mockito.when(mockRequest.getReader()).thenReturn(Mockito.mock(BufferedReader.class));
        Mockito.when(mockRequest.getReader().lines()).thenReturn(jsonS.lines());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));

        groupEntity = new GroupDTOMapperImpl().mapToEntity(group);
        Mockito.when(mockGroupService.save(groupEntity)).thenReturn(groupEntity);
        groupDTO = new GroupDTOMapperImpl().mapToDTO(mockGroupService.save(groupEntity));
        groupServlet.doPost(mockRequest, mockResponse);

        Assertions.assertEquals(byteArrayOutputStream.toString(), groupDTO.toString());
    }

    @Test
    void doDeleteTest() throws ServletException, IOException, SQLException {
        Mockito.when(mockRequest.getPathInfo()).thenReturn("/1");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));

        Mockito.when(mockGroupService.findById(1)).thenReturn(groupEntity);
        Mockito.when(mockGroupService.deleteById(1)).thenReturn(true);

        groupServlet.doDelete(mockRequest, mockResponse);

        Assertions.assertEquals(byteArrayOutputStream.toString(), "{\"success\":\""+mockGroupService.deleteById(1)+"\"}");

        Mockito.when(mockRequest.getPathInfo()).thenReturn("/2");
        Mockito.when(mockGroupService.findById(2)).thenReturn(null);
        byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));

        groupServlet.doDelete(mockRequest, mockResponse);
        Assertions.assertEquals(byteArrayOutputStream.toString(), "Group is not found");

        Mockito.when(mockRequest.getPathInfo()).thenReturn(null);
        byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));
        groupServlet.doDelete(mockRequest, mockResponse);
        Assertions.assertEquals(byteArrayOutputStream.toString(), "Must to write a group's id");

        Mockito.when(mockRequest.getPathInfo()).thenReturn("/");
        byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));
        groupServlet.doDelete(mockRequest, mockResponse);
        Assertions.assertEquals(byteArrayOutputStream.toString(), "Must to write a group's id");
    }
}