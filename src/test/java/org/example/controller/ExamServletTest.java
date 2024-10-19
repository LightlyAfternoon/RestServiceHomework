package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.ExamEntity;
import org.example.model.GroupEntity;
import org.example.model.SubjectEntity;
import org.example.model.TeacherEntity;
import org.example.service.ExamService;
import org.example.service.impl.ExamServiceImpl;
import org.example.controller.dto.ExamDTO;
import org.example.controller.mapper.ExamDTOMapper;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

class ExamServletTest {
    ExamServlet examServlet;
    ExamService mockExamService;
    ExamDTO examDTO;
    ExamEntity examEntity;
    HttpServletRequest mockRequest;
    HttpServletResponse mockResponse;
    TeacherEntity teacher;
    GroupEntity group;
    SubjectEntity subject;

    ExamDTOMapper examMapper = ExamDTOMapper.INSTANCE;

    @BeforeEach
    void setUp() {
        mockRequest = Mockito.mock(HttpServletRequest.class);
        mockResponse = Mockito.mock(HttpServletResponse.class);

        mockExamService = Mockito.mock(ExamServiceImpl.class);
        examServlet = new ExamServlet(mockExamService);

        teacher = new TeacherEntity(1, "t", "t", "t");
        group = new GroupEntity(1, "t", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), teacher);
        subject = new SubjectEntity(1, "t");
        examEntity = new ExamEntity(1, new Date(new GregorianCalendar(2029, Calendar.SEPTEMBER, 1).getTimeInMillis()),
                group, subject, teacher);
    }

//    @Test
//    void doGetTest() throws ServletException, IOException, SQLException {
//        Mockito.when(mockRequest.getPathInfo()).thenReturn("/1");
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));
//
//        Mockito.when(mockExamService.findById(1)).thenReturn(examMapper.mapToDTO(examEntity));
//        examDTO = mockExamService.findById(1);
//
//        examServlet.doGet(mockRequest, mockResponse);
//
//        examEntity = new ExamEntity(1, new Date(new GregorianCalendar(2029, Calendar.SEPTEMBER, 1).getTimeInMillis()),
//                group, subject, teacher);
//        Assertions.assertEquals(byteArrayOutputStream.toString(), examDTO.toString());
//
//        Mockito.when(mockRequest.getPathInfo()).thenReturn("/2");
//        byteArrayOutputStream = new ByteArrayOutputStream();
//        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));
//
//        Mockito.when(mockExamService.findById(1)).thenReturn(null);
//        examServlet.doGet(mockRequest, mockResponse);
//
//        Assertions.assertEquals("Exam is not found", byteArrayOutputStream.toString());
//
//        Mockito.when(mockRequest.getPathInfo()).thenReturn("/");
//        byteArrayOutputStream = new ByteArrayOutputStream();
//        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));
//
//        Mockito.when(mockExamService.findAll()).thenReturn(List.of(examDTO));
//        List<ExamDTO> exams = mockExamService.findAll().stream().toList();
//
//        examServlet.doGet(mockRequest, mockResponse);
//
//        StringBuilder s = new StringBuilder();
//        for (ExamDTO dto : exams) {
//            s.append(dto.toString());
//        }
//
//        Assertions.assertEquals(byteArrayOutputStream.toString(), s.toString());
//    }
//
//    @Test
//    void doPostTest() throws ServletException, IOException, SQLException {
//        String jsonS = """
//                    {
//                        "id": 0,
//                        "startDate": "2029-09-01",
//                        "groupId": 1,
//                        "subjectExamId": 1
//                    }""";
//
//        JsonObject json = JsonParser.parseString(jsonS).getAsJsonObject();
//        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
//
//        ExamDTO exam = gson.fromJson(json, ExamDTO.class);
//
//        Mockito.when(mockRequest.getReader()).thenReturn(Mockito.mock(BufferedReader.class));
//        Mockito.when(mockRequest.getReader().lines()).thenReturn(jsonS.lines());
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));
//
//        examEntity = examMapper.mapToEntity(exam);
//        Mockito.when(mockExamService.save(examEntity)).thenReturn(examMapper.mapToDTO(examEntity));
//        examDTO = mockExamService.save(examEntity);
//        examServlet.doPost(mockRequest, mockResponse);
//
//        Assertions.assertEquals(byteArrayOutputStream.toString(), examDTO.toString());
//    }
//
//    @Test
//    void doPutTest() throws ServletException, IOException, SQLException {
//        String jsonS = """
//                    {
//                        "id": 0,
//                        "startDate": "2029-09-01",
//                        "groupId": 1,
//                        "subjectExamId": 1
//                    }""";
//
//        JsonObject json = JsonParser.parseString(jsonS).getAsJsonObject();
//        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
//
//        ExamDTO exam = gson.fromJson(json, ExamDTO.class);
//
//        Mockito.when(mockRequest.getPathInfo()).thenReturn("/1");
//        Mockito.when(mockExamService.findById(1)).thenReturn(examMapper.mapToDTO(examEntity));
//        Mockito.when(mockRequest.getReader()).thenReturn(Mockito.mock(BufferedReader.class));
//        Mockito.when(mockRequest.getReader().lines()).thenReturn(jsonS.lines());
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));
//
//        examEntity = examMapper.mapToEntity(exam, 1);
//        Mockito.when(mockExamService.save(examEntity)).thenReturn(examMapper.mapToDTO(examEntity));
//        examDTO = mockExamService.save(examEntity);
//        examServlet.doPut(mockRequest, mockResponse);
//
//        Assertions.assertEquals(byteArrayOutputStream.toString(), examDTO.toString());
//
//        byteArrayOutputStream = new ByteArrayOutputStream();
//        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));
//    }
//
//    @Test
//    void doDeleteTest() throws ServletException, IOException, SQLException {
//        Mockito.when(mockRequest.getPathInfo()).thenReturn("/1");
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));
//
//        Mockito.when(mockExamService.findById(1)).thenReturn(examMapper.mapToDTO(examEntity));
//        Mockito.when(mockExamService.deleteById(1)).thenReturn(true);
//
//        examServlet.doDelete(mockRequest, mockResponse);
//
//        Assertions.assertEquals(byteArrayOutputStream.toString(), "{\"success\":\""+mockExamService.deleteById(1)+"\"}");
//
//        Mockito.when(mockRequest.getPathInfo()).thenReturn("/2");
//        Mockito.when(mockExamService.findById(2)).thenReturn(null);
//        byteArrayOutputStream = new ByteArrayOutputStream();
//        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));
//
//        examServlet.doDelete(mockRequest, mockResponse);
//        Assertions.assertEquals("Exam is not found", byteArrayOutputStream.toString());
//
//        Mockito.when(mockRequest.getPathInfo()).thenReturn(null);
//        byteArrayOutputStream = new ByteArrayOutputStream();
//        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));
//        examServlet.doDelete(mockRequest, mockResponse);
//        Assertions.assertEquals("Must to write a exam's id", byteArrayOutputStream.toString());
//
//        Mockito.when(mockRequest.getPathInfo()).thenReturn("/");
//        byteArrayOutputStream = new ByteArrayOutputStream();
//        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));
//        examServlet.doDelete(mockRequest, mockResponse);
//        Assertions.assertEquals("Must to write a exam's id", byteArrayOutputStream.toString());
//    }
}