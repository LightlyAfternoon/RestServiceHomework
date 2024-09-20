package org.example.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.TeacherEntity;
import org.example.service.TeacherService;
import org.example.service.impl.TeacherServiceImpl;
import org.example.servlet.dto.TeacherDTO;
import org.example.servlet.mapper.TeacherDTOMapperImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class TeacherServletTest {
    TeacherServlet teacherServlet;
    TeacherService mockTeacherService;
    TeacherDTO teacherDTO;
    TeacherEntity teacherEntity;
    HttpServletRequest mockRequest;
    HttpServletResponse mockResponse;

    @BeforeEach
    void setUp() {
        mockRequest = Mockito.mock(HttpServletRequest.class);
        mockResponse = Mockito.mock(HttpServletResponse.class);

        mockTeacherService = Mockito.mock(TeacherServiceImpl.class);
        teacherServlet = new TeacherServlet(mockTeacherService);
        teacherEntity = new TeacherEntity(1, "t", "t", "t");
    }

    @Test
    void doGetTest() throws ServletException, IOException, SQLException {
        Mockito.when(mockRequest.getPathInfo()).thenReturn("/1");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));

        Mockito.when(mockTeacherService.findById(1)).thenReturn(teacherEntity);
        teacherDTO = new TeacherDTOMapperImpl().mapToDTO(mockTeacherService.findById(1));

        teacherServlet.doGet(mockRequest, mockResponse);

        teacherEntity = new TeacherEntity(1, "t", "t", "t");
        Assertions.assertEquals(byteArrayOutputStream.toString(), teacherDTO.toString());

        Mockito.when(mockRequest.getPathInfo()).thenReturn("/2");
        byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));

        Mockito.when(mockTeacherService.findById(1)).thenReturn(null);
        teacherServlet.doGet(mockRequest, mockResponse);

        Assertions.assertEquals(byteArrayOutputStream.toString(), "Teacher is not found");

        Mockito.when(mockRequest.getPathInfo()).thenReturn("/");
        byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));

        Mockito.when(mockTeacherService.findAll()).thenReturn(List.of(new TeacherDTOMapperImpl().mapToEntity(teacherDTO)));
        List<TeacherDTO> teachers = mockTeacherService.findAll().stream().map(t -> new TeacherDTOMapperImpl().mapToDTO(t)).toList();

        teacherServlet.doGet(mockRequest, mockResponse);

        StringBuilder s = new StringBuilder();
        for (TeacherDTO dto : teachers) {
            s.append(dto.toString());
        }

        Assertions.assertEquals(byteArrayOutputStream.toString(), s.toString());
    }

    @Test
    void doPostTest() throws ServletException, IOException, SQLException {
        String jsonS = """
                    {
                        "id": 1,
                        "firstName": "t",
                        "lastName": "t",
                        "patronymic": "t"
                    }""";

        JsonObject json = JsonParser.parseString(jsonS).getAsJsonObject();
        Gson gson = new Gson().fromJson(json, Gson.class);

        TeacherDTO teacher = gson.fromJson(json, TeacherDTO.class);

        Mockito.when(mockRequest.getReader()).thenReturn(Mockito.mock(BufferedReader.class));
        Mockito.when(mockRequest.getReader().lines()).thenReturn(jsonS.lines());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));

        teacherEntity = new TeacherDTOMapperImpl().mapToEntity(teacher);
        Mockito.when(mockTeacherService.save(teacherEntity)).thenReturn(teacherEntity);
        teacherDTO = new TeacherDTOMapperImpl().mapToDTO(mockTeacherService.save(teacherEntity));
        teacherServlet.doPost(mockRequest, mockResponse);

        Assertions.assertEquals(byteArrayOutputStream.toString(), teacherDTO.toString());
    }

    @Test
    void doDeleteTest() throws ServletException, IOException, SQLException {
        Mockito.when(mockRequest.getPathInfo()).thenReturn("/1");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));

        Mockito.when(mockTeacherService.findById(1)).thenReturn(teacherEntity);
        Mockito.when(mockTeacherService.deleteById(1)).thenReturn(true);

        teacherServlet.doDelete(mockRequest, mockResponse);

        Assertions.assertEquals(byteArrayOutputStream.toString(), "{\"success\":\""+mockTeacherService.deleteById(1)+"\"}");

        Mockito.when(mockRequest.getPathInfo()).thenReturn("/2");
        Mockito.when(mockTeacherService.findById(2)).thenReturn(null);
        byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));

        teacherServlet.doDelete(mockRequest, mockResponse);
        Assertions.assertEquals(byteArrayOutputStream.toString(), "Teacher is not found");

        Mockito.when(mockRequest.getPathInfo()).thenReturn(null);
        byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));
        teacherServlet.doDelete(mockRequest, mockResponse);
        Assertions.assertEquals(byteArrayOutputStream.toString(), "Must to write a teacher's id");

        Mockito.when(mockRequest.getPathInfo()).thenReturn("/");
        byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));
        teacherServlet.doDelete(mockRequest, mockResponse);
        Assertions.assertEquals(byteArrayOutputStream.toString(), "Must to write a teacher's id");
    }
}