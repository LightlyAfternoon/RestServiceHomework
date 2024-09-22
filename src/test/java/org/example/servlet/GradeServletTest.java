package org.example.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.GradeEntity;
import org.example.service.GradeService;
import org.example.service.impl.GradeServiceImpl;
import org.example.servlet.dto.GradeDTO;
import org.example.servlet.mapper.GradeDTOMapperImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

class GradeServletTest {
    GradeServlet gradeServlet;
    GradeService mockGradeService;
    GradeDTO gradeDTO;
    GradeEntity gradeEntity;
    HttpServletRequest mockRequest;
    HttpServletResponse mockResponse;

    @BeforeEach
    void setUp() {
        mockRequest = Mockito.mock(HttpServletRequest.class);
        mockResponse = Mockito.mock(HttpServletResponse.class);

        mockGradeService = Mockito.mock(GradeServiceImpl.class);
        gradeServlet = new GradeServlet(mockGradeService);
        gradeEntity = new GradeEntity(1, 1, 1, (short) 1);
    }

    @Test
    void doGetTest() throws ServletException, IOException, SQLException {
        Mockito.when(mockRequest.getPathInfo()).thenReturn("/1");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));

        Mockito.when(mockGradeService.findById(1)).thenReturn(gradeEntity);
        gradeDTO = new GradeDTOMapperImpl().mapToDTO(mockGradeService.findById(1));

        gradeServlet.doGet(mockRequest, mockResponse);

        gradeEntity = new GradeEntity(1, 1, 1, (short) 1);
        Assertions.assertEquals(byteArrayOutputStream.toString(), gradeDTO.toString());

        Mockito.when(mockRequest.getPathInfo()).thenReturn("/2");
        byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));

        Mockito.when(mockGradeService.findById(1)).thenReturn(null);
        gradeServlet.doGet(mockRequest, mockResponse);

        Assertions.assertEquals("Grade is not found", byteArrayOutputStream.toString());

        Mockito.when(mockRequest.getPathInfo()).thenReturn("/");
        byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));

        Mockito.when(mockGradeService.findAll()).thenReturn(List.of(new GradeDTOMapperImpl().mapToEntity(gradeDTO)));
        List<GradeDTO> grades = mockGradeService.findAll().stream().map(t -> new GradeDTOMapperImpl().mapToDTO(t)).toList();

        gradeServlet.doGet(mockRequest, mockResponse);

        StringBuilder s = new StringBuilder();
        for (GradeDTO dto : grades) {
            s.append(dto.toString());
        }

        Assertions.assertEquals(byteArrayOutputStream.toString(), s.toString());
    }

    @Test
    void doPostTest() throws ServletException, IOException, SQLException {
        String jsonS = """
                    {
                        "id": 1,
                        "studentId": 1,
                        "examId": 1,
                        "mark": 1
                    }""";

        JsonObject json = JsonParser.parseString(jsonS).getAsJsonObject();
        Gson gson = new GsonBuilder().create();

        GradeDTO grade = gson.fromJson(json, GradeDTO.class);

        Mockito.when(mockRequest.getReader()).thenReturn(Mockito.mock(BufferedReader.class));
        Mockito.when(mockRequest.getReader().lines()).thenReturn(jsonS.lines());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));

        gradeEntity = new GradeDTOMapperImpl().mapToEntity(grade);
        Mockito.when(mockGradeService.save(gradeEntity)).thenReturn(gradeEntity);
        gradeDTO = new GradeDTOMapperImpl().mapToDTO(mockGradeService.save(gradeEntity));
        gradeServlet.doPost(mockRequest, mockResponse);

        Assertions.assertEquals(byteArrayOutputStream.toString(), gradeDTO.toString());
    }

    @Test
    void doPutTest() throws ServletException, IOException, SQLException {
        String jsonS = """
                    {
                        "id": 1,
                        "studentId": 1,
                        "examId": 1,
                        "mark": 1
                    }""";

        JsonObject json = JsonParser.parseString(jsonS).getAsJsonObject();
        Gson gson = new GsonBuilder().create();

        GradeDTO grade = gson.fromJson(json, GradeDTO.class);

        Mockito.when(mockRequest.getPathInfo()).thenReturn("/1");
        Mockito.when(mockGradeService.findById(1)).thenReturn(gradeEntity);
        Mockito.when(mockRequest.getReader()).thenReturn(Mockito.mock(BufferedReader.class));
        Mockito.when(mockRequest.getReader().lines()).thenReturn(jsonS.lines());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));

        gradeEntity = new GradeDTOMapperImpl().mapToEntity(grade, grade.getId());
        Mockito.when(mockGradeService.save(gradeEntity)).thenReturn(gradeEntity);
        gradeDTO = new GradeDTOMapperImpl().mapToDTO(mockGradeService.save(gradeEntity));
        gradeServlet.doPut(mockRequest, mockResponse);

        Assertions.assertEquals(byteArrayOutputStream.toString(), gradeDTO.toString());

        byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));
    }

    @Test
    void doDeleteTest() throws ServletException, IOException, SQLException {
        Mockito.when(mockRequest.getPathInfo()).thenReturn("/1");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));

        Mockito.when(mockGradeService.findById(1)).thenReturn(gradeEntity);
        Mockito.when(mockGradeService.deleteById(1)).thenReturn(true);

        gradeServlet.doDelete(mockRequest, mockResponse);

        Assertions.assertEquals(byteArrayOutputStream.toString(), "{\"success\":\""+mockGradeService.deleteById(1)+"\"}");

        Mockito.when(mockRequest.getPathInfo()).thenReturn("/2");
        Mockito.when(mockGradeService.findById(2)).thenReturn(null);
        byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));

        gradeServlet.doDelete(mockRequest, mockResponse);
        Assertions.assertEquals("Grade is not found", byteArrayOutputStream.toString());

        Mockito.when(mockRequest.getPathInfo()).thenReturn(null);
        byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));
        gradeServlet.doDelete(mockRequest, mockResponse);
        Assertions.assertEquals("Must to write a grade's id", byteArrayOutputStream.toString());

        Mockito.when(mockRequest.getPathInfo()).thenReturn("/");
        byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));
        gradeServlet.doDelete(mockRequest, mockResponse);
        Assertions.assertEquals("Must to write a grade's id", byteArrayOutputStream.toString());
    }
}