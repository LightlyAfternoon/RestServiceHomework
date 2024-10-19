package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.*;
import org.example.service.GradeService;
import org.example.controller.dto.GradeDTO;
import org.example.controller.mapper.GradeDTOMapper;

class GradeServletTest {
    GradeServlet gradeServlet;
    GradeService mockGradeService;
    GradeDTO gradeDTO;
    GradeEntity gradeEntity;
    HttpServletRequest mockRequest;
    HttpServletResponse mockResponse;
    TeacherEntity teacher;
    GroupEntity group;
    SubjectEntity subject;
    StudentEntity student;
    ExamEntity exam;

    GradeDTOMapper gradeMapper = GradeDTOMapper.INSTANCE;

//    @BeforeEach
//    void setUp() {
//        mockRequest = Mockito.mock(HttpServletRequest.class);
//        mockResponse = Mockito.mock(HttpServletResponse.class);
//
//        mockGradeService = Mockito.mock(GradeServiceImpl.class);
//        gradeServlet = new GradeServlet(mockGradeService);
//
//        teacher = new TeacherEntity(1, "t", "t", "t");
//        group = new GroupEntity(1, "t", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), teacher);
//        subject = new SubjectEntity(1, "t");
//        student = new StudentEntity(2, "t", "t", "t", group);
//        exam = new ExamEntity(1, new Date(System.currentTimeMillis()), group, subject, teacher);
//        gradeEntity = new GradeEntity(1, student, exam, (short) 1);
//    }
//
//    @Test
//    void doGetTest() throws ServletException, IOException, SQLException {
//        Mockito.when(mockRequest.getPathInfo()).thenReturn("/1");
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));
//
//        Mockito.when(mockGradeService.findById(1)).thenReturn(gradeMapper.mapToDTO(gradeEntity));
//        gradeDTO = mockGradeService.findById(1);
//
//        gradeServlet.doGet(mockRequest, mockResponse);
//
//        gradeEntity = new GradeEntity(1, student, exam, (short) 1);
//        Assertions.assertEquals(byteArrayOutputStream.toString(), gradeDTO.toString());
//
//        Mockito.when(mockRequest.getPathInfo()).thenReturn("/2");
//        byteArrayOutputStream = new ByteArrayOutputStream();
//        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));
//
//        Mockito.when(mockGradeService.findById(1)).thenReturn(null);
//        gradeServlet.doGet(mockRequest, mockResponse);
//
//        Assertions.assertEquals("Grade is not found", byteArrayOutputStream.toString());
//
//        Mockito.when(mockRequest.getPathInfo()).thenReturn("/");
//        byteArrayOutputStream = new ByteArrayOutputStream();
//        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));
//
//        Mockito.when(mockGradeService.findAll()).thenReturn(List.of(gradeDTO));
//        List<GradeDTO> grades = mockGradeService.findAll().stream().toList();
//
//        gradeServlet.doGet(mockRequest, mockResponse);
//
//        StringBuilder s = new StringBuilder();
//        for (GradeDTO dto : grades) {
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
//                        "studentId": 1,
//                        "examId": 1,
//                        "mark": 1
//                    }""";
//
//        JsonObject json = JsonParser.parseString(jsonS).getAsJsonObject();
//        Gson gson = new GsonBuilder().create();
//
//        GradeDTO grade = gson.fromJson(json, GradeDTO.class);
//
//        Mockito.when(mockRequest.getReader()).thenReturn(Mockito.mock(BufferedReader.class));
//        Mockito.when(mockRequest.getReader().lines()).thenReturn(jsonS.lines());
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));
//
//        gradeEntity = gradeMapper.mapToEntity(grade);
//        Mockito.when(mockGradeService.save(gradeEntity)).thenReturn(gradeMapper.mapToDTO(gradeEntity));
//        gradeDTO = mockGradeService.save(gradeEntity);
//        gradeServlet.doPost(mockRequest, mockResponse);
//
//        Assertions.assertEquals(byteArrayOutputStream.toString(), gradeDTO.toString());
//    }
//
//    @Test
//    void doPutTest() throws ServletException, IOException, SQLException {
//        String jsonS = """
//                    {
//                        "id": 0,
//                        "studentId": 1,
//                        "examId": 1,
//                        "mark": 1
//                    }""";
//
//        JsonObject json = JsonParser.parseString(jsonS).getAsJsonObject();
//        Gson gson = new GsonBuilder().create();
//
//        GradeDTO grade = gson.fromJson(json, GradeDTO.class);
//
//        Mockito.when(mockRequest.getPathInfo()).thenReturn("/1");
//        Mockito.when(mockGradeService.findById(1)).thenReturn(gradeMapper.mapToDTO(gradeEntity));
//        Mockito.when(mockRequest.getReader()).thenReturn(Mockito.mock(BufferedReader.class));
//        Mockito.when(mockRequest.getReader().lines()).thenReturn(jsonS.lines());
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));
//
//        gradeEntity = gradeMapper.mapToEntity(grade, 1);
//        Mockito.when(mockGradeService.save(gradeEntity)).thenReturn(gradeMapper.mapToDTO(gradeEntity));
//        gradeDTO = mockGradeService.save(gradeEntity);
//        gradeServlet.doPut(mockRequest, mockResponse);
//
//        Assertions.assertEquals(byteArrayOutputStream.toString(), gradeDTO.toString());
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
//        Mockito.when(mockGradeService.findById(1)).thenReturn(gradeMapper.mapToDTO(gradeEntity));
//        Mockito.when(mockGradeService.deleteById(1)).thenReturn(true);
//
//        gradeServlet.doDelete(mockRequest, mockResponse);
//
//        Assertions.assertEquals(byteArrayOutputStream.toString(), "{\"success\":\""+mockGradeService.deleteById(1)+"\"}");
//
//        Mockito.when(mockRequest.getPathInfo()).thenReturn("/2");
//        Mockito.when(mockGradeService.findById(2)).thenReturn(null);
//        byteArrayOutputStream = new ByteArrayOutputStream();
//        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));
//
//        gradeServlet.doDelete(mockRequest, mockResponse);
//        Assertions.assertEquals("Grade is not found", byteArrayOutputStream.toString());
//
//        Mockito.when(mockRequest.getPathInfo()).thenReturn(null);
//        byteArrayOutputStream = new ByteArrayOutputStream();
//        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));
//        gradeServlet.doDelete(mockRequest, mockResponse);
//        Assertions.assertEquals("Must to write a grade's id", byteArrayOutputStream.toString());
//
//        Mockito.when(mockRequest.getPathInfo()).thenReturn("/");
//        byteArrayOutputStream = new ByteArrayOutputStream();
//        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));
//        gradeServlet.doDelete(mockRequest, mockResponse);
//        Assertions.assertEquals("Must to write a grade's id", byteArrayOutputStream.toString());
//    }
}