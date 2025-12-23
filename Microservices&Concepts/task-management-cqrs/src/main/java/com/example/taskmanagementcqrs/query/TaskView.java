package com.example.taskmanagementcqrs.query;

import lombok.Data;

@Data
public class TaskView {
    private String id;
    private String title;
    private String description;
}