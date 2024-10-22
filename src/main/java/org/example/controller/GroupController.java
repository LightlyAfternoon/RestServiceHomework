package org.example.controller;

import org.example.controller.dto.ExamDTO;
import org.example.controller.dto.GroupDTO;
import org.example.controller.dto.StudentDTO;
import org.example.controller.dto.SubjectDTO;
import org.example.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Set;

@RestController
@RequestMapping("/group")
public class GroupController {
    GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/")
    public Set<GroupDTO> getGroups() throws SQLException {
        return groupService.findAll();
    }

    @GetMapping("/{id}")
    public GroupDTO getGroup(@PathVariable("id") int id) throws SQLException {
        return groupService.findById(id);
    }

    @GetMapping("/{id}/student")
    public Set<StudentDTO> getStudents(@PathVariable("id") int id) throws SQLException {
        return groupService.findAllStudentsWithGroupId(id);
    }

    @GetMapping("/{id}/subject")
    public Set<SubjectDTO> getSubjects(@PathVariable("id") int id) throws SQLException {
        return groupService.findAllSubjectsWithGroupId(id);
    }

    @GetMapping("/{id}/exam")
    public Set<ExamDTO> getExams(@PathVariable("id") int id) throws SQLException {
        return groupService.findAllExamsWithGroupId(id);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public GroupDTO createGroup(@RequestBody GroupDTO groupDTO) throws SQLException {
        return groupService.save(groupDTO);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public GroupDTO updateGroup(@PathVariable("id") int id, @RequestBody GroupDTO groupDTO) throws SQLException {
        return groupService.save(groupDTO, id);
    }

    @DeleteMapping("/{id}")
    public String deleteGroup(@PathVariable("id") int id) throws SQLException {
        GroupDTO groupDTO = groupService.findById(id);

        if (groupDTO != null && (groupDTO.getStudents() != null || groupDTO.getExams() != null)) {
            return "Group is connected to some students and/or exams!";
        } else if (groupDTO == null) {
            return "There is no group with id " + id;
        } else {
            groupService.deleteById(id);

            if (groupService.findById(id) == null) {
                return "Group deleted!";
            } else {
                return "Failed to delete group!";
            }
        }
    }
}