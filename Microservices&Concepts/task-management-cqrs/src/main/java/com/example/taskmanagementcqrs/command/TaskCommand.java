package com.example.taskmanagementcqrs.command;

import lombok.Data;

@Data
public class TaskCommand {
    private String title;
    private String description;
}