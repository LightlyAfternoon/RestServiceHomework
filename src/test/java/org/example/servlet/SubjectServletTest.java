package org.example.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.SubjectEntity;
import org.example.service.SubjectService;
import org.example.service.impl.SubjectServiceImpl;
import org.example.servlet.dto.SubjectDTO;
import org.example.servlet.mapper.SubjectDTOMapperImpl;
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

class SubjectServletTest {
    SubjectServlet subjectServlet;
    SubjectService mockSubjectService;
    SubjectDTO subjectDTO;
    SubjectEntity subjectEntity;
    HttpServletRequest mockRequest;
    HttpServletResponse mockResponse;

    @BeforeEach
    void setUp() {
        mockRequest = Mockito.mock(HttpServletRequest.class);
        mockResponse = Mockito.mock(HttpServletResponse.class);

        mockSubjectService = Mockito.mock(SubjectServiceImpl.class);
        subjectServlet = new SubjectServlet(mockSubjectService);
        subjectEntity = new SubjectEntity(1, "t");
    }

    @Test
    void doGetTest() throws ServletException, IOException, SQLException {
        Mockito.when(mockRequest.getPathInfo()).thenReturn("/1");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));

        Mockito.when(mockSubjectService.findById(1)).thenReturn(subjectEntity);
        subjectDTO = new SubjectDTOMapperImpl().mapToDTO(mockSubjectService.findById(1));

        subjectServlet.doGet(mockRequest, mockResponse);

        subjectEntity = new SubjectEntity(1, "t");
        Assertions.assertEquals(byteArrayOutputStream.toString(), subjectDTO.toString());

        Mockito.when(mockRequest.getPathInfo()).thenReturn("/2");
        byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));

        Mockito.when(mockSubjectService.findById(1)).thenReturn(null);
        subjectServlet.doGet(mockRequest, mockResponse);

        Assertions.assertEquals(byteArrayOutputStream.toString(), "Subject is not found");

        Mockito.when(mockRequest.getPathInfo()).thenReturn("/");
        byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));

        Mockito.when(mockSubjectService.findAll()).thenReturn(List.of(new SubjectDTOMapperImpl().mapToEntity(subjectDTO)));
        List<SubjectDTO> subjects = mockSubjectService.findAll().stream().map(t -> new SubjectDTOMapperImpl().mapToDTO(t)).toList();

        subjectServlet.doGet(mockRequest, mockResponse);

        StringBuilder s = new StringBuilder();
        for (SubjectDTO dto : subjects) {
            s.append(dto.toString());
        }

        Assertions.assertEquals(byteArrayOutputStream.toString(), s.toString());
    }

    @Test
    void doPostTest() throws ServletException, IOException, SQLException {
        String jsonS = """
                    {
                        "id": 1,
                        "name": "t"
                    }""";

        JsonObject json = JsonParser.parseString(jsonS).getAsJsonObject();
        Gson gson = new Gson().fromJson(json, Gson.class);

        SubjectDTO subject = gson.fromJson(json, SubjectDTO.class);

        Mockito.when(mockRequest.getReader()).thenReturn(Mockito.mock(BufferedReader.class));
        Mockito.when(mockRequest.getReader().lines()).thenReturn(jsonS.lines());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));

        subjectEntity = new SubjectDTOMapperImpl().mapToEntity(subject);
        Mockito.when(mockSubjectService.save(subjectEntity)).thenReturn(subjectEntity);
        subjectDTO = new SubjectDTOMapperImpl().mapToDTO(mockSubjectService.save(subjectEntity));
        subjectServlet.doPost(mockRequest, mockResponse);

        Assertions.assertEquals(byteArrayOutputStream.toString(), subjectDTO.toString());
    }

    @Test
    void doDeleteTest() throws ServletException, IOException, SQLException {
        Mockito.when(mockRequest.getPathInfo()).thenReturn("/1");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));

        Mockito.when(mockSubjectService.findById(1)).thenReturn(subjectEntity);
        Mockito.when(mockSubjectService.deleteById(1)).thenReturn(true);

        subjectServlet.doDelete(mockRequest, mockResponse);

        Assertions.assertEquals(byteArrayOutputStream.toString(), "{\"success\":\""+mockSubjectService.deleteById(1)+"\"}");

        Mockito.when(mockRequest.getPathInfo()).thenReturn("/2");
        Mockito.when(mockSubjectService.findById(2)).thenReturn(null);
        byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));

        subjectServlet.doDelete(mockRequest, mockResponse);
        Assertions.assertEquals(byteArrayOutputStream.toString(), "Subject is not found");

        Mockito.when(mockRequest.getPathInfo()).thenReturn(null);
        byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));
        subjectServlet.doDelete(mockRequest, mockResponse);
        Assertions.assertEquals(byteArrayOutputStream.toString(), "Must to write a subject's id");

        Mockito.when(mockRequest.getPathInfo()).thenReturn("/");
        byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));
        subjectServlet.doDelete(mockRequest, mockResponse);
        Assertions.assertEquals(byteArrayOutputStream.toString(), "Must to write a subject's id");
    }
}