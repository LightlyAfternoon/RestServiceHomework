package org.example.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.GroupEntity;
import org.example.model.SubjectEntity;
import org.example.model.TeacherEntity;
import org.example.service.GroupService;
import org.example.service.SubjectService;
import org.example.service.TeacherService;
import org.example.service.impl.GroupServiceImpl;
import org.example.service.impl.SubjectServiceImpl;
import org.example.servlet.dto.*;
import org.example.servlet.dto.SubjectDTO;
import org.example.servlet.mapper.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

class SubjectServletTest {
    SubjectServlet subjectServlet;
    SubjectService mockSubjectService;
    @Autowired
    TeacherService mockTeacherService;
    GroupService mockGroupService;
    SubjectDTO subjectDTO;
    SubjectEntity subjectEntity;
    HttpServletRequest mockRequest;
    HttpServletResponse mockResponse;

    SubjectDTOMapper subjectMapper = SubjectDTOMapper.INSTANCE;
    TeacherDTOMapper teacherMapper = TeacherDTOMapper.INSTANCE;
    GroupDTOMapper groupMapper = GroupDTOMapper.INSTANCE;

    @BeforeEach
    void setUp() {
        mockRequest = Mockito.mock(HttpServletRequest.class);
        mockResponse = Mockito.mock(HttpServletResponse.class);

        mockSubjectService = Mockito.mock(SubjectServiceImpl.class);
        mockTeacherService = Mockito.mock(TeacherService.class);
        mockGroupService = Mockito.mock(GroupServiceImpl.class);
        subjectServlet = new SubjectServlet(mockSubjectService, mockTeacherService, mockGroupService);
        subjectEntity = new SubjectEntity(1, "t");
    }

    @Test
    void doGetTest() throws ServletException, IOException, SQLException {
        Mockito.when(mockRequest.getPathInfo()).thenReturn("/1");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));

        Mockito.when(mockSubjectService.findById(1)).thenReturn(subjectMapper.mapToDTO(subjectEntity));
        subjectDTO = mockSubjectService.findById(1);

        subjectServlet.doGet(mockRequest, mockResponse);

        subjectEntity = new SubjectEntity(1, "t");
        Assertions.assertEquals(byteArrayOutputStream.toString(), subjectDTO.toString());

        Mockito.when(mockRequest.getPathInfo()).thenReturn("/2");
        byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));

        Mockito.when(mockSubjectService.findById(1)).thenReturn(null);
        subjectServlet.doGet(mockRequest, mockResponse);

        Assertions.assertEquals("Subject is not found", byteArrayOutputStream.toString());

        Mockito.when(mockRequest.getPathInfo()).thenReturn("/");
        byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));

        Mockito.when(mockSubjectService.findAll()).thenReturn(List.of(subjectDTO));
        List<SubjectDTO> subjects = mockSubjectService.findAll().stream().toList();

        subjectServlet.doGet(mockRequest, mockResponse);

        StringBuilder stringBuilder = new StringBuilder();
        for (SubjectDTO dto : subjects) {
            stringBuilder.append(dto.toString());
        }

        Assertions.assertEquals(byteArrayOutputStream.toString(), stringBuilder.toString());

        Mockito.when(mockRequest.getPathInfo()).thenReturn("/1/exam");
        byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));

        TeacherEntity teacher = new TeacherEntity(1, "t", "t", "t");
        GroupEntity group = new GroupEntity(1, "t", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), teacher);
        SubjectEntity subject = new SubjectEntity(1, "t");
        ExamDTO examDTO = new ExamDTO(1, new Date(new GregorianCalendar(2011,Calendar.SEPTEMBER,1).getTimeInMillis()), group, subject, teacher);
        Mockito.when(mockSubjectService.findById(1)).thenReturn(subjectMapper.mapToDTO(subjectEntity));
        Mockito.when(mockSubjectService.findAllExamsWithSubjectId(1)).thenReturn(List.of(examDTO));
        List<ExamDTO> exams = mockSubjectService.findAllExamsWithSubjectId(1).stream().toList();

        subjectServlet.doGet(mockRequest, mockResponse);

        stringBuilder = new StringBuilder();
        for (ExamDTO dto : exams) {
            if (exams.getLast() != dto) {
                stringBuilder.append(dto.toString()).append(", \n");
            } else {
                stringBuilder.append(dto.toString());
            }
        }

        Assertions.assertEquals(byteArrayOutputStream.toString(), stringBuilder.toString());

        Mockito.when(mockRequest.getPathInfo()).thenReturn("/1/teacher");
        byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));

        TeacherDTO teacherDTO = new TeacherDTO(1, "t", "t", "t");
        Mockito.when(mockSubjectService.findById(1)).thenReturn(subjectMapper.mapToDTO(subjectEntity));
        Mockito.when(mockSubjectService.findAllTeachersWithSubjectId(1)).thenReturn(List.of(teacherDTO));
        List<TeacherDTO> teachers = mockSubjectService.findAllTeachersWithSubjectId(1).stream().toList();

        subjectServlet.doGet(mockRequest, mockResponse);

        stringBuilder = new StringBuilder();
        for (TeacherDTO dto : teachers) {
            if (teachers.getLast() != dto) {
                stringBuilder.append(dto.toString()).append(", \n");
            } else {
                stringBuilder.append(dto.toString());
            }
        }

        Assertions.assertEquals(byteArrayOutputStream.toString(), stringBuilder.toString());

        Mockito.when(mockRequest.getPathInfo()).thenReturn("/1/group");
        byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));

        GroupDTO groupDTO = new GroupDTO(1, "t",
                new Date(new GregorianCalendar(2011,Calendar.SEPTEMBER,1).getTimeInMillis()),
                new Date(new GregorianCalendar(2016,Calendar.JANUARY,5).getTimeInMillis()), teacher);
        Mockito.when(mockSubjectService.findById(1)).thenReturn(subjectMapper.mapToDTO(subjectEntity));
        Mockito.when(mockSubjectService.findAllGroupsWithSubjectId(1)).thenReturn(List.of(groupDTO));
        List<GroupDTO> groups = mockSubjectService.findAllGroupsWithSubjectId(1).stream().toList();

        subjectServlet.doGet(mockRequest, mockResponse);

        stringBuilder = new StringBuilder();
        for (GroupDTO dto : groups) {
            if (groups.getLast() != dto) {
                stringBuilder.append(dto.toString()).append(", \n");
            } else {
                stringBuilder.append(dto.toString());
            }
        }

        Assertions.assertEquals(byteArrayOutputStream.toString(), stringBuilder.toString());
    }

    @Test
    void doPostTest() throws ServletException, IOException, SQLException {
        String jsonS = """
                    {
                        "id": 0,
                        "name": "t"
                    }""";

        JsonObject json = JsonParser.parseString(jsonS).getAsJsonObject();
        Gson gson = new GsonBuilder().create();

        SubjectDTO subject = gson.fromJson(json, SubjectDTO.class);

        Mockito.when(mockRequest.getReader()).thenReturn(Mockito.mock(BufferedReader.class));
        Mockito.when(mockRequest.getReader().lines()).thenReturn(jsonS.lines());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));

        subjectEntity = subjectMapper.mapToEntity(subject);
        Mockito.when(mockSubjectService.save(subjectEntity)).thenReturn(subjectMapper.mapToDTO(subjectEntity));
        subjectDTO = mockSubjectService.save(subjectEntity);
        subjectServlet.doPost(mockRequest, mockResponse);

        Assertions.assertEquals(byteArrayOutputStream.toString(), subjectDTO.toString());
    }

    @Test
    void doPutTest() throws ServletException, IOException, SQLException {
        String jsonS = """
                    {
                        "id": 0,
                        "name": "t"
                    }""";

        JsonObject json = JsonParser.parseString(jsonS).getAsJsonObject();
        Gson gson = new GsonBuilder().create();

        SubjectDTO subject = gson.fromJson(json, SubjectDTO.class);

        Mockito.when(mockRequest.getPathInfo()).thenReturn("/1");
        Mockito.when(mockSubjectService.findById(1)).thenReturn(subjectMapper.mapToDTO(subjectEntity));
        Mockito.when(mockRequest.getReader()).thenReturn(Mockito.mock(BufferedReader.class));
        Mockito.when(mockRequest.getReader().lines()).thenReturn(jsonS.lines());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));

        subjectEntity = subjectMapper.mapToEntity(subject, 1);
        Mockito.when(mockSubjectService.save(subjectEntity)).thenReturn(subjectMapper.mapToDTO(subjectEntity));
        subjectDTO = mockSubjectService.save(subjectEntity);

        subjectServlet.doPut(mockRequest, mockResponse);

        Assertions.assertEquals(byteArrayOutputStream.toString(), subjectDTO.toString());

        Mockito.when(mockRequest.getPathInfo()).thenReturn("/1/teacher/1");
        byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));

        byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));

        TeacherEntity teacherEntity = new TeacherEntity(1, "t", "t", "t");
        Mockito.when(mockTeacherService.findById(1)).thenReturn(teacherMapper.mapToDTO(teacherEntity));
        Mockito.when(mockTeacherService.save(teacherEntity)).thenReturn(teacherMapper.mapToDTO(teacherEntity));
        Mockito.when(mockSubjectService.save(subjectEntity, teacherEntity)).thenReturn(teacherMapper.mapToDTO(teacherEntity));
        TeacherDTO teacherDTO = mockTeacherService.save(teacherEntity);

        subjectServlet.doPut(mockRequest, mockResponse);

        Assertions.assertEquals(byteArrayOutputStream.toString(), teacherDTO.toString());

        Mockito.when(mockRequest.getPathInfo()).thenReturn("/1/group/1");
        byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));

        byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));

        GroupEntity groupEntity = new GroupEntity(1, "t", new Date(new GregorianCalendar(2029, Calendar.SEPTEMBER, 1).getTimeInMillis()),
                new Date(new GregorianCalendar(2033, Calendar.JULY, 3).getTimeInMillis()), teacherEntity);
        Mockito.when(mockGroupService.findById(1)).thenReturn(groupMapper.mapToDTO(groupEntity));
        Mockito.when(mockGroupService.save(groupEntity)).thenReturn(groupMapper.mapToDTO(groupEntity));
        Mockito.when(mockSubjectService.save(subjectEntity, groupEntity)).thenReturn(groupMapper.mapToDTO(groupEntity));
        GroupDTO groupDTO = mockGroupService.save(groupEntity);

        subjectServlet.doPut(mockRequest, mockResponse);

        Assertions.assertEquals(byteArrayOutputStream.toString(), groupDTO.toString());
    }

    @Test
    void doDeleteTest() throws ServletException, IOException, SQLException {
        Mockito.when(mockRequest.getPathInfo()).thenReturn("/1");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));

        Mockito.when(mockSubjectService.findById(1)).thenReturn(subjectMapper.mapToDTO(subjectEntity));
        Mockito.when(mockSubjectService.deleteById(1)).thenReturn(true);

        subjectServlet.doDelete(mockRequest, mockResponse);

        Assertions.assertEquals(byteArrayOutputStream.toString(), "{\"success\":\""+mockSubjectService.deleteById(1)+"\"}");

        Mockito.when(mockRequest.getPathInfo()).thenReturn("/2");
        Mockito.when(mockSubjectService.findById(2)).thenReturn(null);
        byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));

        subjectServlet.doDelete(mockRequest, mockResponse);
        Assertions.assertEquals("Subject is not found", byteArrayOutputStream.toString());

        Mockito.when(mockRequest.getPathInfo()).thenReturn(null);
        byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));
        subjectServlet.doDelete(mockRequest, mockResponse);
        Assertions.assertEquals("Must to write a subject's id", byteArrayOutputStream.toString());

        Mockito.when(mockRequest.getPathInfo()).thenReturn("/");
        byteArrayOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockResponse.getWriter()).thenReturn(new PrintWriter(byteArrayOutputStream));
        subjectServlet.doDelete(mockRequest, mockResponse);
        Assertions.assertEquals("Must to write a subject's id", byteArrayOutputStream.toString());
    }
}