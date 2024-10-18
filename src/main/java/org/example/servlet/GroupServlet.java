package org.example.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.db.ConnectionManager;
import org.example.repository.GroupRepository;
import org.example.service.GroupService;
import org.example.service.impl.GroupServiceImpl;
import org.example.servlet.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@WebServlet("/group/*")
public class GroupServlet extends HttpServlet {
    GroupService groupService;

    @Autowired
    public GroupServlet(GroupRepository groupRepository) {
        this.groupService = new GroupServiceImpl(groupRepository);

        ConnectionManager.setConfig();
    }

    public GroupServlet(GroupService groupService) {
        this.groupService = groupService;
    }
}