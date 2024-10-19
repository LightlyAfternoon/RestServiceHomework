package org.example.controller;

import jakarta.servlet.annotation.WebServlet;
import org.example.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;

@WebServlet("/group/*")
public class GroupServlet {
    GroupService groupService;

    @Autowired
    public GroupServlet(GroupService groupService) {
        this.groupService = groupService;
    }
}