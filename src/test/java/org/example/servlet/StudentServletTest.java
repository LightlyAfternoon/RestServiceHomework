package org.example.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.StudentEntity;
import org.example.service.StudentService;
import org.example.service.impl.StudentServiceImpl;
import org.example.servlet.dto.StudentDTO;
import org.example.servlet.mapper.StudentDTOMapperImpl;
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

class StudentServletTest {
    StudentServlet studentServlet;
    StudentService mockStudentService;
    StudentDTO studentDTO;
    StudentEntity studentEntity;
    HttpServletRequest mockRequest;
    HttpServletResponse mockResponse;

    @BeforeEach
    void setUp() {
        mockRequest = Mockito.mock(HttpServletRequest.class);
        mockResponse = Mockito.mock(HttpServletResponse.class);

        mockStudentService = Mockito.mock(StudentServiceImpl.class);
        studentServlet = new StudentServlet(mockStudentService);
        studentEntity = new StudentEntity(1, "t", "t", "t", 1);
    }

    @Test
    void doGetTest() throws ServletException, IOException, SQLException {
        Mockito.when(mockRequest.getPathInfo()).thenReturn("/1");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));

        Mockito.when(mockStudentService.findById(1)).thenReturn(studentEntity);
        studentDTO = new StudentDTOMapperImpl().mapToDTO(mockStudentService.findById(1));

        studentServlet.doGet(mockRequest, mockResponse);

        studentEntity = new StudentEntity(1, "t", "t", "t", 1);
        Assertions.assertEquals(byteArrayOutputStream.toString(), studentDTO.toString());

        Mockito.when(mockRequest.getPathInfo()).thenReturn("/2");
        byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));

        Mockito.when(mockStudentService.findById(1)).thenReturn(null);
        studentServlet.doGet(mockRequest, mockResponse);

        Assertions.assertEquals("Student is not found", byteArrayOutputStream.toString());

        Mockito.when(mockRequest.getPathInfo()).thenReturn("/");
        byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));

        Mockito.when(mockStudentService.findAll()).thenReturn(List.of(new StudentDTOMapperImpl().mapToEntity(studentDTO)));
        List<StudentDTO> students = mockStudentService.findAll().stream().map(t -> new StudentDTOMapperImpl().mapToDTO(t)).toList();

        studentServlet.doGet(mockRequest, mockResponse);

        StringBuilder s = new StringBuilder();
        for (StudentDTO dto : students) {
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
                        "patronymic": "t",
                        "groupId": 1
                    }""";

        JsonObject json = JsonParser.parseString(jsonS).getAsJsonObject();
        Gson gson = new Gson().fromJson(json, Gson.class);

        StudentDTO student = gson.fromJson(json, StudentDTO.class);

        Mockito.when(mockRequest.getReader()).thenReturn(Mockito.mock(BufferedReader.class));
        Mockito.when(mockRequest.getReader().lines()).thenReturn(jsonS.lines());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));

        studentEntity = new StudentDTOMapperImpl().mapToEntity(student);
        Mockito.when(mockStudentService.save(studentEntity)).thenReturn(studentEntity);
        studentDTO = new StudentDTOMapperImpl().mapToDTO(mockStudentService.save(studentEntity));
        studentServlet.doPost(mockRequest, mockResponse);

        Assertions.assertEquals(byteArrayOutputStream.toString(), studentDTO.toString());
    }

    @Test
    void doPutTest() throws ServletException, IOException, SQLException {
        String jsonS = """
                    {
                        "id": 1,
                        "firstName": "t",
                        "lastName": "t",
                        "patronymic": "t"
                    }""";

        JsonObject json = JsonParser.parseString(jsonS).getAsJsonObject();
        Gson gson = new Gson().fromJson(json, Gson.class);

        StudentDTO student = gson.fromJson(json, StudentDTO.class);

        Mockito.when(mockRequest.getPathInfo()).thenReturn("/1");
        Mockito.when(mockStudentService.findById(1)).thenReturn(studentEntity);
        Mockito.when(mockRequest.getReader()).thenReturn(Mockito.mock(BufferedReader.class));
        Mockito.when(mockRequest.getReader().lines()).thenReturn(jsonS.lines());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));

        studentEntity = new StudentDTOMapperImpl().mapToEntity(student, student.getId());
        Mockito.when(mockStudentService.save(studentEntity)).thenReturn(studentEntity);
        studentDTO = new StudentDTOMapperImpl().mapToDTO(mockStudentService.save(studentEntity));
        studentServlet.doPut(mockRequest, mockResponse);

        Assertions.assertEquals(byteArrayOutputStream.toString(), studentDTO.toString());

        byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));
    }

    @Test
    void doDeleteTest() throws ServletException, IOException, SQLException {
        Mockito.when(mockRequest.getPathInfo()).thenReturn("/1");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));

        Mockito.when(mockStudentService.findById(1)).thenReturn(studentEntity);
        Mockito.when(mockStudentService.deleteById(1)).thenReturn(true);

        studentServlet.doDelete(mockRequest, mockResponse);

        Assertions.assertEquals(byteArrayOutputStream.toString(), "{\"success\":\""+mockStudentService.deleteById(1)+"\"}");

        Mockito.when(mockRequest.getPathInfo()).thenReturn("/2");
        Mockito.when(mockStudentService.findById(2)).thenReturn(null);
        byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));

        studentServlet.doDelete(mockRequest, mockResponse);
        Assertions.assertEquals("Student is not found", byteArrayOutputStream.toString());

        Mockito.when(mockRequest.getPathInfo()).thenReturn(null);
        byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));
        studentServlet.doDelete(mockRequest, mockResponse);
        Assertions.assertEquals("Must to write a student's id", byteArrayOutputStream.toString());

        Mockito.when(mockRequest.getPathInfo()).thenReturn("/");
        byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));
        studentServlet.doDelete(mockRequest, mockResponse);
        Assertions.assertEquals("Must to write a student's id", byteArrayOutputStream.toString());
    }
}